package com.istd.digitalcanteen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
//A ViewHolder for an CardView embedded in the RecyclerView in the OrderViewingFragment for stall owner
public class OrderHolder extends RecyclerView.ViewHolder {
    Integer orderId;
    View mView;
    ArrayList<String> foodList;
    TextView textViewFoods;
    String toPutInTextView = "";
    CheckBox checkBoxFinished;
    FirebaseDatabase mFB;//a firebase database object
    DatabaseReference mRef;// a reference object for firebase orderqueue
    DatabaseReference foodQSize;
    public OrderHolder(View itemView){
        //todo 1.4. delete the food in the
        super(itemView);
        mView = itemView;
        foodList = new ArrayList<String>();
        textViewFoods = mView.findViewById(R.id.cardView_food_item);
        checkBoxFinished = mView.findViewById(R.id.checkBox_finished);
        mFB = FirebaseDatabase.getInstance();
        foodQSize = mFB.getReference("foodQueueSize");
        checkBoxFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //change the done attribute to true
                    mRef = mFB.getReference("orderQueue/"+orderId+"/done");
                    mRef.setValue(true);
                    DatabaseReference mRefQ = mFB.getReference("foodQueue");
                    final TextView textViewTime = mView.findViewById(R.id.cardView_time_of_ordering);//assuming different orders come at different time
                    Query applesQuery = mRefQ.orderByChild("timeOrdered").equalTo(textViewTime.getText().toString());
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("hello", "onCancelled", databaseError.toException());
                        }
                    });
                    checkBoxFinished.setEnabled(false);
                }else{
                    //change the done attribute to false
                    mRef = mFB.getReference("orderQueue/"+orderId+"/done");
                    mRef.setValue(false);
                }
            }
        });
    }

    public void setId(int id){
        this.orderId = id;
        String message = "a view with id "+id+" is created and set.";
        Log.i("setid", message);
    }

    //TODO 2.implement the time passed since ordering function
    public void setDetails(Context ctx, String time, ArrayList<Integer> foods, boolean done){
        Log.i("orderqueue","the foods received by holder is "+foods.toString());
        TextView textViewTime = mView.findViewById(R.id.cardView_time_of_ordering);
        //TODO_done 1.1. use adapter, display JSONArray in ListView
        //TODO_done 1.2. change back to use arraylist since it's so easy to use hhh
        this.getFoodList(foods);
        Log.i("orderqueue1","foodList now is "+foodList.toString());
        textViewTime.setText(time);
        //todo_done try using a single textView to do the job
        checkBoxFinished.setChecked(done);
        if (done == true){
            checkBoxFinished.setEnabled(false);
        }
    }

    private void getFoodList(ArrayList<Integer> foods){//how come i didnt return anything it also works?
        for (int i = 0; i < foods.size(); i++){
            DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference("menu/" + foods.get(i).toString()+"/name");
            tempRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    foodList.add(dataSnapshot.getValue(String.class));
                    Log.i("orderqueue12","foodList now is "+foodList.toString());
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
}
