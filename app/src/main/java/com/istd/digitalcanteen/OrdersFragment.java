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

// the home page
//TODO 1.3. change cardview of an order to grey if it is finished. (Pass done variable when retrieveing from firebase)
public class OrdersFragment extends Fragment {
    Context mContext;
    RecyclerView recyclerViewOrders;
    FirebaseDatabase mFB;//a firebase database object
    DatabaseReference mRef;// a reference object for firebase
    AdapterOrders mAdapter;// an adapter that combines the firebase reference to the recycler view in our UI
    List list;// a list for order objects
    View rootView;
    ArrayList<Integer> foods;
    Order localOrder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("orderqueueDebug","entered fragment orders");
        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        mFB = FirebaseDatabase.getInstance();
        mRef = mFB.getReference("orderQueue");
        recyclerViewOrders = rootView.findViewById(R.id.recyclerView_orders);
        recyclerViewOrders.setHasFixedSize(true);
        Log.i("movingDebug","mFB, mRef, recyclerview assigned.");
        FloatingActionButton fab_refresh = (FloatingActionButton) rootView.findViewById(R.id.fab_refresh_orders);//refresh the list when this button is clicked
        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Toast toast = Toast.makeText(getActivity(), R.string.databaseChanged, Toast.LENGTH_LONG);
//                        toast.show();
                        list = new ArrayList<Order>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Order orderRetrieved = dataSnapshot1.getValue(Order.class);
                            localOrder = new Order();
                            String time = orderRetrieved.getTime();
                            localOrder.setTime(time);
                            Log.i("hello", "successfully added one more food");
                            foods = new ArrayList<Integer>();
                            Log.i("orderqueue","key now is "+dataSnapshot1.getKey());
                            DatabaseReference tempRef = mFB.getReference("orderQueue/" + dataSnapshot1.getKey() + "/foods");
                            tempRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                        Log.i("orderqueue","food now is number "+dataSnapshot2.getValue());
                                        foods.add((int)(long)dataSnapshot2.getValue());
                                        Log.i("orderqueue","foods now is "+foods.toString());
                                    };
                                    localOrder.setFoods(foods);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.i("hello","retrieve foods failed");
                                }
                            });
                            list.add(localOrder);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("hello", "Failed to read value.", databaseError.toException());
                    }
                });
                Log.i("movingDebug", "OnChangeListener finished");
                mAdapter = new AdapterOrders(list, recyclerViewOrders.getContext());
                Log.i("movingDebug", "mAdapter assigned");
                recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
                Log.i("movingDebug", "layout manager set.");
                recyclerViewOrders.setAdapter(mAdapter);
                Log.i("movingDebug", "mAdapter connected with recyclerview");
            }
        });
        return rootView;
    }
}
