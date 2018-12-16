package com.istd.digitalcanteen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
//This is an Adapter to display orders onto RecyclerView of OrderViewingFragment of the stall side
public class AdapterOrders extends RecyclerView.Adapter<OrderHolder> {
    List<Order> orders;
    Context context;

    public AdapterOrders(List<Order> list, Context context) {
        this.orders = list;
        this.context = context;
    }

    public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_orders, viewGroup, false);
        Log.i("adapter", "view is inflated!");
        OrderHolder orderHolder = new OrderHolder(view);
        return orderHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder orderHolder, int position) {
        Log.i("adapterhay", "onBindViewHolder is called!");
        Order anotherOrder = orders.get(position);
        Log.i("setid", "id is set in onBindViewHolder");
        orderHolder.setDetails(context, anotherOrder.getTime(), anotherOrder.getFoods(), anotherOrder.isDone());
        //todo 1.4 instead of directly using position, try to use the actual id on firebase (now is because they are coincidentally the same.)
        orderHolder.setId(position);
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

