package com.istd.digitalcanteen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdapterTrackingOrders extends RecyclerView.Adapter<StudentOrderHolder> {
    List<Order> orders;
    Context context;

    public AdapterTrackingOrders(List<Order> list, Context context) {
        this.orders = list;
        this.context = context;
        //Log.i("adapterOrder","orders received by AdapterOrder is "+orders.toString());

    }

    public StudentOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //public  RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_track_orders, viewGroup, false);
        StudentOrderHolder studentOrderHolder = new StudentOrderHolder(view);
        return studentOrderHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentOrderHolder studentOrderHolder, int position) {
        Order anotherOrder = orders.get(position);
        studentOrderHolder.setDetails(context, anotherOrder.getTime(), anotherOrder.getFoods(), anotherOrder.isDone());
        studentOrderHolder.setId(position);
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try {
            arr = orders.size();
        } catch (Exception e) {
        }
        return arr;
    }
}
