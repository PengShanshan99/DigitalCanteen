package com.istd.digitalcanteen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditMenu extends AppCompatActivity {

    Button buttonMenuSave;
    Integer idCounterFood;//and id counter, everytime a new food item is created, it is added by one and used as the id of the new food item
    EditText editTextFoodName;
    EditText editTextFoodPrice;
    EditText editTextFoodPrepTime;
    Spinner spinnerFoodAvailability;//a drop down list of "Yes" and "No" to let the stall owners select the availability of the food.
    FirebaseDatabase database;
    int idOld;//the food id passed from Menu.java when people clicked on an food item display to modify it
    static String TAG = "thefirebasebug";//just for debug, can ignore
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        Intent intent = getIntent();
        database = FirebaseDatabase.getInstance();
        idOld = intent.getIntExtra("id",-1);//get the id from the intent

        spinnerFoodAvailability = findViewById(R.id.spinner_availability);
        editTextFoodName = findViewById(R.id.enterFoodName);
        editTextFoodPrepTime = findViewById(R.id.enterPreparationTime);
        editTextFoodPrice = findViewById(R.id.enterPrice);
        buttonMenuSave = findViewById(R.id.menu_save);

        if (idOld!=-1){//add new food item
            DatabaseReference refOld = database.getReference("menu/" + idOld);
            refOld.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       Food foodRetrieved = dataSnapshot.getValue(Food.class);
                       String name = foodRetrieved.getName();
                       String price = foodRetrieved.getPrice();
                       String prepTime = foodRetrieved.getPrepTime();
                       String availability = foodRetrieved.getAvailability();
                       editTextFoodName.setText(name);
                       editTextFoodPrice.setText(price);
                       editTextFoodPrepTime.setText(prepTime);
                   }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("hello", "Failed to read value old.", databaseError.toException());
                }
            });
        };
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("foodId");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i(TAG,"cannot find firebase bug TVT");
                    String id = dataSnapshot.getValue(String.class);
                    Log.i(TAG,"behind the statement!");
                    idCounterFood = Integer.parseInt(id);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //String[] tags = {"name","price","availability","prepTime"};this was supposed to be a string
        //array to loop through. But since it's only 5 elements and I was having some issues with
        //R.string.enter_food_name, I will just hard-code it out.
        buttonMenuSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idOld == -1) {//add new food item
                    idCounterFood += 1;
                    DatabaseReference refFoodName = database.getReference("menu/" + idCounterFood + "/name");
                    refFoodName.setValue(editTextFoodName.getText().toString());
                    DatabaseReference refFoodPrice = database.getReference("menu/" + idCounterFood + "/price");
                    refFoodPrice.setValue(editTextFoodPrice.getText().toString());
                    DatabaseReference refFoodPrepTime = database.getReference("menu/" + idCounterFood + "/prepTime");
                    refFoodPrepTime.setValue(editTextFoodPrepTime.getText().toString());
                    DatabaseReference refFoodAvailability = database.getReference("menu/" + idCounterFood + "/availability");
                    refFoodAvailability.setValue(spinnerFoodAvailability.getSelectedItem().toString());
                    DatabaseReference refFoodId = database.getReference("foodId");
                    refFoodId.setValue(idCounterFood.toString());
                    Intent intent = new Intent(EditMenu.this, Menu.class);
                    startActivity(intent);
                }else{//modify existing food item
                    DatabaseReference refFoodNameOld = database.getReference("menu/" + idOld + "/name");
                    refFoodNameOld.setValue(editTextFoodName.getText().toString());
                    DatabaseReference refFoodPriceOld = database.getReference("menu/" + idOld + "/price");
                    refFoodPriceOld.setValue(editTextFoodPrice.getText().toString());
                    DatabaseReference refFoodPrepTimeOld = database.getReference("menu/" + idOld + "/prepTime");
                    refFoodPrepTimeOld.setValue(editTextFoodPrepTime.getText().toString());
                    DatabaseReference refFoodAvailabilityOld = database.getReference("menu/" + idOld + "/availability");
                    refFoodAvailabilityOld.setValue(spinnerFoodAvailability.getSelectedItem().toString());
                    Intent intent = new Intent(EditMenu.this, Menu.class);
                    startActivity(intent);
                }
            }
        });
    }
}
