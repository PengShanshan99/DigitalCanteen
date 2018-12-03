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
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
    //DatabaseReference mRefFoodQ;
    DatabaseReference mRef;// a reference object for firebase orderqueue
    AdapterOrders mAdapter;// an adapter that combines the firebase reference to the recycler view in our UI
    ArrayList<Order> list = new ArrayList<Order>();// a list for order objects
    View rootView;
    ArrayList<Integer> foods;
    Order localOrder;
    Order orderRetrieved;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("orderqueueDebug","entered fragment orders");
        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        mFB = FirebaseDatabase.getInstance();
        //mRefFoodQ = mFB.getReference("foodQueue");
        mRef = mFB.getReference("orderQueue");
        recyclerViewOrders = rootView.findViewById(R.id.recyclerView_orders);
        recyclerViewOrders.setHasFixedSize(true);
        Log.i("movingDebug","mFB, mRef, recyclerview assigned.");
        FloatingActionButton fab_refresh = (FloatingActionButton) rootView.findViewById(R.id.fab_refresh_orders);//refresh the list when this button is clicked


        Log.i("referenceOrder","adapter set ");

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdapterOrders mAdapterHay = GetAdapter();
                recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewOrders.setAdapter(mAdapterHay);
            }
        });
        return rootView;
    }
    public AdapterOrders GetAdapter(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Toast toast = Toast.makeText(getActivity(), R.string.databaseChanged, Toast.LENGTH_LONG);
//                        toast.show();
                list = new ArrayList<Order>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.i("orderqueue","key now is "+dataSnapshot1.getKey());
                    orderRetrieved = dataSnapshot1.getValue(Order.class);
                    //localOrder = new Order();
                    foods = new ArrayList<Integer>();
                    DatabaseReference tempRef = mFB.getReference("orderQueue/" + dataSnapshot1.getKey() + "/foods");
                    tempRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot6) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot6.getChildren()) {
                                Log.i("orderqueue","food now is number "+dataSnapshot2.getValue());
                                foods.add((int)(long)dataSnapshot2.getValue());
                                Log.i("orderqueue","foods now is "+foods.toString());
                            };
                            Log.i("orderqueue4","setting foods "+foods.toString()+" in order");
                            //localOrder.setFoods(foods);
                            orderRetrieved.setFoods(foods);
                            Log.i("orderqueue4","foods in this order is "+orderRetrieved.getFoods().toString()+"is settled");
                            //Log.i("orderqueue4","time in this order is "+localOrder.getTime());
                            //String time = orderRetrieved.getTime();
                            //localOrder.setTime(time);
                            Log.i("orderqueue4","time of this order is "+orderRetrieved.getTime());
                            foods = new ArrayList<Integer>();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.i("hello","retrieve foods failed");
                        }
                    });
                    //list.add(localOrder);
                    list.add(orderRetrieved);
                    Log.i("orderqueue3","list of orders now is "+list.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("hello", "Failed to read value.", databaseError.toException());
            }
        });
        Log.i("movingDebug", "OnChangeListener finished");
        Log.i("movingDebug", "mAdapter assigned with list "+list.toString());

//        for (Order order1: list){
//            Log.i("orderqueue5","foods in order "+order1.toString()+" is "+order1.getTime().toString());
//        }
        mAdapter = new AdapterOrders(list, recyclerViewOrders.getContext());
//        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerViewOrders.setAdapter(mAdapter);

        Log.i("movingDebug", "layout manager set.");
        Log.i("movingDebug", "mAdapter connected with recyclerview");
        return mAdapter;
    }
}
