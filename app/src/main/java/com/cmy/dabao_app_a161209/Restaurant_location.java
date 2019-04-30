package com.cmy.dabao_app_a161209;

import android.widget.Button;

public class Restaurant_location {
    String username,restaurantName,foodTag1,foodTag2,profilePic;
    Button btnOrder;

    public Restaurant_location(){

    }

    public Restaurant_location(String username, String restaurantName, String foodTag1, String foodTag2, String profilePic, Button btnOrder) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.foodTag1 = foodTag1;
        this.foodTag2 = foodTag2;
        this.profilePic = profilePic;
        this.btnOrder = btnOrder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getFoodTag1() {
        return foodTag1;
    }

    public void setFoodTag1(String foodTag1) {
        this.foodTag1 = foodTag1;
    }

    public String getFoodTag2() {
        return foodTag2;
    }

    public void setFoodTag2(String foodTag2) {
        this.foodTag2 = foodTag2;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Button getBtnOrder() {
        return btnOrder;
    }

    public void setBtnOrder(Button btnOrder) {
        this.btnOrder = btnOrder;
    }
}
