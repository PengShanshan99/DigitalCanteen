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
import android.text.format.DateUtils;

public class WalletFragments extends Fragment {
    Context mContext;
    RecyclerView recyclerViewMenu;
    FirebaseDatabase mFB;
    DatabaseReference mRef;
    ArrayList<Integer> list_of_id;// a list to remember the id's when the food items are retrieved from firebase, so that when
    //people want to modify a food and click on a food display in the ui and be led to the edit food page, the edittextviews could be filled up automatically
    //with the data retrieved from firebase first.
    View rootView;
    Adapter mAdapter;
    double dailyExpense;
    double weeklyExpense;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_wallet, container, false);

        //TODO:retrieve finished order by this user according to date of order
        mFB = FirebaseDatabase.getInstance();
        mRef = mFB.getReference("menu"); //menu to be changed


        FloatingActionButton fab_refresh = (FloatingActionButton) rootView.findViewById(R.id.fab_refresh_moving);//refresh the list when this button is clicked

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Toast toast = Toast.makeText(getActivity(), R.string.databaseChanged, Toast.LENGTH_LONG);
//                        toast.show();
                        double dailyExpense = 0.0;
                        double weeklyExpense = 0.0;

                        list_of_id = new ArrayList<Integer>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            list_of_id.add(Integer.parseInt(dataSnapshot1.getKey()));
                            Order orderRetrieved = dataSnapshot1.getValue(Order.class);

                            double orderTotal = orderRetrieved.getTotal();
                            String orderTime = orderRetrieved.getTime();
                            //TODO:write date information in order
                            //TODO: read date info here

                            //if DateUtils.isToday(orderTime){
                            //  dailyExpense = dailyExpense + orderTotal;
                            //}
                            //TODO: calculate and display daily&weekly expense


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("hello", "Failed to read value.", databaseError.toException());
                    }
                });

            }

        });
        return rootView;
    }

}
