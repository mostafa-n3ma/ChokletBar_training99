package com.example.chokletbar_training99.Models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

//@Entity(tableName = "cart_table")
public class Cart_Model extends SugarRecord {

    @Unique
    private String product_id;
    private String Publisher_id
            ,product_image_url
            ,product_title
            ,product_details
            , product_description
            ,product_category;

    private int  product_price;
    private int quantity ;
    private  int product_total_price ;

    public Cart_Model() {
    }



    public Cart_Model(String product_id, String publisher_id, String product_image_url, String product_title, String product_details, String product_description, String product_category, int product_price, int quantity, int product_total_price) {
        this.product_id = product_id;
        Publisher_id = publisher_id;
        this.product_image_url = product_image_url;
        this.product_title = product_title;
        this.product_details = product_details;
        this.product_description = product_description;
        this.product_category = product_category;
        this.product_price = product_price;
        this.quantity = quantity;
        this.product_total_price = product_total_price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPublisher_id() {
        return Publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        Publisher_id = publisher_id;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_details() {
        return product_details;
    }

    public void setProduct_details(String product_details) {
        this.product_details = product_details;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getProduct_total_price() {
        return product_total_price;
    }

    public void setProduct_total_price(int product_total_price) {
        this.product_total_price = product_total_price;
    }


}
