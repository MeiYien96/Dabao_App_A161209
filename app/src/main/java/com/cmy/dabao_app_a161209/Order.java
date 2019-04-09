package com.cmy.dabao_app_a161209;

public class Order {
    String driverUid,driverName,hunterUid, hunterName, from, to;
    Double foodPrice, deliveryFee, total;

    public Order(String driverUid, String driverName, String hunterUid, String hunterName, String from, String to, Double foodPrice, Double deliveryFee, Double total) {
        this.driverUid = driverUid;
        this.driverName = driverName;
        this.hunterUid = hunterUid;
        this.hunterName = hunterName;
        this.from = from;
        this.to = to;
        this.foodPrice = foodPrice;
        this.deliveryFee = deliveryFee;
        this.total = total;
    }

    public String getDriverUid() {
        return driverUid;
    }

    public void setDriverUid(String driverUid) {
        this.driverUid = driverUid;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getHunterUid() {
        return hunterUid;
    }

    public void setHunterUid(String hunterUid) {
        this.hunterUid = hunterUid;
    }

    public String getHunterName() {
        return hunterName;
    }

    public void setHunterName(String hunterName) {
        this.hunterName = hunterName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
