package com.example.chokletbar_training99.tools;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.chokletbar_training99.MainActivity;
import com.example.chokletbar_training99.Models.Cart_Model;
import com.example.chokletbar_training99.Models.Order_Model;
import com.example.chokletbar_training99.Models.Product_Model;
import com.example.chokletbar_training99.Models.User_Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Utils {

    public static final String USERS_TABLE = "users_table";
    public static final String PRODUCTS_TABLE = "products_table";
    public static final String ORDERS_TABLE = "orders_table";
    public static final String CART_VIEW="CART";
    public static final String CART_ORDER_VIEW="CART_ORDER";


    public static Cart_Model getCartModel(Product_Model product_model) {
        Cart_Model cart_model = new Cart_Model();
        cart_model.setProduct_id(product_model.getProduct_id());
        cart_model.setProduct_image_url(product_model.getProduct_image_url());
        cart_model.setProduct_title(product_model.getProduct_title());
        cart_model.setProduct_details(product_model.getProduct_details());
        cart_model.setProduct_description(product_model.getProduct_description());
        cart_model.setProduct_category(product_model.getProduct_category());
        cart_model.setProduct_price(product_model.getProduct_price());
        return cart_model;
    }

    public static boolean loged_in() {

        if (User_Model.listAll(User_Model.class).size() == 1) {
            return true;
        }
        return false;
    }

    public static User_Model get_Logged_user() {
        User_Model model = User_Model.listAll(User_Model.class).get(0);
        return model;
    }

    public static boolean Chick_Admin_permission(){
        boolean is_admin= get_Logged_user().isIs_admin();
        return is_admin;
    }
    public static void go_online(Activity activity,boolean is_online){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection(Utils.USERS_TABLE)
                .document(get_Logged_user().getFirst_name()+" "+get_Logged_user().getLast_name())
                .update("is_online",is_online)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
    }
    static List<User_Model>user_modelList;
    public static int online_users(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        db.collection(USERS_TABLE).whereEqualTo("is_online",true).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        user_modelList=queryDocumentSnapshots.toObjects(User_Model.class);

                    }
                });
        int online_count=user_modelList.size();
        return online_count;
    }

    public static Order_Model create_order(String order_id,String extra_address,String extra_phone_number,String order_details,String total ) {
        Order_Model order = new Order_Model();
        order.setOrder_id(order_id);
        order.setUser_model(get_Logged_user());
        order.setCart_list(Cart_Model.listAll(Cart_Model.class));
        order.setOrder_total_price(total);
        order.setExtra_address(extra_address);
        order.setExtra_phone_number(extra_phone_number);
        order.setOrder_details(order_details);
        order.setOrder_Date(get_current_date_time());
        return order;
    }

    public static String get_current_date_time(){
        DateFormat df = new SimpleDateFormat(" dd-MM-yyyy, h:mm a");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }


    public static List<Order_Model>split_orders(List<Order_Model>list,boolean isConformed){
        List<Order_Model>conformed_list = new ArrayList<>();
        List<Order_Model>not_conformed_list = new ArrayList<>();
        for (Order_Model order :
                list) {
            if (order.isConformed()) {
                conformed_list.add(order);
            }else {
                not_conformed_list.add(order);
            }
        }
        if (isConformed) {
            return conformed_list;
        }else {
            return not_conformed_list;
        }
    }



}
