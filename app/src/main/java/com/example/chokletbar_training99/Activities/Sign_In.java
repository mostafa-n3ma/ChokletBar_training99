package com.example.chokletbar_training99.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chokletbar_training99.Models.User_Model;
import com.example.chokletbar_training99.R;
import com.example.chokletbar_training99.tools.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Sign_In extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //        check logging
        if (Utils.loged_in()) {
            Toast.makeText(Sign_In.this, "قمت بتسجيل الدخول بالفعل ...", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        bindViews();
    }




    TextInputLayout emailfield,passfield;
    Button sign_in_btn ;
    private void bindViews() {
        emailfield=findViewById(R.id.email_in);
        passfield=findViewById(R.id.Password_in);
        sign_in_btn=findViewById(R.id.Sign_In_btn);
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valedaitedata();
            }
        });

    }

    private void valedaitedata() {
        String email,password;
        email=emailfield.getEditText().getText().toString();
        password=passfield.getEditText().getText().toString();
        //if fields are empty
        if (TextUtils.isEmpty(email.trim())) {
            emailfield.setErrorEnabled(true);
            emailfield.setError("الرجاء ادخال البريد الالكتروني ");
            emailfield.requestFocus();
            return;
        } else {
            emailfield.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(password.trim())) {
            passfield.setErrorEnabled(true);
            passfield.setError("الرجاء ادخال الرمز السري ");
            passfield.requestFocus();
            return;
        } else {
            passfield.setErrorEnabled(false);
        }

        //        check email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailfield.setError("البريد الالكتروني خاطيء..!");
            emailfield.requestFocus();
            return;
        } else {
            emailfield.setError(null);
        }
        searchData(email,password);
    }
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ProgressDialog pg;
    private void searchData(String email,String password) {
        pg=new ProgressDialog(this);
        pg.setTitle("جاري التحقق يرجى الانتظار...");
        pg.setCancelable(false);
        pg.show();
        db.collection(Utils.USERS_TABLE).whereEqualTo("email", email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots == null) {
                    pg.hide();
                    pg.dismiss();
                    Toast.makeText(Sign_In.this, "لايوجد حساب مسجل بهذا الاميل ...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (queryDocumentSnapshots.isEmpty()) {
                    pg.hide();
                    pg.dismiss();
                    Toast.makeText(Sign_In.this, "لايوجد حساب مسجل بهذا الاميل ...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User_Model>users=queryDocumentSnapshots.toObjects(User_Model.class);
                User_Model sign_in_user= users.get(0);
                if (sign_in_user.getPassword().equals(password)){
                    log_in(sign_in_user);
                    pg.hide();
                    pg.dismiss();
                    finish();
                    return;
                }else {
                    pg.hide();
                    pg.dismiss();
                    Toast.makeText(Sign_In.this, "الرمز خاطيء", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void log_in(User_Model sign_in_user) {
        try {
            User_Model.deleteAll(User_Model.class);
            User_Model.save(sign_in_user);
            Toast.makeText(Sign_In.this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

        }
    }

    public void open_Sign_up(View view) {
        Intent intent=new Intent(Sign_In.this,Sign_Up.class);
        startActivity(intent);
    }
}