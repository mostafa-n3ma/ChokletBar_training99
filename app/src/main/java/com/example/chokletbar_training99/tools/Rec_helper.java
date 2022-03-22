package com.example.chokletbar_training99.tools;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chokletbar_training99.Activities.Product_Info;
import com.example.chokletbar_training99.Adapters.Products_Adapter;
import com.example.chokletbar_training99.Models.Product_Model;
import com.example.chokletbar_training99.R;

import java.util.List;

public class Rec_helper {


    public static void init_main_res(Activity activity, List<Product_Model>finalProducts) {
        RecyclerView main_res = (RecyclerView) activity.findViewById(R.id.main_rec);
        main_res.setLayoutManager(new GridLayoutManager(activity, 2));
        main_res.setHasFixedSize(true);
        Products_Adapter adapter = new Products_Adapter(activity, finalProducts);
        main_res.setAdapter(adapter);
        adapter.setOnProductClickListener(new Products_Adapter.OnProductClickListner() {
            @Override
            public void onProductClick(Product_Model model, int position) {
                Intent intent = new Intent(activity, Product_Info.class);
                intent.putExtra("product_id", model.getProduct_id());
                activity.startActivity(intent);
            }
        });


    }

        public static void init_h_res(Activity activity, List<Product_Model>finalProducts) {
            RecyclerView h_rec=(RecyclerView) activity.findViewById(R.id.h_rec);
            h_rec.setLayoutManager(new LinearLayoutManager(
                    activity,LinearLayoutManager.HORIZONTAL,false
            ));

            h_rec.setHasFixedSize(true);
            Products_Adapter adapter = new Products_Adapter(activity, finalProducts);
            h_rec.setAdapter(adapter);
            adapter.setOnProductClickListener(new Products_Adapter.OnProductClickListner(){
                @Override
                public void onProductClick(Product_Model model, int position) {
                    Intent intent = new Intent(activity, Product_Info.class);
                    intent.putExtra("product_id", model.getProduct_id());
                    activity.startActivity(intent);
                }
            });

            //التدوير التلقائي
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    int length=finalProducts.size();
                    for (int i=0;i<=length;i++){
                        try {
                            Thread.sleep(2000);
                              //التدوير التلقائي
                            h_rec.smoothScrollToPosition(i);
                            if (i == length) {
                                for (int j=length;j>=0;j--){
                                    h_rec.smoothScrollToPosition(j);
                                    Thread.sleep(2000);
                                }
                                i=0;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            t.start();

        }


    public static void showPopup(Activity activity, View view) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        popupMenu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) activity);
        popupMenu.inflate(R.menu.long_click_menu);
        popupMenu.show();
    }

}


//
//
//adapter.setOnProductLongClickListener(new Products_Adapter.OnProductLongClickListener() {
//@Override
//public void onProductLongClick(Product_Model model, int position, View lyt_parent) {
//        showPopup(activity,lyt_parent);
//        }
//        });
