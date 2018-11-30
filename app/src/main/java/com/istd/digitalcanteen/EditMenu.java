package com.istd.digitalcanteen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
//this is the Activity where users use to add new food item or modify existing item.
public class EditMenu extends AppCompatActivity {

    private static final int GALLERY_INTENT = 2;
    Button buttonMenuSave;//the save button
    Integer idCounterFood = 0;//and id counter, everytime a new food item is created, it is added by one and used as the id of the new food item
    EditText editTextFoodName;//the input for name
    EditText editTextFoodPrice;//the input for price
    EditText editTextFoodPrepTime;//the input for preparation time
    ImageButton imageButtonFoodPhoto;//the input to upload/change photo
    Spinner spinnerFoodAvailability;//a drop down list of "Yes" and "No" to let the stall owners select the availability of the food.
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri photoUri;//the selected photo uri
    StorageReference storageRef;//a reference to the root of firebase storage
    StorageReference imagePath;//a reference to store to the path of the image in firebase
    int idOld;//the food id passed from Menu.java when people clicked on an food item display to modify it
    boolean allFilled = false;//check if all fields are filled
    boolean photoUriGot = false;//check if there is a selected photo
    Integer actualId;
    //TODO_DONE debug: why when clicking on "+" and add new food item, the activity crashes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        Intent intent = getIntent();
        database = FirebaseDatabase.getInstance();
        //Log.i("databaseInstance","FirebaseDatabase.getInstance() is successfully executed "+database.toString());
        idOld = intent.getIntExtra("id",-1);//get the id from the intent
        storage = FirebaseStorage.getInstance();
        //Log.i("databaseInstance","FirebaseStorage.getInstance() is successfully executed "+storage.toString());
        storageRef = storage.getReference();
        spinnerFoodAvailability = findViewById(R.id.spinner_availability);
        editTextFoodName = findViewById(R.id.enterFoodName);
        editTextFoodPrepTime = findViewById(R.id.enterPreparationTime);
        editTextFoodPrice = findViewById(R.id.enterPrice);
        buttonMenuSave = findViewById(R.id.menu_save);
        imageButtonFoodPhoto = findViewById((R.id.food_photo));


        //TODO 4. make the go back go back to the correct place: to be done as the last step as the activity structures may change
        if (idOld!=-1){//editing existing food item
            actualId = idOld;
            DatabaseReference refOld = database.getReference("menu/" + actualId);
            imagePath = storageRef.child("foodPhotos").child(actualId+".jpg");
            GlideApp.with(EditMenu.this /* context */)
                    .load(imagePath)
                    .into(imageButtonFoodPhoto);
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
                       editTextFoodPrepTime.setText(prepTime);}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("hello", "Failed to read value old.", databaseError.toException());
                }
            });
        }else{
            DatabaseReference mref = FirebaseDatabase.getInstance().getReference("foodId");
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String id = dataSnapshot.getValue(String.class);
                    idCounterFood = Integer.parseInt(id);
                    actualId = idCounterFood+1;
                    Log.i("inElse1","id retrieved: "+idCounterFood);
                    Log.i("inElse","got actual ID: "+actualId);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("inElse1","id retriev cancelled");
                }
            });

        };


        //upload new photo (no matter it is editing existing food or adding new food)
        imageButtonFoodPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        //String[] tags = {"name","price","availability","prepTime"};this was supposed to be a string
        //array to loop through. But since it's only 5 elements and I was having some issues with
        //R.string.enter_food_name, I will just hard-code it out.

        buttonMenuSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePath = storageRef.child("foodPhotos").child(actualId+".jpg");
                if (idOld == -1) {//add new food item
                    if (editTextFoodName.getText().toString().equals("")|
                            editTextFoodPrice.getText().toString().equals("")|
                            editTextFoodPrepTime.getText().toString().equals("")|
                            photoUriGot == false){
                        allFilled = false;
                    }else{
                        allFilled = true;
                    }
                    if(allFilled){//if all the fields are filled (including pictures), save to firebase.
                    idCounterFood += 1;
                    DatabaseReference refFoodName = database.getReference("menu/" + actualId + "/name");
                    refFoodName.setValue(editTextFoodName.getText().toString());
                    DatabaseReference refFoodPrice = database.getReference("menu/" + actualId + "/price");
                    refFoodPrice.setValue(editTextFoodPrice.getText().toString());
                    DatabaseReference refFoodPrepTime = database.getReference("menu/" + actualId + "/prepTime");
                    refFoodPrepTime.setValue(editTextFoodPrepTime.getText().toString());
                    DatabaseReference refFoodAvailability = database.getReference("menu/" + actualId + "/availability");
                    refFoodAvailability.setValue(spinnerFoodAvailability.getSelectedItem().toString());
                    DatabaseReference refFoodId = database.getReference("foodId");
                    refFoodId.setValue(actualId.toString());
                    imagePath.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditMenu.this, R.string.food_photo_upload_done, Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditMenu.this, R.string.food_photo_upload_failed, Toast.LENGTH_LONG).show();
                        }
                    });
                    DatabaseReference refImagePath = database.getReference("menu/"+actualId+"/imagePath");
                    refImagePath.setValue(imagePath.toString());
                    Intent intent = new Intent(EditMenu.this, MainActivity.class);
                    startActivity(intent);
                    }else{//Otherwise remind the user using a toast.
                        Toast.makeText(EditMenu.this, R.string.empty_input, Toast.LENGTH_LONG).show();
                    }
                }else{//modify existing food item
                    DatabaseReference refFoodNameOld = database.getReference("menu/" + actualId + "/name");
                    refFoodNameOld.setValue(editTextFoodName.getText().toString());
                    DatabaseReference refFoodPriceOld = database.getReference("menu/" + actualId + "/price");
                    refFoodPriceOld.setValue(editTextFoodPrice.getText().toString());
                    DatabaseReference refFoodPrepTimeOld = database.getReference("menu/" + actualId + "/prepTime");
                    refFoodPrepTimeOld.setValue(editTextFoodPrepTime.getText().toString());
                    DatabaseReference refFoodAvailabilityOld = database.getReference("menu/" + actualId + "/availability");
                    refFoodAvailabilityOld.setValue(spinnerFoodAvailability.getSelectedItem().toString());
                    if (photoUriGot){//if changed photo, upload the new photo to firebase
                        imagePath.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(EditMenu.this, R.string.food_photo_upload_done, Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditMenu.this, R.string.food_photo_upload_failed, Toast.LENGTH_LONG).show();
                            }
                        });
                        DatabaseReference refImagePath = database.getReference("menu/"+actualId+"/imagePath");
                        refImagePath.setValue(imagePath.toString());
                    }
                    Intent intent = new Intent(EditMenu.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//get back to the APP after photo selected from gallery
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            photoUri = data.getData();
            photoUriGot = true;
            //display the image in the imagebutton view
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                imageButtonFoodPhoto.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
