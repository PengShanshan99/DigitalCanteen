package com.istd.digitalcanteen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
//the menu page, where stall owners can add new food or edit existing item.
//TODO 3. solve the problem of having to click 3 times of button before the UI successfully retrieve data from firebase.
public class ExploreFragments extends Fragment {
    Context mContext;
    RecyclerView recyclerViewMenu;
    FirebaseDatabase mFB;
    DatabaseReference mRef;
    Adapter mAdapter;// an adapter that combines the firebase reference to the recycler view in our UI
    List list;// a list for food objects
    ArrayList<Integer> list_of_id;// a list to remember the id's when the food items are retrieved from firebase, so that when
    //people want to modify a food and click on a food display in the ui and be led to the edit food page, the edittextviews could be filled up automatically
    //with the data retreved from firebase first.
    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        mFB = FirebaseDatabase.getInstance();
        mRef = mFB.getReference("menu");
        recyclerViewMenu = rootView.findViewById(R.id.recyclerView_menu_moving);
        recyclerViewMenu.setHasFixedSize(true);
        Log.i("movingDebug","mFB, mRef, recyclerview assigned.");
        FloatingActionButton fab_refresh_moving = (FloatingActionButton) rootView.findViewById(R.id.fab_refresh_moving);//refresh the list when this button is clicked
        fab_refresh_moving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Toast toast = Toast.makeText(getActivity(), R.string.databaseChanged, Toast.LENGTH_LONG);
//                        toast.show();
                        list = new ArrayList<Food>();
                        list_of_id = new ArrayList<Integer>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            list_of_id.add(Integer.parseInt(dataSnapshot1.getKey()));
                            Food foodRetrieved = dataSnapshot1.getValue(Food.class);
                            Food localFood = new Food();
                            String name = foodRetrieved.getName();
                            String price = foodRetrieved.getPrice();
                            String prepTime = foodRetrieved.getPrepTime();
                            String availability = foodRetrieved.getAvailability();
                            localFood.setName(name);
                            localFood.setPrice(price);
                            localFood.setPrepTime(prepTime);
                            localFood.setAvailability(availability);
                            list.add(localFood);
                            String theList = list_of_id.toString();
                            Log.i("hello", "successfully added one more food");
                            Log.i("listofid", theList);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("hello", "Failed to read value.", databaseError.toException());
                    }
                });
                Log.i("movingDebug", "OnChangeListener finished");
                mAdapter = new Adapter(list, recyclerViewMenu.getContext(), list_of_id);
                Log.i("movingDebug", "mAdapter assigned");
                recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
                Log.i("movingDebug", "layout manager set.");
                recyclerViewMenu.setAdapter(mAdapter);
                Log.i("movingDebug", "mAdapter connected with recyclerview");
            }
        });
        return rootView;
    }
}
