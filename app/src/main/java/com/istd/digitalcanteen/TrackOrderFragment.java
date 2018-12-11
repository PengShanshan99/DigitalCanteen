package com.istd.digitalcanteen;

import android.content.Context;
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

public class TrackOrderFragment extends Fragment {
    Context mContext;
    View rootView;
    FloatingActionButton fab_refresh_track;
    RecyclerView recyclerViewTrackOrders;
    DatabaseReference mRef;
    FirebaseDatabase mFB;
    ArrayList<Order> list;
    Order orderRetrieved;
    ArrayList<Integer> foods;
    AdapterTrackingOrders mAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("orderqueueDebug", "entered fragment orders");
        mFB = FirebaseDatabase.getInstance();
        mRef = mFB.getReference("orderQueue");
        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_track_order, container, false);
        fab_refresh_track = rootView.findViewById(R.id.fab_refresh_track_orders);
        recyclerViewTrackOrders = rootView.findViewById(R.id.recyclerView_track_orders);
        fab_refresh_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdapterTrackingOrders mAdapterHay = GetAdapter();
                recyclerViewTrackOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewTrackOrders.setAdapter(mAdapterHay);
            }
        });
        return rootView;
    }
    public AdapterTrackingOrders GetAdapter(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Order>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    orderRetrieved = dataSnapshot1.getValue(Order.class);
                    foods = new ArrayList<Integer>();
                    DatabaseReference tempRef = mFB.getReference("orderQueue/" + dataSnapshot1.getKey() + "/foods");
                    tempRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot6) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot6.getChildren()) {;
                                foods.add((int)(long)dataSnapshot2.getValue());
                            };
                            orderRetrieved.setFoods(foods);
                            foods = new ArrayList<Integer>();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.i("hello","retrieve foods failed");
                        }
                    });
                    list.add(orderRetrieved);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("hello", "Failed to read value.", databaseError.toException());
            }
        });
        mAdapter = new AdapterTrackingOrders(list, recyclerViewTrackOrders.getContext());
        return mAdapter;
    }
}
