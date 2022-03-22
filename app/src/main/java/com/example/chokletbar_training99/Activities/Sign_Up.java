package com.example.chokletbar_training99.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chokletbar_training99.Models.User_Model;
import com.example.chokletbar_training99.R;
import com.example.chokletbar_training99.tools.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;

public class Sign_Up extends AppCompatActivity {
public static final String TAG="Uder9999999999999999";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        check logging
        if (Utils.loged_in()) {
            Toast.makeText(Sign_Up.this, "قمت بتسجيل الدخول بالفعل ...", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        bindViews();
    }


    TextInputLayout first_name_field, last_name_field, email_field, Phone_field, Password_field, Password2_field, address_field;
    ImageView profile_image_Url_field;
    Button Signup;
    ImageButton bt_close;

    private void bindViews() {
        new_User.setUserId(db.collection(Utils.USERS_TABLE).document().getId()) ;;
        first_name_field = findViewById(R.id.first_name);
        last_name_field = findViewById(R.id.last_name);
        email_field = findViewById(R.id.email);
        Phone_field = findViewById(R.id.Phone);
        Password_field = findViewById(R.id.Password);
        Password2_field = findViewById(R.id.Password2);
        address_field = findViewById(R.id.address);
        profile_image_Url_field = findViewById(R.id.profile_image_Url);
        Signup = findViewById(R.id.Signup);
        bt_close = findViewById(R.id.bt_close);

        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        profile_image_Url_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_profile_image();
            }

        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate_data();
            }
        });
    }




    private void validate_data() {
        String first_name, last_name, email, Phone, Password, Password2, profile_image_Url, address, Registration_Date;
        first_name = first_name_field.getEditText().getText().toString().trim();
        last_name = last_name_field.getEditText().getText().toString().trim();
        email = email_field.getEditText().getText().toString().trim();
        Phone = Phone_field.getEditText().getText().toString().trim();
        Password = Password_field.getEditText().getText().toString();
        Password2 = Password2_field.getEditText().getText().toString();
        address = address_field.getEditText().getText().toString().trim();


        //check image
        if (imagePath == null) {
            Toast.makeText(Sign_Up.this, "الرجاء اختيار صورة شخصية", Toast.LENGTH_SHORT).show();
            return;
        }


        //if fields are empty
        if (TextUtils.isEmpty(first_name)) {
            first_name_field.setErrorEnabled(true);
            first_name_field.setError("الرجاء ادخال الاسم ");
            first_name_field.requestFocus();
            return;
        } else {
            first_name_field.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(last_name)) {
            last_name_field.setErrorEnabled(true);
            last_name_field.setError("الرجاء ادخال الاسم الثاني ");
            last_name_field.requestFocus();
            return;
        } else {
            last_name_field.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(email)) {
            email_field.setErrorEnabled(true);
            email_field.setError("الرجاء ادخال البريد الالكتروني ");
            email_field.requestFocus();
            return;
        } else {
            email_field.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(Password)) {
            Password_field.setErrorEnabled(true);
            Password_field.setError("الرجاء ادخال الرمز السري ");
            Password_field.requestFocus();
            return;
        } else {
            Password_field.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(Password2)) {
            Password2_field.setErrorEnabled(true);
            Password2_field.setError("الرجاء ادخال الرمز السري ");
            Password2_field.requestFocus();
            return;
        } else {
            Password2_field.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(Phone)) {
            Phone_field.setErrorEnabled(true);
            Phone_field.setError("الرجاء ادخال رقم الهاتف ");
            Phone_field.requestFocus();
            return;
        } else {
            Phone_field.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(address)) {
            address_field.setErrorEnabled(true);
            address_field.setError("الرجاء ادخال العنوان ");
            address_field.requestFocus();
            return;
        } else {
            address_field.setErrorEnabled(false);
        }

//        check email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_field.setError("البريد الالكتروني خاطيء..!");
            email_field.requestFocus();
            return;
        } else {
            email_field.setError(null);
        }
//        Phone check
        if (Phone.length() != 11) {
            Phone_field.setErrorEnabled(true);
            Phone_field.setError(" رقم الهاتف خطأ ");
            Phone_field.requestFocus();
            return;
        } else {
            Phone_field.setErrorEnabled(false);
        }
        //  check password leng
        if (Password.length()<5) {

            Password_field.setErrorEnabled(true);
            Password_field.setError("الرمز السري ضعيف ");
            Password_field.requestFocus();
            return;
        } else {
            Password_field.setErrorEnabled(false);
        }
//        check password matches
        if (!Password.equals(Password2)) {
            Password2_field.setErrorEnabled(true);
            Password2_field.setError("الرمز السري غير متطابق ");
            Password2_field.requestFocus();
            return;
        } else {
            Password2_field.setErrorEnabled(false);
        }


//        fill user information
        new_User.setFirst_name(first_name);
        new_User.setLast_name(last_name);
        new_User.setEmail(email);
        new_User.setPhone(Phone);
        new_User.setPassword(Password);
        new_User.setAddress(address);
        new_User.setRegistration_Date(Utils.get_current_date_time());
        upload_image();
    }



    StorageReference storage_ref_main;
    ProgressDialog progressDialog;
    private void upload_image() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("جاري انشاء الحساب يرجى الانتظار ");
        progressDialog.show();
        storage_ref_main= FirebaseStorage.getInstance().getReference();
        storage_ref_main.child(Utils.USERS_TABLE+"/"+new_User.getFirst_name()+" "+new_User.getLast_name()).putFile(imagePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storage_ref_main.child(Utils.USERS_TABLE+"/"+new_User.getFirst_name()+" "+new_User.getLast_name()).getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                new_User.setProfile_image_Url(uri.toString());
                                create_user();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }




    User_Model new_User = new User_Model();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private void create_user() {
        db.collection(Utils.USERS_TABLE).document(new_User.getFirst_name()+" "+new_User.getLast_name()).set(new_User)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.hide();
                        progressDialog.dismiss();
                        finish();
                        return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }
        });

    }


    private final int PICK_PROFILE_IMAGE_REQUEST = 2;

    private void get_profile_image() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "اختر صورة شخصية"), PICK_PROFILE_IMAGE_REQUEST);
    }


    private Uri imagePath = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PROFILE_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profile_image_Url_field.setImageBitmap(bitmap);
                profile_image_Url_field.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}