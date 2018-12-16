package com.istd.digitalcanteen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//This is the ViewHolder for the CardView Embedded in the RecyclerView for the ExploreFragment for the student users.
public class FoodHolderStudent extends RecyclerView.ViewHolder {
    View mView;
    int id;
    FirebaseDatabase mFB;
    DatabaseReference mRef;

    public FoodHolderStudent(View itemView){

        super(itemView);
        mView = itemView;
        Log.i("holderhay", "holder is created");

        mView.setOnClickListener(new View.OnClickListener(){
            // what happens after clicking a food item in the menu:
            // the food id is saved under tempOrder in the firebase, and is retrieved by shoppingCart before checking out
            @Override
            public void onClick(View view) {
                int id = getId();
                sendTempOrder(id);
                Toast.makeText(view.getContext(),"Added to shopping cart",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getId(){
        return this.id; //save the id of clicked food, the id is going to be saved under tempOrder in Firebase
    }

    public void sendTempOrder(int id){ //write foodId to firebase
        mFB = FirebaseDatabase.getInstance();
        mRef = mFB.getReference("tempOrder");
        final int idValue = id;

        mRef.addListenerForSingleValueEvent(new ValueEventListener() { // use ListenerForSingleValueEvent
            // instead of ValueEventListener to avoid carrying out mRef.child("childName").setValue(value) repeatedly
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long oldId = dataSnapshot.getChildrenCount(); //read the current no. of items in tempOrder
                int newId = (int)oldId + 1; //newId is the child to be added
                String actualId = Integer.toString(newId);
                mRef.child(actualId).setValue(idValue); // idValue is the foodId to be saved
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        

    }

    public void setId(int id){
        this.id = id;
        String message = "a view with id "+id+" is created and set.";
        Log.i("setid", message);
    }

    public void setDetails(Context ctx, String name, String price, String prepTime, String availability){
        Log.i("setid2","setDetails is called with id "+this.id);
        TextView textViewName = mView.findViewById(R.id.cardview_food_name);
        TextView textViewPrice = mView.findViewById(R.id.cardview_food_price);
        TextView textViewPrepTime = mView.findViewById(R.id.cardview_food_preptime);
        TextView textViewAvailability = mView.findViewById(R.id.cardview_food_availability);
        ImageView imageViewPhoto = mView.findViewById(R.id.cardview_food_photo);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagePathReal = storageRef.child("foodPhotos").child(this.id+".jpg");
        GlideApp.with(itemView.getContext() /* context */)
                .load(imagePathReal)
                .into(imageViewPhoto);
        Log.i("glidehay",imagePathReal.toString());
        textViewName.setText(name);
        textViewPrice.setText("price: "+price);
        textViewPrepTime.setText("estimated preparation time: "+prepTime);
        textViewAvailability.setText("availability: "+availability);
    }


}