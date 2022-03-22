package com.example.chokletbar_training99.Models;

import java.util.List;

public class Order_Model {


    private String order_id;
    private User_Model user_model;
    private String extra_address,
            extra_phone_number,
            order_details,
            Order_Date;
    private List<Cart_Model> cart_list;
    private String order_total_price;
    private boolean conformed;

    public boolean isConformed() {
        return conformed;
    }

    public void setConformed(boolean conformed) {
        this.conformed = conformed;
    }

    public Order_Model() {
    }

    public Order_Model(User_Model user_model, String extra_address, String extra_phone_number, String order_details, String order_Date, List<Cart_Model> cart_list, String order_total_price) {
        this.user_model = user_model;
        this.extra_address = extra_address;
        this.extra_phone_number = extra_phone_number;
        this.order_details = order_details;
        Order_Date = order_Date;
        this.cart_list = cart_list;
        this.order_total_price = order_total_price;
    }


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public User_Model getUser_model() {
        return user_model;
    }

    public void setUser_model(User_Model user_model) {
        this.user_model = user_model;
    }

    public String getExtra_address() {
        return extra_address;
    }

    public void setExtra_address(String extra_address) {
        this.extra_address = extra_address;
    }

    public String getExtra_phone_number() {
        return extra_phone_number;
    }

    public void setExtra_phone_number(String extra_phone_number) {
        this.extra_phone_number = extra_phone_number;
    }

    public String getOrder_details() {
        return order_details;
    }

    public void setOrder_details(String order_details) {
        this.order_details = order_details;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public List<Cart_Model> getCart_list() {
        return cart_list;
    }

    public void setCart_list(List<Cart_Model> cart_list) {
        this.cart_list = cart_list;
    }

    public String getOrder_total_price() {
        return order_total_price;
    }

    public void setOrder_total_price(String order_total_price) {
        this.order_total_price = order_total_price;
    }
}
