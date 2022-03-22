package com.example.chokletbar_training99.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.chokletbar_training99.R;

public class About_us extends AppCompatActivity {
    ImageView s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        s=findViewById(R.id.aboutimage);
        String url="https://firebasestorage.googleapis.com/v0/b/banjoldemo.appspot.com/o/products%2FcpK7zHxlGvn2vlsr3JnH?alt=media&token=b983c551-5fd1-40ba-a032-e09b4ab84119";
        Glide.with(this).load(url)
                 .into(s);
    }
}