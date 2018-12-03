package com.istd.digitalcanteen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SCHolder extends RecyclerView.ViewHolder {
    Integer orderId;
    View mView;
    ArrayList<String> foodList;
    ListView listViewfoods;
    TextView textViewFoodName;
    String toPutInTextView = "";
    FirebaseDatabase mFB;//a firebase database object
    DatabaseReference mRef;// a reference object for firebase orderqueue
    DatabaseReference foodQSize;
    //TODO 5. implement swipe and delete function
    public SCHolder(View itemView){
        super(itemView);
        mView = itemView;
        foodList = new ArrayList<String>();
        textViewFoodName = mView.findViewById(R.id.cardView_sc_foodname);
        mFB = FirebaseDatabase.getInstance();
        foodQSize = mFB.getReference("foodQueueSize");
    }
    public void setDetails(Context ctx, String foodName){
        textViewFoodName.setText(foodName);
    }
}
