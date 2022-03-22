package com.example.chokletbar_training99.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chokletbar_training99.Models.Product_Model;
import com.example.chokletbar_training99.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Products_Adapter extends RecyclerView.Adapter<Products_Adapter.ProductsHolder> {
private OnProductClickListner listner;
private OnProductLongClickListener longListener;
Context context;
  private List<Product_Model> products=new ArrayList<>();

public Products_Adapter(Context  context,List<Product_Model> products){
    this.context=context;
    this.products=products;
}

    @NonNull
    @Override
    public ProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsHolder holder, int position) {
        Product_Model currentProduct=products.get(position);
        holder.title.setText(currentProduct.getProduct_title());
        holder.price.setText(currentProduct.getProduct_price()+"");

//        Glide.with(context)
//                .load(currentProduct.getProduct_image_url())
//                .into(holder.image);
        Picasso.with(context)
                .load(currentProduct.getProduct_image_url())
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductsHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;
        public View lyt_parent;
        public ProductsHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.product_image_i);
            title = (TextView) itemView.findViewById(R.id.product_title_i);
            price = (TextView) itemView.findViewById(R.id.product_price_i);
            lyt_parent = (View) itemView.findViewById(R.id.lyt_parent);

            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onProductClick(products.get(position),position);
                    }
                }
            });

            lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position=getAdapterPosition();
                    if (longListener!=null&&position!=RecyclerView.NO_POSITION){
                        longListener.onProductLongClick(products.get(position),position,lyt_parent);
                    }
                    return true;
                }
            });

        }
    }

    public interface OnProductClickListner{
        void onProductClick(Product_Model model,int position);
    }

    public void setOnProductClickListener(OnProductClickListner onProductClickListener){
        this.listner=onProductClickListener;

    }
    public interface OnProductLongClickListener{
    void onProductLongClick(Product_Model model, int position, View lyt_parent);
    }
    public void setOnProductLongClickListener(OnProductLongClickListener onProductLongClickListener){
    this.longListener=onProductLongClickListener;
    }
}
