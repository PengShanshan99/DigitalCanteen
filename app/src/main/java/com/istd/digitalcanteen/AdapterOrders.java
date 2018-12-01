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

public class AdapterOrders extends RecyclerView.Adapter<OrderHolder> {
//public class AdapterOrders extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Order> orders;
    Context context;

    public AdapterOrders(List<Order> list, Context context) {
        this.orders = list;
        this.context = context;
        //Log.i("adapterOrder","orders received by AdapterOrder is "+orders.toString());

    }
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    //public  RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_orders, viewGroup, false);
        Log.i("adapter","view is inflated!");
        OrderHolder orderHolder = new OrderHolder(view);
        return orderHolder;
//        switch (viewType) {
//            case 1:
//                return new Foo1ViewHolder(LayoutInflater.from(context).inflate(R.layout.foo1_layout, viewGroup, false));
//
//            case 2:
//                return new Foo2ViewHolder(LayoutInflater.from(context).inflate(R.layout.foo2_layout, viewGroup, false));
//
//            default:
//                return null;
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder orderHolder, int position) {
    //public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i("adapterhay","onBindViewHolder is called!");
        Order anotherOrder = orders.get(position);
        Log.i("setid","id is set in onBindViewHolder");
        orderHolder.setDetails(context, anotherOrder.getTime(), anotherOrder.getFoods());
//        switch (the_view_types.get(position)) {
//            case 1:
//                Foo1ViewHolder foo1ViewHolder = (Foo1ViewHolder) holder;
//                foo1ViewHolder.setText(the_strings.get(position).get(0));
//                break;
//
//            case 2:
//                Foo2ViewHolder foo2ViewHolder = (Foo2ViewHolder) holder;
//                foo2ViewHolder.setText(the_strings.get(position));
//                break;
//        }
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

//    public int getItemViewType(int position) {
//        return the_view_types.get(position);
//    }
//
//    public class Foo1ViewHolder extends RecyclerView.ViewHolder {
//        private TextView tvFoo1Value;
//
//        public Foo1ViewHolder(View itemView) {
//            super(itemView);
//            tvFoo1Value = (TextView) itemView.findViewById(R.id.foo1);
//        }
//
//        public void setText(String text) {
//            tvFoo1Value.setText(text);
//        }
//    }
//
//    public class Foo2ViewHolder extends RecyclerView.ViewHolder {
//        private TextView tvFoo2Value;
//
//        public Foo2ViewHolder(View itemView) {
//            super(itemView);
//            tvFoo2Value = (TextView) itemView.findViewById(R.id.foo2);
//        }
//
//        public void  setText(String text) {
//            tvFoo2Value.setText(text);
//        }
//    }
//
}
