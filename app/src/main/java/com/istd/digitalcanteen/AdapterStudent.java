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

import java.util.ArrayList;
import java.util.List;
//this is an adapter used to display firebase database lively on MenuFragments in MainActivity
public class   AdapterStudent extends RecyclerView.Adapter<FoodHolderStudent> {
    List<Food> list;
    Context context;
    ArrayList<Integer> list_of_id;

    public AdapterStudent(List<Food> list, Context context, ArrayList<Integer> list_of_id) {
        this.list = list;
        this.context = context;
        this.list_of_id = list_of_id;
        Log.i("adapter","Adapter created!");
    }

    @NonNull
    @Override
    public FoodHolderStudent onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, viewGroup, false);
        Log.i("adapter","view is inflated!");
        FoodHolderStudent foodHolderStudent = new FoodHolderStudent(view);
        return foodHolderStudent;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolderStudent foodHolderStudent, int position) {
        Log.i("adapterhay","onBindViewHolder is called!");
        Food anotherFood = list.get(position);
        foodHolderStudent.setId(list_of_id.get(position));
        Log.i("setid","id is set in onBindViewHolder");
        foodHolderStudent.setDetails(context, anotherFood.getName(), anotherFood.getPrice(), anotherFood.getPrepTime(), anotherFood.getAvailability());
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


