package com.example.chokletbar_training99.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.chokletbar_training99.Activities.Order_info;
import com.example.chokletbar_training99.Models.Order_Model;
import com.example.chokletbar_training99.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Orders_Adapter extends RecyclerView.Adapter<Orders_Adapter.orderHolder> {
    private Context context;
    private List<Order_Model> orders_list;


    public Orders_Adapter(Context context, List<Order_Model> orders_list) {
        this.context = context;
        this.orders_list = orders_list;
    }

    @NonNull
    @Override
    public orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        return new orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderHolder holder, int position) {
        Order_Model order_model=orders_list.get(position);
        String user_image_url=order_model.getUser_model().getProfile_image_Url();
//        Glide.with(context)
//                .load(user_image_url)
//                .into(holder.user_Image);
        Picasso.with(context)
                .load(user_image_url)
                .into(holder.user_Image);


        holder.total.setText(order_model.getOrder_total_price()+"د.ع");
        holder.date_time.setText(order_model.getOrder_Date());


    }

    @Override
    public int getItemCount() {
        return orders_list.size();
    }

    public class orderHolder extends RecyclerView.ViewHolder {
        private ImageView user_Image;
        private TextView total,date_time;
        public orderHolder(@NonNull View itemView) {
            super(itemView);
            user_Image=itemView.findViewById(R.id.order_user_image);
            total=itemView.findViewById(R.id.order_total);
            date_time=itemView.findViewById(R.id.date_time_field);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Order_Model model=orders_list.get(position);
                    Intent intent =new Intent(context, Order_info.class);
                    intent.putExtra("order_id",model.getOrder_id());
                    context.startActivity(intent);
                }
            });
        }
    }
}
