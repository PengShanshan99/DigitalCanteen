package com.istd.digitalcanteen;

import android.content.Intent;
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

public class EditMenu extends AppCompatActivity {

    Button buttonMenuSave;
    Integer idCounterFood = 9;
    EditText editTextFoodName;
    EditText editTextFoodPrice;
    EditText editTextFoodPrepTime;
    Spinner spinnerFoodAvailability;
    static String TAG = "thefirebasebug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        spinnerFoodAvailability = findViewById(R.id.spinner_availability);
        editTextFoodName = findViewById(R.id.enterFoodName);
        editTextFoodPrepTime = findViewById(R.id.enterPreparationTime);
        editTextFoodPrice = findViewById(R.id.enterPrice);
        buttonMenuSave = findViewById(R.id.menu_save);
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
                idCounterFood += 1;
                //the marked down code is used if we decided to use a food class. But I think directly reading from
                // the input line is more convenient.
//                Food anotherFood = new Food();
//                anotherFood.setId(idCounter.toString());//to be changed
//                anotherFood.setAvailability(true);
//                anotherFood.setName(xxx);
//                anotherFood.setPrice(xxx);
//                anotherFood.setStall(xxx);
//                anotherFood.setPrepTime(xxx);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference refFoodName = database.getReference("menu/"+idCounterFood+"/name");
                refFoodName.setValue(editTextFoodName.getText().toString());
                DatabaseReference refFoodPrice = database.getReference("menu/"+idCounterFood+"/price");
                refFoodPrice.setValue(editTextFoodPrice.getText().toString());
                DatabaseReference refFoodPrepTime = database.getReference("menu/"+idCounterFood+"/prepTime");
                refFoodPrepTime.setValue(editTextFoodPrepTime.getText().toString());
                DatabaseReference refFoodAvailability = database.getReference("menu/"+idCounterFood+"/availability");
                refFoodAvailability.setValue(spinnerFoodAvailability.getSelectedItem().toString());
                DatabaseReference refFoodId = database.getReference("foodId");
                refFoodId.setValue(idCounterFood.toString());
                Intent intent = new Intent(EditMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
