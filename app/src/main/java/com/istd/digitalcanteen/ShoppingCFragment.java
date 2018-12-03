package com.istd.digitalcanteen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShoppingCFragment extends Fragment {
    Context mContext;
    RecyclerView recyclerViewSC;
    FirebaseDatabase mFB;//a firebase database object
    //DatabaseReference mRefFoodQ;
    DatabaseReference mRef;// a reference object for firebase orderqueue
    AdapterOrders mAdapter;// an adapter that combines the firebase reference to the recycler view in our UI
    ArrayList<Order> list = new ArrayList<Order>();// a list for order objects
    View rootView;
    Button buttonCheckOut;
    FloatingActionButton fabRefreshSC;
    ArrayList<Integer> foods;
    Order localOrder;
    Order orderRetrieved;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        buttonCheckOut = rootView.findViewById(R.id.sc_checkout);
        mFB = FirebaseDatabase.getInstance();
        mRef = mFB.getReference("orderQueue");
        recyclerViewSC = rootView.findViewById(R.id.recyclerView_sc);
        recyclerViewSC.setHasFixedSize(true);
        fabRefreshSC = (FloatingActionButton) rootView.findViewById(R.id.fab_refresh_SC);//refresh the list when this button is clicked
        return rootView;
    }
}
