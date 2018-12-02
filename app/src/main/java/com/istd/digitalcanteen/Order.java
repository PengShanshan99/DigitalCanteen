package com.istd.digitalcanteen;

import org.json.JSONArray;

import java.util.ArrayList;
//TODO 1. finish orderQueue
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

    public void addFood(Integer foodId){
        this.foods.add(foodId);
    }

}
