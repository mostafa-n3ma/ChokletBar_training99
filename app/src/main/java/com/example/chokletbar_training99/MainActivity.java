package com.example.chokletbar_training99;

import static com.example.chokletbar_training99.tools.Rec_helper.init_main_res;
import static com.example.chokletbar_training99.tools.Rec_helper.init_h_res;
import static com.example.chokletbar_training99.tools.my_tool.click_cart_btn;
import static com.example.chokletbar_training99.tools.my_tool.closeDrawer;
import static com.example.chokletbar_training99.tools.my_tool.initiate_drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chokletbar_training99.Activities.Sign_In;

import com.example.chokletbar_training99.Models.Product_Model;
import com.example.chokletbar_training99.tools.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {


    private static boolean is_admin= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Utils.loged_in()) {
            Utils.go_online(this,true);
            if (Utils.Chick_Admin_permission()){
                is_admin=true;
            }

        } else {
            Intent intent = new Intent(MainActivity.this, Sign_In.class);
            startActivity(intent);
        }
        initiate_drawer(this, true, false, false, false, is_admin);
        click_cart_btn(this);
        getAllProducts();
    }


    private void initpruducts() {
        init_main_res(this,finalProducts);
        init_h_res(this,finalProducts);
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Product_Model> finalProducts = new ArrayList<>();

    public void getAllProducts() {
        db.collection(Utils.PRODUCTS_TABLE).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                finalProducts = queryDocumentSnapshots.toObjects(Product_Model.class);
                initpruducts();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "getting products is not working", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.droewr_layout);
        closeDrawer(drawerLayout);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        click_cart_btn(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.go_online(this,false);
        Toast.makeText(this, "on destroy", Toast.LENGTH_SHORT).show();
    }

}