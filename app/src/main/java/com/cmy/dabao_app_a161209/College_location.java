package com.cmy.dabao_app_a161209;

public class College_location {
    String collegeName,driverUid,username;

    public College_location(){

    }

    public College_location(String collegeName, String driverUid, String username) {
        this.collegeName = collegeName;
        this.driverUid = driverUid;
        this.username = username;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getDriverUid() {
        return driverUid;
    }

    public void setDriverUid(String driverUid) {
        this.driverUid = driverUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
