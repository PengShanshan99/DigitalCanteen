package com.istd.digitalcanteen;

public class Food {
    //private String id;//Do I need ID as an attribute actually?
    private String name;
    private String price;
    private String prepTime;
    private String availability;
    private String photoUri;

    public Food(String id, String stall, String name, String price, String prepTime, String availability, String photoUri) {
        //this.id = id;
        this.name = name;
        this.price = price;
        this.prepTime = prepTime;
        this.availability = availability;
    }
    public Food(){}

//    public String getId() {
//        return id;
//    }

//    public void setId(String id) {
//        this.id = id;
//    }

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
