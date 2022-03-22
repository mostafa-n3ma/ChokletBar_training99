package com.example.chokletbar_training99.Activities;

import static com.example.chokletbar_training99.tools.my_tool.initiate_drawer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chokletbar_training99.Models.User_Model;
import com.example.chokletbar_training99.R;
import com.example.chokletbar_training99.tools.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {

private boolean is_admin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (Utils.Chick_Admin_permission()){
            is_admin=true;
        }
        initiate_drawer(this, false, true, false, false,is_admin);
        binViews();
        Intent intent = getIntent();
        if (intent.hasExtra("user_full_name")){
            get_User_info(intent.getStringExtra("user_full_name"));
        } else {
            if (Utils.loged_in()) {
                user_model = User_Model.listAll(User_Model.class).get(0);
                set_User_data();
            }else {
                Toast.makeText(getApplicationContext(), "انت لم تسجل الدخول ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void set_User_data() {
        Glide.with(this).load(user_model.getProfile_image_Url()).into(user_image);
        Picasso.with(this)
                .load(user_model.getProfile_image_Url())
                .fit().into(user_image);

        first_name_txt .setText(user_model.getFirst_name());
        last_name_txt.setText(user_model.getLast_name());
        email_txt.setText( user_model.getEmail());
        phone_txt.setText(user_model.getPhone());
        address_txt.setText(user_model.getAddress());
        reg_date_txt .setText(user_model.getRegistration_Date());
    }

     FirebaseFirestore db = FirebaseFirestore.getInstance();
     ProgressDialog pd;
    User_Model user_model;
    private void get_User_info(String userName) {
        pd = new ProgressDialog(this);
        pd.setTitle("جاري تحميل معلومات المستخدم...");
        pd.show();
        db.collection(Utils.USERS_TABLE).document(userName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user_model=documentSnapshot.toObject(User_Model.class);
                    set_User_data();
                    pd.hide();
                    pd.dismiss();
            }
        });

    }


    private ImageView user_image;
    private TextView first_name_txt, last_name_txt, email_txt, phone_txt, address_txt, reg_date_txt;

    private void binViews() {
        user_image = findViewById(R.id.user_image_Url);
        first_name_txt = findViewById(R.id.user_first_name);
        last_name_txt = findViewById(R.id.user_last_name);
        email_txt = findViewById(R.id.user_email);
        phone_txt = findViewById(R.id.user_phone);
        address_txt = findViewById(R.id.user_address);
        reg_date_txt = findViewById(R.id.user_reg_date);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}