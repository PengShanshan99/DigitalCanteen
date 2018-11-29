package com.istd.digitalcanteen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class FoodHolder extends RecyclerView.ViewHolder {
    View mView;
    Integer id;
    public FoodHolder(View itemView){
        super(itemView);
        mView = itemView;
        Log.i("holderhay", "holder is created");
        mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditMenu.class);
                intent.putExtra("id", id);
                view.getContext().startActivity(intent);
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

