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
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//TODO 6 implement the swipe to remove function for the recycler view.
public class ShoppingCFragment extends Fragment {
    Context mContext;
    RecyclerView recyclerViewSC;
    FirebaseDatabase mFB;//a firebase database object
    DatabaseReference mRef;// a reference object for firebase orderqueue
    AdapterSC mAdapter;// an adapter that combines the firebase reference to the recycler view in our UI
    ArrayList<Order> list = new ArrayList<Order>();// a list for order objects
    View rootView;
    Button buttonCheckOut;
    FloatingActionButton fabRefreshSC;
    ArrayList<Integer> foodIds = new ArrayList<Integer>();
    ArrayList<String> foodNames = new ArrayList<String>();
    Double total;
    TextView textViewTotal;
    Integer writeToId;
    DatabaseReference refFoodQSize;
    Date currentTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        buttonCheckOut = rootView.findViewById(R.id.sc_checkout);
        mFB = FirebaseDatabase.getInstance();
        mRef = mFB.getReference("tempOrder");
        recyclerViewSC = rootView.findViewById(R.id.recyclerView_sc);
        recyclerViewSC.setHasFixedSize(true);
        textViewTotal = rootView.findViewById(R.id.sc_total);
        fabRefreshSC = (FloatingActionButton) rootView.findViewById(R.id.fab_refresh_SC);//refresh the list when this button is clicked
        fabRefreshSC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                total = 0.0;
                foodIds = new ArrayList<Integer>();
                foodNames = new ArrayList<String>();
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (final DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            Integer foodId = dataSnapshot1.getValue(Integer.class);
                            foodIds.add(foodId);
                            Log.i("shoppingcart8","foodIds now is "+foodIds.toString());
                            DatabaseReference mRefName = mFB.getReference("menu/"+foodId+"/name");
                            mRefName.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String anotherFoodName = dataSnapshot.getValue(String.class);
                                    foodNames.add(anotherFoodName);
                                    mAdapter = new AdapterSC(foodNames, recyclerViewSC.getContext());
                                    recyclerViewSC.setAdapter(mAdapter);
                                    recyclerViewSC.setLayoutManager(new LinearLayoutManager(getActivity()));
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.i("hello","retrieving name from id failed.");
                                }
                            });
                            DatabaseReference mRefPrice = mFB.getReference("menu/"+foodId+"/price");
                            mRefPrice.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Double price = Double.parseDouble(dataSnapshot.getValue(String.class));
                                    total += price;
                                    textViewTotal.setText("$ "+total.toString());
                                    Log.i("shoppingcart7","price of "+price+" is added.");
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.i("hello","retrieving price failed.");
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.i("hello","retrieving from tempOrder failed.");
                    }
                });

            }
        });
        buttonCheckOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final DatabaseReference mRefId = mFB.getReference("orderId");
                mRefId.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        writeToId = dataSnapshot.getValue(Integer.class)+1;
                        DatabaseReference mRefWrDone = mFB.getReference("orderQueue/"+writeToId.toString()+"/done");
                        mRefWrDone.setValue(false);
                        DatabaseReference mRefWrId = mFB.getReference("orderQueue/"+writeToId.toString()+"/id");
                        mRefWrId.setValue(writeToId);
                        DatabaseReference mRefWrTotal = mFB.getReference("orderQueue/"+writeToId.toString()+"/total");
                        mRefWrTotal.setValue(total);
                        DatabaseReference mRefWrTime = mFB.getReference("orderQueue/"+writeToId.toString()+"/time");
                        currentTime = Calendar.getInstance().getTime();
                        mRefWrTime.setValue(currentTime.toString());
                        refFoodQSize = mFB.getReference("foodQueueSize");
                        Log.i("shoppingcart8","foodIds size now is "+foodIds.size());
                        refFoodQSize.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Integer foodQsize = dataSnapshot.getValue(Integer.class);
                                for (int i = 0; i < foodNames.size(); i++){
                                    Log.i("shoppingcart8","iterator now is "+i);
                                    DatabaseReference mRefWrFoods = mFB.getReference("orderQueue/"+writeToId.toString()+"/foods/"+i);
                                    mRefWrFoods.setValue(foodIds.get(i));
                                    Integer writeFoodQ = foodQsize+i+1;
                                    mFB.getReference("foodQueue/"+writeFoodQ.toString()+"/foodName").setValue(foodNames.get(i));
                                    mFB.getReference("foodQueue/"+writeFoodQ.toString()+"/foodId").setValue(foodIds.get(i));
                                    mFB.getReference("foodQueue/"+writeFoodQ.toString()+"/timeOrdered").setValue(currentTime.toString());
                                }
                                refFoodQSize.setValue(foodQsize+foodNames.size());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        mRefId.setValue(writeToId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                DatabaseReference toDeleteTemp = mFB.getReference("tempOrder");
                toDeleteTemp.removeValue();
            }
        });
        return rootView;
    }
}
