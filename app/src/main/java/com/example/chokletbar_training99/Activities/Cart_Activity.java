package com.example.chokletbar_training99.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Cart_Activity extends AppCompatActivity {

    //    private View_Model view_model;
    private List<Cart_Model> cart_List = new ArrayList<>();
    private Cart_Adapter cart_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //        check logging
        if (!Utils.loged_in()) {
            Toast.makeText(Cart_Activity.this, "قم بتسجيل الدخول اولا او انشيء حساب", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            Toast.makeText(Cart_Activity.this, "مرحبا:" + User_Model.listAll(User_Model.class).get(0).getFirst_name(), Toast.LENGTH_SHORT).show();
        }


        bideViews();

        cart_List = Cart_Model.listAll(Cart_Model.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cart_Activity.this));
        recyclerView.setHasFixedSize(true);
        cart_adapter = new Cart_Adapter(Cart_Activity.this, cart_List, Utils.CART_VIEW);
        recyclerView.setAdapter(cart_adapter);
        Cart_Activity.order_total_price.setText(String.valueOf(cart_adapter.getTotal()));

    }

    TextInputLayout extra_address_field, extra_phone_number_field, order_details_field;
    public static TextView order_total_price;
    ImageButton close_cart_btn;
    Button sumbit_btn;
    private RecyclerView recyclerView;

    private void bideViews() {
        recyclerView = findViewById(R.id.cart_res);
        close_cart_btn = findViewById(R.id.close_cart_btn);
        order_total_price = findViewById(R.id.order_total_price);
        extra_address_field = findViewById(R.id.extra_address);
        extra_phone_number_field = findViewById(R.id.extra_phone_number);
        order_details_field = findViewById(R.id.order_details);
        sumbit_btn = findViewById(R.id.sumbit_btn);
        sumbit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });
        close_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void createOrder() {
        String order_total_price_txt, extra_address, extra_phone_number, order_details;
        extra_address = extra_address_field.getEditText().getText().toString();
        extra_phone_number = extra_phone_number_field.getEditText().getText().toString();
        order_total_price_txt = order_total_price.getText().toString();
        order_details = order_details_field.getEditText().getText().toString();
        String order_id = db.collection(Utils.ORDERS_TABLE).document().getId();

        Order_Model order_model = Utils.create_order(order_id, extra_address, extra_phone_number, order_details, order_total_price_txt);
        submit_Order(order_model);


    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog pd;

    private void submit_Order(Order_Model order_model) {
        pd = new ProgressDialog(this);
        pd.setTitle("جاري تقديم الطلب.....");
        pd.setCancelable(false);
        pd.show();
        db.collection(Utils.ORDERS_TABLE).document(order_model.getOrder_id()).set(order_model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Cart_Activity.this, "تم تسجيل الطلب بنجاح", Toast.LENGTH_SHORT).show();
                        pd.hide();
                        pd.dismiss();
                        Cart_Model.deleteAll(Cart_Model.class);
                        finish();
                        return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }


}