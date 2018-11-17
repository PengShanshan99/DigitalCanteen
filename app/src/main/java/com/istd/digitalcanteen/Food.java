package com.istd.digitalcanteen;

public class Food {
    String id;
    String stall;
    String name;
    String price;
    String prepTime;
    boolean availability;
    public void setId(String id){
        this.id = id;
    }
    public void setStall(String stall){
        this.stall = stall;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public void setAvailability(boolean availability){
        this.availability = availability;
    }
    public void setPrepTime(String time){
        this.prepTime = time;
    }
}
