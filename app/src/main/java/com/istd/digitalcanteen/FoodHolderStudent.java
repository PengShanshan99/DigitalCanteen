package com.istd.digitalcanteen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.sql.ResultSet;

import org.w3c.dom.Text;

import java.util.ArrayList;


// a view holder to inflate cardviews on recycler view to display firebase database on UI
public class FoodHolderStudent extends RecyclerView.ViewHolder {
    View mView;
    int id;
    FirebaseDatabase mFB;
    DatabaseReference mRef;
    Query lastQuery;
    long oldId;
    int newId;
    String actualId;

    public FoodHolderStudent(View itemView){

        super(itemView);
        mView = itemView;
        Log.i("holderhay", "holder is created");
        mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                int id = getId();
                //sendTempOrder(id);

                Toast.makeText(view.getContext(),"Added to shopping cart",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public int getId(){
        return this.id;
    }

    public void sendTempOrder(int id){
        mFB = FirebaseDatabase.getInstance();
        mRef = mFB.getReference("tempOrder");
        Query lastQuery = mRef.child("tempOrder").orderByKey().limitToLast(1);
        //long oldId = ResultSet.getLong(lastQuery);

        int newId = (int)oldId + 1;
        String actualId = Integer.toString(newId);
        mRef.child(actualId).setValue(id);



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