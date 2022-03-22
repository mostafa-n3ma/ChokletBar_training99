package com.example.chokletbar_training99.Admin_Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chokletbar_training99.Models.Product_Model;
import com.example.chokletbar_training99.R;
import com.example.chokletbar_training99.tools.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Add_product extends AppCompatActivity {
    ImageButton btn_done,clos_btn;
    ImageView product_photo;
    private final int PICK_IMAGE_REQUEST = 1;
    TextInputEditText product_title,product_description,product_details, product_price,product_Category;
    Product_Model new_product = new Product_Model();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;
    StorageReference storage_ref_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide states bar
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //
        setContentView(R.layout.activity_add_product);


        bindViews();
    }

    private void bindViews() {
        new_product.setProduct_id(db.collection(Utils.PRODUCTS_TABLE).document().getId()) ;
        clos_btn=findViewById(R.id.bt_close);
        btn_done = findViewById(R.id.btn_done);
        product_title=findViewById(R.id.product_title_i);
        product_description=findViewById(R.id.product_description_i);
        product_details=findViewById(R.id.product_details_i);
        product_price=findViewById(R.id.product_price_i);
        product_Category=findViewById(R.id.Product_Category);
        product_photo=findViewById(R.id.product_photo);
        clos_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_product();
            }
        });
        product_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    private void submit_product() {
        new_product.setProduct_title(product_title.getText().toString());
        if (new_product.getProduct_title().trim().isEmpty()) {
            Toast.makeText(this, "Product title can't be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        new_product.setProduct_description(product_description.getText().toString());
        if (new_product.getProduct_description().trim().isEmpty()) {
            Toast.makeText(this, "please add some description", Toast.LENGTH_SHORT).show();
            return;
        }
        new_product.setProduct_details(product_details.getText().toString());
        if (product_price.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Product price can't be empty. ", Toast.LENGTH_SHORT).show();
            return;
        }
        new_product.setProduct_price(Integer.parseInt(product_price.getText().toString()));
        if (new_product.getProduct_price()<0){
            Toast.makeText(this, "Product price can't be less than zero.", Toast.LENGTH_SHORT).show();
            return;
        }

        new_product.setProduct_category(product_Category.getText().toString());

        if (imagePath == null) {
            Toast.makeText(this, "You must select product photo.", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("");
        progressDialog.show();
        storage_ref_main= FirebaseStorage.getInstance().getReference();

        storage_ref_main.child("products/"+new_product.getProduct_id()).putFile(imagePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Add_product.this, "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                storage_ref_main.child("products/"+new_product.getProduct_id()).getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                new_product.setProduct_image_url(uri.toString());
                                upload_to_firestore();
                                return;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_product.this, "can't get the Uri", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Add_product.this, "Can't Uploaded..!", Toast.LENGTH_SHORT).show();
                return;
            }
        });


    }

    private void upload_to_firestore() {
        db.collection(Utils.PRODUCTS_TABLE).document(new_product.getProduct_id()).set(new_product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.hide();
                        progressDialog.dismiss();
                        Toast.makeText(Add_product.this, "Uploaded successfully!.", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.hide();
                Toast.makeText(Add_product.this, "Failed upload product because " + e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select product image"), PICK_IMAGE_REQUEST);
    }


    private Uri imagePath = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                product_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}