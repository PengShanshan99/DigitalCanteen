package com.istd.digitalcanteen;

import java.util.ArrayList;
//Corresponds to a record under the "OrderQueue" key of firebase database
//TODO 1. finish orderQueue
//TODO_doneByUsingTextView 1.1. make the listview in the recycler view expandable
//TODO 1.2. solve the problem of the latest food order should be displayed at the top
public class Order {
    private String time;
    private Double total;
    private ArrayList<Integer> foods;
    private boolean done;

    public Order(){}
    public Order(String time, Double total, ArrayList<Integer> foods, boolean done) {
        this.time = time;
        this.total = total;
        this.foods = foods;
        this.done = done;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ArrayList<Integer> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Integer> foods) {
        this.foods = foods;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
