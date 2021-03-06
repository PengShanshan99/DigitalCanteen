package com.istd.digitalcanteen;
//This class corresponds to a record under the "FoodQueue" key in firebase database/
//This is to be displayed on the RecyclerView for the FoodCookingFragment
public class FoodQ {
    private String timeOrdered;
    private String foodName;
    private Integer foodId;

    public FoodQ(String timeOrdered, String foodName, Integer foodId) {
        this.timeOrdered = timeOrdered;
        this.foodName = foodName;
        this.foodId = foodId;
    }

    public FoodQ(){}

    public String getTimeOrdered() {
        return timeOrdered;
    }

    public void setTimeOrdered(String timeOrdered) {
        this.timeOrdered = timeOrdered;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }
}
