package com.example.chokletbar_training99.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.chokletbar_training99.Activities.Cart_Activity;
import com.example.chokletbar_training99.Activities.Product_Info;
import com.example.chokletbar_training99.MainActivity;
import com.example.chokletbar_training99.Models.Cart_Model;
import com.example.chokletbar_training99.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.CartHolder> {
    private OnCartClickListener9 listener9;
    private Context mContext;
    private List<Cart_Model>list;
    private String view;
    private int total;
    public Cart_Adapter(Context mContext, List<Cart_Model> list,String view1) {
        this.mContext =  mContext;
        this.list = list;
        this.view=view1;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if (view.equals("CART")){
             itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_cart, parent, false);

        }else if (view.equals("CART_ORDER")){
             itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_item_product, parent, false);
        }
        return new CartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart_Model current_cart=list.get(position);
       holder.product_title.setText(current_cart.getProduct_title());
       holder.product_total_price.setText(String.valueOf(current_cart.getProduct_total_price()));
       holder.quantity.setText(String.valueOf(current_cart.getQuantity()));
       if (view.equals("CART")){

           holder.addQty.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int qty=current_cart.getQuantity();
                   qty++;
                   current_cart.setQuantity(qty);
                   current_cart.setProduct_total_price(current_cart.getProduct_price()*qty);
                   Cart_Model.update(current_cart);
                   holder.quantity.setText(String.valueOf(String.valueOf(current_cart.getQuantity())));
                   holder.product_total_price.setText(String.valueOf(current_cart.getProduct_total_price()));
                   Cart_Activity.order_total_price.setText(String.valueOf( getTotal()));

               }
           });
           holder.minusQty.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int qty=current_cart.getQuantity();
                   qty--;
                   current_cart.setQuantity(qty);
                   current_cart.setProduct_total_price(current_cart.getProduct_price()*qty);
                   Cart_Model.update(current_cart);
                   if (qty==0){
                       Cart_Model.delete(current_cart);
                       list.remove(position);
                       notifyItemRemoved(position);
                       Cart_Activity.order_total_price.setText(String.valueOf( getTotal()));

                   }else {
                       holder.quantity.setText(String.valueOf(String.valueOf(current_cart.getQuantity())));
                       holder.product_total_price.setText(String.valueOf(current_cart.getProduct_total_price()));
                       Cart_Activity.order_total_price.setText(String.valueOf( getTotal()));
                   }

               }

           });

       }

//        Glide.with(mContext)
//                .load(current_cart.getProduct_image_url())
//                .into(holder.image);
        Picasso.with(mContext)
                .load(current_cart.getProduct_image_url())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Cart_Model getCartAt(int position){
        return list.get(position);
    }



    public class CartHolder extends RecyclerView.ViewHolder  {

        public ImageView image;
        public TextView product_title,product_total_price,quantity;
        ImageButton addQty,minusQty;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
                image=itemView.findViewById(R.id.product_image_url);
            product_title=itemView.findViewById(R.id.product_title);
            product_total_price=itemView.findViewById(R.id.product_total_price);
            quantity=itemView.findViewById(R.id.quantity);
            addQty=itemView.findViewById(R.id.addQty);
            minusQty=itemView.findViewById(R.id.minusQty);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener9 != null && position != RecyclerView.NO_POSITION) {
                        listener9.onItemclick9(list.get(position));
                    }
                }
            });
        }
    }



    public interface OnCartClickListener9 {
        void onItemclick9(Cart_Model cart_model);
    }

    public void setOnItemClickListener9(OnCartClickListener9 listener9) {
        this.listener9 = listener9;
    }
    public int getTotal(){
        total=0;
        for (Cart_Model model:list) {
          total=total+model.getProduct_total_price();
        }
        return total;
    }
}

