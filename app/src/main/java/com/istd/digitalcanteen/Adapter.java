package com.istd.digitalcanteen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<FoodHolder> {
    List<Food> list;
    Context context;

    public Adapter(List<Food> list, Context context) {
        this.list = list;
        this.context = context;
        Log.i("adapter","Adapter created!");
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, viewGroup, false);
        Log.i("adapter","view is inflated!");
        FoodHolder foodHolder = new FoodHolder(view);
        return foodHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder foodHolder, int i) {
        Log.i("adapter","onBindViewHolder is called!");
        Food anotherFood = list.get(i);
        foodHolder.setDetails(context, anotherFood.getName(), anotherFood.getPrice(), anotherFood.getPrepTime(), anotherFood.getAvailability());
    }



    @Override
    public int getItemCount() {
        int arr = 0;
        try {
//            if(list.size() == 0){
//                arr = 0;
//            }else{
//                arr = list.size();
            arr = list.size();
        } catch (Exception e) {

        }
        return arr;
    }
}


