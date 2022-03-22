package com.example.chokletbar_training99.Models;

public class Product_Model {

    private String
            product_id,
            Publisher_id,
            Publish_date,
            product_image_url,
            product_title,
            product_details,
            product_description,
            product_category;

    private int product_price,
                Product_score;

    private  boolean available;


    public Product_Model() {
    }

    public Product_Model(String publish_date, String product_image_url, String product_title, String product_details, String product_description, String product_category, int product_price, int product_score, boolean available) {
        Publish_date = publish_date;
        this.product_image_url = product_image_url;
        this.product_title = product_title;
        this.product_details = product_details;
        this.product_description = product_description;
        this.product_category = product_category;
        this.product_price = product_price;
        Product_score = product_score;
        this.available = available;
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

    public String getPublish_date() {
        return Publish_date;
    }

    public void setPublish_date(String publish_date) {
        Publish_date = publish_date;
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

    public int getProduct_score() {
        return Product_score;
    }

    public void setProduct_score(int product_score) {
        Product_score = product_score;
    }

    public boolean Is_available() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
