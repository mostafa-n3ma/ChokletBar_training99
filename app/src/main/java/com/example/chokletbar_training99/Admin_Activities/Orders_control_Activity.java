package com.example.chokletbar_training99.Admin_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chokletbar_training99.Adapters.Orders_Adapter;
import com.example.chokletbar_training99.Models.Order_Model;
import com.example.chokletbar_training99.R;
import com.example.chokletbar_training99.tools.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Orders_control_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_control);
        getOrders();

    }




    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ProgressDialog pg;
    private void getOrders() {
        pg=new ProgressDialog(Orders_control_Activity.this);
        pg.setTitle("جاري تحميل الطلبات...");
        pg.setCancelable(false);
        pg.show();
        db.collection(Utils.ORDERS_TABLE).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        orders=queryDocumentSnapshots.toObjects(Order_Model.class);
                        bindViews();
                        pg.hide();
                        pg.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Orders_control_Activity.this, "can't get the orders", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private RecyclerView recyclerView,recyclerView2;
    private List<Order_Model>orders=new ArrayList<>();
    private Orders_Adapter orders_adapter,orders_adapter2;
    private ImageButton close_order_Activity_btn;


    private void bindViews() {
        List<Order_Model>conformed_list=Utils.split_orders(orders,true);
        List<Order_Model>not_conformed_list=Utils.split_orders(orders,false);

        orders_adapter=new Orders_Adapter(this,not_conformed_list);
        recyclerView=findViewById(R.id.orders_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(orders_adapter);


        orders_adapter2=new Orders_Adapter(this,conformed_list);
        recyclerView2=findViewById(R.id.order_res2);
        recyclerView2.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setAdapter(orders_adapter2);





        close_order_Activity_btn=findViewById(R.id.close_order_Activity_btn);
        close_order_Activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(Orders_control_Activity.this, "on reestart", Toast.LENGTH_SHORT).show();
        getOrders();
    }
}