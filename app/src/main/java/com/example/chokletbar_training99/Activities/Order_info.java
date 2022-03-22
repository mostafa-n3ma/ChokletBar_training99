package com.example.chokletbar_training99.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chokletbar_training99.Adapters.Cart_Adapter;
import com.example.chokletbar_training99.Models.Cart_Model;
import com.example.chokletbar_training99.Models.Order_Model;
import com.example.chokletbar_training99.Models.User_Model;
import com.example.chokletbar_training99.R;
import com.example.chokletbar_training99.tools.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Order_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        Intent intent=getIntent();
        String order_id=intent.getStringExtra("order_id");
        if (order_id != null) {
            get_order(order_id);
        }

    }
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Order_Model order_model;
    private void get_order(String order_id) {
        db.collection(Utils.ORDERS_TABLE).document(order_id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        order_model=documentSnapshot.toObject(Order_Model.class);
                        extract_order_data(order_model);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }




    private void extract_order_data(Order_Model order_model) {
        User_Model user_model=order_model.getUser_model();
        List<Cart_Model>order_cart_list=order_model.getCart_list();
        String user_name,user_phone,user_address,extra_phone,extra_address,details,order_total;
        user_name=user_model.getFirst_name()+" "+user_model.getLast_name();
        user_phone=user_model.getPhone();
        user_address=user_model.getAddress();
        extra_phone=order_model.getExtra_phone_number();
        extra_address=order_model.getExtra_address();
        details= order_model.getOrder_details();
        order_total=order_model.getOrder_total_price();
        bindViews(user_name,user_phone,user_address,extra_phone,extra_address,details,order_total,order_cart_list);
        check_conformed(order_model);
    }

    private void check_conformed(Order_Model order_model) {
        Button order_conform_btn=(Button) findViewById(R.id.order_conform);
        if (order_model.isConformed()) {
            order_conform_btn.setVisibility(View.GONE);
        }else {
            order_conform_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    order_model.setConformed(true);
                    db.collection(Utils.ORDERS_TABLE).document(order_model.getOrder_id())
                            .update("conformed",true)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Order_info.this, "order conformed", Toast.LENGTH_SHORT).show();

                                    finish();
                                }
                            });
                }
            });
        }
    }


    private TextView user_name_TV,user_phone_TV,user_address_TV,extra_phone_TV,extra_address_TV,details_TV,order_total_TV;
    private RecyclerView orderRecyclerView;
    private Cart_Adapter cart_adapter;
    private ImageButton close_order_info;
    private void bindViews(String user_name, String user_phone, String user_address, String extra_phone, String extra_address, String details, String order_total, List<Cart_Model> order_cart_list) {

        close_order_info=findViewById(R.id.close_order_info);
        close_order_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        user_name_TV=findViewById(R.id.order_user_name);
        user_name_TV.setText(user_name);
        user_phone_TV=findViewById(R.id.order_user_number);
        user_phone_TV.setText(user_phone);
        user_address_TV=findViewById(R.id.order_user_address);
        user_address_TV.setText(user_address);
        extra_phone_TV=findViewById(R.id.order_user_number_extra);
        extra_phone_TV.setText(extra_phone);
        extra_address_TV=findViewById(R.id.order_user_address_extra);
        extra_address_TV.setText(extra_address);
        details_TV=findViewById(R.id.order_details_o);
        details_TV.setText(details);
        order_total_TV=findViewById(R.id.order_total_o);
        order_total_TV.setText(order_total);


        orderRecyclerView=findViewById(R.id.order_info_res);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setHasFixedSize(true);
        cart_adapter=new Cart_Adapter(Order_info.this,order_cart_list,Utils.CART_ORDER_VIEW);
        orderRecyclerView.setAdapter(cart_adapter);

    }


}