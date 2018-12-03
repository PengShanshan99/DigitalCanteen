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
public class AddCard extends AppCompatActivity {

    private static final int GALLERY_INTENT = 2;
    Button buttonCardSave;//the save button
    //Integer idCounterFood = 0;//and id counter, everytime a new food item is created, it is added by one and used as the id of the new food item
    EditText editTextCardNumber;//the input for name
    EditText editTextBillingAddress;//the input for address
    EditText editTextPostalCode;//the input for postal code
    //ImageButton imageButtonFoodPhoto;//the input to upload/change photo
    //Spinner spinnerFoodAvailability;//a drop down list of "Yes" and "No" to let the stall owners select the availability of the food.
    //FirebaseDatabase database;
    //FirebaseStorage storage;
    //Uri photoUri;//the selected photo uri
    //StorageReference storageRef;//a reference to the root of firebase storage
    //StorageReference imagePath;//a reference to store to the path of the image in firebase
    //int idOld;//the food id passed from Menu.java when people clicked on an food item display to modify it
    boolean allFilled = false;//check if all fields are filled
    //boolean photoUriGot = false;//check if there is a selected photo
    //Integer actualId;
    //TODO_DONE debug: why when clicking on "+" and add new food item, the activity crashes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Intent intent = getIntent();
        //database = FirebaseDatabase.getInstance();
        //Log.i("databaseInstance","FirebaseDatabase.getInstance() is successfully executed "+database.toString());
        //idOld = intent.getIntExtra("id",-1);//get the id from the intent
        //storage = FirebaseStorage.getInstance();
        //Log.i("databaseInstance","FirebaseStorage.getInstance() is successfully executed "+storage.toString());
        //storageRef = storage.getReference();

        editTextCardNumber = findViewById(R.id.enterCardNumber);
        editTextBillingAddress = findViewById(R.id.enterBillingAddress);
        editTextPostalCode = findViewById(R.id.enterPostalCode);
        buttonCardSave = findViewById(R.id.card_save);





        buttonCardSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextCardNumber.getText().toString().equals("")|
                        editTextBillingAddress.getText().toString().equals("")|
                        editTextPostalCode.getText().toString().equals("")){
                        allFilled = false;
                }else{
                    allFilled = true;
                }
                if(allFilled){//if all the fields are filled (including pictures), save to firebase.

                        //DatabaseReference refFoodName = database.getReference("menu/" + actualId + "/name");
                        //refFoodName.setValue(editTextFoodName.getText().toString());
                        //DatabaseReference refFoodPrice = database.getReference("menu/" + actualId + "/price");
                        //refFoodPrice.setValue(editTextFoodPrice.getText().toString());
                        //DatabaseReference refFoodPrepTime = database.getReference("menu/" + actualId + "/prepTime");
                        //refFoodPrepTime.setValue(editTextFoodPrepTime.getText().toString());
                       // DatabaseReference refFoodAvailability = database.getReference("menu/" + actualId + "/availability");
                        //refFoodAvailability.setValue(spinnerFoodAvailability.getSelectedItem().toString());
                        //DatabaseReference refFoodId = database.getReference("foodId");
                        //refFoodId.setValue(actualId.toString());
                        //imagePath.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                          //  @Override
                           // public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          //      Toast.makeText(EditMenu.this, R.string.food_photo_upload_done, Toast.LENGTH_LONG).show();
                          //  }
                        //}).addOnFailureListener(new OnFailureListener() {
                        //    @Override
                         //   public void onFailure(@NonNull Exception e) {
                         //       Toast.makeText(EditMenu.this, R.string.food_photo_upload_failed, Toast.LENGTH_LONG).show();
                            //}
                        //});
                       // DatabaseReference refImagePath = database.getReference("menu/"+actualId+"/imagePath");
                       // refImagePath.setValue(imagePath.toString());
                    Intent intent = new Intent(AddCard.this, WalletFragments.class);
                    startActivity(intent);
                }else{//Otherwise remind the user using a toast.
                    Toast.makeText(AddCard.this, R.string.empty_input, Toast.LENGTH_LONG).show();
                }
                //}else{//modify existing food item
                    //DatabaseReference refFoodNameOld = database.getReference("menu/" + actualId + "/name");
                    //refFoodNameOld.setValue(editTextFoodName.getText().toString());
                    //DatabaseReference refFoodPriceOld = database.getReference("menu/" + actualId + "/price");
                    //refFoodPriceOld.setValue(editTextFoodPrice.getText().toString());
                    //DatabaseReference refFoodPrepTimeOld = database.getReference("menu/" + actualId + "/prepTime");
                    //refFoodPrepTimeOld.setValue(editTextFoodPrepTime.getText().toString());
                    //DatabaseReference refFoodAvailabilityOld = database.getReference("menu/" + actualId + "/availability");
                    ///refFoodAvailabilityOld.setValue(spinnerFoodAvailability.getSelectedItem().toString());
                    //if (photoUriGot){//if changed photo, upload the new photo to firebase
                    //    imagePath.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     //       @Override
                        //    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //        Toast.makeText(EditMenu.this, R.string.food_photo_upload_done, Toast.LENGTH_LONG).show();
                      //      }
                     //   }).addOnFailureListener(new OnFailureListener() {
                         //   @Override
                        //    public void onFailure(@NonNull Exception e) {
                        //        Toast.makeText(EditMenu.this, R.string.food_photo_upload_failed, Toast.LENGTH_LONG).show();
                        //    }
                       // });
                       // DatabaseReference refImagePath = database.getReference("menu/"+actualId+"/imagePath");
                       // refImagePath.setValue(imagePath.toString());
                    //}
                    //Intent intent = new Intent(AddCard.this, MainActivity.class);
                    //startActivity(intent);
            }
        });
    }
}

/*
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
*/
