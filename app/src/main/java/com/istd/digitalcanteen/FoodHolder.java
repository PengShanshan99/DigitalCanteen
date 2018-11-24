package com.istd.digitalcanteen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FoodHolder extends RecyclerView.ViewHolder {
    View mView;
    public FoodHolder(View itemView){
        super(itemView);
        mView = itemView;
    }

    public void setDetails(Context ctx, String name, String price, String prepTime, String availability){
        TextView textViewName = mView.findViewById(R.id.cardview_food_name);
        TextView textViewPrice = mView.findViewById(R.id.cardview_food_price);
        TextView textViewPrepTime = mView.findViewById(R.id.cardview_food_preptime);
        TextView textViewAvailability = mView.findViewById(R.id.cardview_food_availability);
        textViewName.setText(name);
        textViewPrice.setText("price: "+price);
        textViewPrepTime.setText("estimated preparation time: "+prepTime);
        textViewAvailability.setText("availability: "+availability);
    }


}

