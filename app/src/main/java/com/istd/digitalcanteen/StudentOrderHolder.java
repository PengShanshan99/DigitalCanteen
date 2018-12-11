package com.istd.digitalcanteen;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class StudentOrderHolder extends RecyclerView.ViewHolder {
    Integer orderId;
    View mView;
    ArrayList<String> foodList;
    TextView textViewFoods;
    String toPutInTextView = "";
    CheckBox checkBoxFinished;
    FirebaseDatabase mFB;//a firebase database object
    DatabaseReference mRef;// a reference object for firebase orderqueue
    DatabaseReference foodQSize;
    boolean orderDone;
    PopupWindow popUp;
    Context rootContext;
    int estimatedTime;
    public StudentOrderHolder(final View itemView){
        super(itemView);
        mView = itemView;
        foodList = new ArrayList<String>();
        textViewFoods = mView.findViewById(R.id.cardView_food_item_track);
        checkBoxFinished = mView.findViewById(R.id.checkBox_finished_track);
        mFB = FirebaseDatabase.getInstance();
        foodQSize = mFB.getReference("foodQueueSize");
        mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (orderDone == false){
                    LayoutInflater inflater = (LayoutInflater) rootContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popupwindow, null);
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    //TextView textViewPop = popupWindow.getContentView().findViewById(R.id.textview_pop);
                    //String textOnPopWindow = getEstimateTime(orderId);
                    //textViewPop.setText(textOnPopWindow);
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });
                }else{
                    LayoutInflater inflater = (LayoutInflater) rootContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popupwindow_finished, null);
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });

                }
            }
        });
    }

    public void setId(int id){
        this.orderId = id;
    }

    public void setDetails(Context ctx, String time, ArrayList<Integer> foods, boolean done){
        TextView textViewTime = mView.findViewById(R.id.cardView_track_order_time);
        this.getFoodList(foods);
        textViewTime.setText(time);
        this.rootContext =ctx;
        checkBoxFinished.setChecked(done);
        orderDone = done;
        if (done == true){
            checkBoxFinished.setEnabled(false);
        }
    }

    private void getFoodList(ArrayList<Integer> foods){
        for (int i = 0; i < foods.size(); i++){
            DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference("menu/" + foods.get(i).toString()+"/name");
            tempRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    foodList.add(dataSnapshot.getValue(String.class));
                    toPutInTextView += dataSnapshot.getValue(String.class);
                    toPutInTextView += "\n";
                    toPutInTextView = toPutInTextView.replace("\\\n",System.getProperty("line.separator"));
                    textViewFoods.setText(toPutInTextView);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i("hello","retrieving food name failed.");
                }
            });
        }
    }

//    private String getEstimateTime(Integer orderId){
//        estimatedTime = 0;
//        mFB.getReference("orderQueue").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                    Order orderRetrieved = dataSnapshot1.getValue(Order.class);
//                    if (orderRetrieved.isDone()==false){
//                        //TODO 8 a very important change: add another attribute to the order: waitingTime.
//                        //estimatedTime += Integer.parseInt(orderRetrieved.getCookingTime());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        })
//    }
}
