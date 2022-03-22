package com.example.chokletbar_training99.Models;

import com.orm.SugarRecord;

public class User_Model extends SugarRecord {

    private String
            UserId,
            first_name,
            last_name,
            email,
            Phone,
            Password,
            profile_image_Url,
            address,
            gps_lat,
            gps_long,
            Registration_Date;
    private boolean is_online;
    private boolean is_admin=false;

    public User_Model() {
    }

    public User_Model(String first_name, String last_name, String email, String phone, String password, String profile_image_Url, String address, String gps_lat, String gps_long, String registration_Date) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        Phone = phone;
        Password = password;
        this.profile_image_Url = profile_image_Url;
        this.address = address;
        this.gps_lat = gps_lat;
        this.gps_long = gps_long;
        Registration_Date = registration_Date;
    }

    public User_Model(String userId, String first_name, String last_name, String email, String phone, String password, String profile_image_Url, String address, String gps_lat, String gps_long, String registration_Date) {
        UserId = userId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        Phone = phone;
        Password = password;
        this.profile_image_Url = profile_image_Url;
        this.address = address;
        this.gps_lat = gps_lat;
        this.gps_long = gps_long;
        Registration_Date = registration_Date;

    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getProfile_image_Url() {
        return profile_image_Url;
    }

    public void setProfile_image_Url(String profile_image_Url) {
        this.profile_image_Url = profile_image_Url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGps_lat() {
        return gps_lat;
    }

    public void setGps_lat(String gps_lat) {
        this.gps_lat = gps_lat;
    }

    public String getGps_long() {
        return gps_long;
    }

    public void setGps_long(String gps_long) {
        this.gps_long = gps_long;
    }

    public String getRegistration_Date() {
        return Registration_Date;
    }

    public void setRegistration_Date(String registration_Date) {
        Registration_Date = registration_Date;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }
}

