package com.istd.digitalcanteen;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SuggestedQueueFragment extends Fragment {
    View rootView;
    FirebaseDatabase mFB;
    FloatingActionButton buttonRefreshQ;
    DatabaseReference mRef;
    FoodQ QRetrieved;
    ArrayList<FoodQ> list = new ArrayList<FoodQ>();
    ArrayList<String> toBeDisplayed = new ArrayList<String>();//to be displayed in list view. remember to reverse the sequence.
    ListView listViewQ;
    FloatingActionButton buttonReorderQ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_suggested_queue, container, false);
        buttonReorderQ = rootView.findViewById(R.id.fab_reorder_queue);
        //buttonReorderQ.hide();
        listViewQ = rootView.findViewById(R.id.listViewQ);
        mFB = FirebaseDatabase.getInstance();
        mRef = mFB.getReference("foodQueue");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    QRetrieved = dataSnapshot1.getValue(FoodQ.class);
                    list.add(QRetrieved);
                    Log.i("foodQ1","Retrieve finished, list now is "+list.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("hello","retrieving food queue failed.");
            }
        });
        buttonRefreshQ = rootView.findViewById(R.id.fab_refresh_queue);
        buttonRefreshQ.setOnClickListener(new View.OnClickListener(){
            ArrayList<FoodQ> QList = new ArrayList<>();
            @Override
            public void onClick(View view) {
                Log.i("foodQ2","list now is"+list);
                toBeDisplayed = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++ ){
                    toBeDisplayed.add(list.get(i).getFoodName());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        toBeDisplayed );
                listViewQ.setAdapter(arrayAdapter);
                TextView toClose = rootView.findViewById(R.id.toBeClosedQ);
                toClose.setVisibility(View.INVISIBLE);
            }
        });


        buttonReorderQ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (toBeDisplayed.size() > 5) {
                    ArrayList<String> reOrdered = new ArrayList<String>();
                    for (int i = 0; i < 5; i++) {
                        reOrdered = reOrder(toBeDisplayed);
                        Log.i("foodQ6", "reordered " + i + " times");
                    }
                    ArrayAdapter<String> arrayAdapterReorder = new ArrayAdapter<String>(
                            getContext(),
                            android.R.layout.simple_list_item_1,
                            reOrdered );
                    listViewQ.setAdapter(arrayAdapterReorder);
                    Log.i("foodQ7","is listview child null? : "+(listViewQ == null));
//                    listViewQ.getChildAt(2).setBackgroundColor(
//                            Color.parseColor("#00743D"));
                    Log.i("foodQ7", "element "+2+" colored blue");
                }
            }
        });
        return rootView;
    }
    public ArrayList<String> reOrder(ArrayList<String> toReorder){
        for (int i = 0; i < 4; i++){
            Log.i("foodQ6","reordering i = "+i);
            for (int j = i+1; j < 5; j++){
                Log.i("foodQ6","reordering j = "+j);
                Log.i("foodQ6","order now is "+toReorder.toString());
                if (toReorder.get(i).equals(toReorder.get(j))){
                    String temp = toReorder.get(i+1);
                    toReorder.set(i+1,toReorder.get(j));
                    toReorder.set(j,temp);
                    return toReorder;
                }
            }
        }
        return toReorder;
    }
}
