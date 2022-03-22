package com.example.chokletbar_training99.Admin_Activities;

import static com.example.chokletbar_training99.tools.my_tool.initiate_drawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chokletbar_training99.Activities.Sign_In;
import com.example.chokletbar_training99.MainActivity;
import com.example.chokletbar_training99.Models.User_Model;
import com.example.chokletbar_training99.R;
import com.example.chokletbar_training99.tools.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Dashboard extends AppCompatActivity {
    private static boolean is_admin= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        if (Utils.loged_in()){
            is_admin=Utils.Chick_Admin_permission();
        }
        initiate_drawer(this, false, false, false, true, is_admin);
        bindViews();

    }
    ImageView add_product,orders,onlin_users_btn;
    TextView online_users;
    private void bindViews() {
        add_product=findViewById(R.id.add_product_btn);
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,Add_product.class);
                startActivity(intent);
            }
        });
        orders=findViewById(R.id.orders_btn);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,Orders_control_Activity.class);
                startActivity(intent);
            }
        });


        online_users=findViewById(R.id.online_users);
        online_users.setVisibility(View.INVISIBLE);
        onlin_users_btn=findViewById(R.id.online_users_btn);
        onlin_users_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_online_users();
            }
        });





    }
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private void get_online_users() {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                db.collection(Utils.USERS_TABLE).whereEqualTo("is_online",true).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<User_Model>online_list=queryDocumentSnapshots.toObjects(User_Model.class);
                                online_users.setVisibility(View.VISIBLE);
                                online_users.setText(online_list.size()+"");
                            }
                        });
            }
        });
        t.start();
    }

}