package com.example.chokletbar_training99.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.chokletbar_training99.Models.Cart_Model;
import com.example.chokletbar_training99.Models.Product_Model;
import com.example.chokletbar_training99.R;
import com.example.chokletbar_training99.tools.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product_Info extends AppCompatActivity  implements Serializable {
FirebaseFirestore db=FirebaseFirestore.getInstance();
    Product_Model model;

    ImageView product_image;
    TextView product_title, product_price, product_details,product_category,product_description;
    Button add_to_cart;
    NumberPicker numberPicker;
    Cart_Model cart_model=new Cart_Model();
    List<Cart_Model>cart_models=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        get_Product_data();
        bindViews();

    }

    private void initProductData() {
        if (model != null) {
//            Glide.with(this)
//                    .load(model.getProduct_image_url())
//                    .into(product_image);
            Picasso.with(this)
                    .load(model.getProduct_image_url())
                    .fit().into(product_image);

            product_title.setText(model.getProduct_title());
            product_price.setText(model.getProduct_price()+"");
            product_description.setText(model.getProduct_description());
            product_details.setText(model.getProduct_details());
            product_category.setText(model.getProduct_category());
            add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Product_Info.this, "Add to Cart", Toast.LENGTH_SHORT).show();
                    cart_model=Utils.getCartModel(model);
                    cart_model.setQuantity(numberPicker.getValue());
                    cart_model.setProduct_total_price(numberPicker.getValue()*cart_model.getProduct_price());
//                    view_model.insert(cart_model);
                    Cart_Model.save(cart_model);
                    finish();
                }
            });

        }
    }

    private void bindViews() {
        product_image = findViewById(R.id.product_image_i);
        product_title = findViewById(R.id.product_title_i);
        product_price = findViewById(R.id.product_price_i);
        product_details = findViewById(R.id.product_details_i);
        product_category=findViewById(R.id.product_category_i);
        product_description=findViewById(R.id.product_description_i);
        add_to_cart = findViewById(R.id.add_to_cart_i);
        numberPicker=findViewById(R.id.number_picker);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);
    }

    private void get_Product_data() {

        Intent intent=getIntent();
        String product_id=intent.getStringExtra("product_id");
        db.collection(Utils.PRODUCTS_TABLE).document(product_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                model=documentSnapshot.toObject(Product_Model.class);
                initProductData();
            }
        });
    }
}