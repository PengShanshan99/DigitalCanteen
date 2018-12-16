package com.istd.digitalcanteen;
//Food class
public class Food {
    private String name;
    private String price;
    private String prepTime;
    private String availability;

    public Food(String id, String stall, String name, String price, String prepTime, String availability, String photoUri) {
        this.name = name;
        this.price = price;
        this.prepTime = prepTime;
        this.availability = availability;
    }
    public Food(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
