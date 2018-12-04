package com.istd.digitalcanteen;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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

import java.util.List;

public class AdapterSC extends RecyclerView.Adapter<SCHolder> {
    List<String> foodNames;
    Context context;
    String anotherFoodName;
    DatabaseReference mRef;

    public AdapterSC(List<String> list, Context context) {
        this.foodNames = list;
        Log.i("shoppingcart","the list received by adapter is "+list.toString());
        this.context = context;
    }
    public SCHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_shopping_cart, viewGroup, false);
        SCHolder scHolder = new SCHolder(view);
        return scHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull SCHolder scHolder, int position) {
        String anotherFoodName = foodNames.get(position);
        scHolder.setDetails(context, anotherFoodName);

    }
    @Override
    public int getItemCount() {
        int arr = 0;
        try {
            arr = foodNames.size();
        } catch (Exception e) {
        }
        return arr;
    }

}
