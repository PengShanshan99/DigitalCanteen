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
//this is the Activity where users use to add bank accounts for their online payment wallet
public class AddCard extends AppCompatActivity {

    private static final int GALLERY_INTENT = 2;
    Button buttonCardSave;//the save button
    EditText editTextBillingAddress;//the input for address
    EditText editTextPostalCode;//the input for postal code
    EditText editTextCardNumber;
    boolean allFilled = false;//check if all fields are filled

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Intent intent = getIntent();
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
                if(allFilled){
                    Intent intent = new Intent(AddCard.this, WalletFragments.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(AddCard.this, R.string.empty_input, Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}

