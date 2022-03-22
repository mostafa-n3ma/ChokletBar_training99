package com.example.chokletbar_training99.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.chokletbar_training99.Activities.About_us;
import com.example.chokletbar_training99.Activities.Cart_Activity;
import com.example.chokletbar_training99.Activities.Sign_In;
import com.example.chokletbar_training99.Activities.UserActivity;
import com.example.chokletbar_training99.Admin_Activities.Dashboard;
import com.example.chokletbar_training99.MainActivity;
import com.example.chokletbar_training99.Models.Cart_Model;
import com.example.chokletbar_training99.Models.User_Model;
import com.example.chokletbar_training99.R;

public class my_tool {

    public static void initiate_drawer(Activity activity, boolean home, boolean user, boolean about,boolean dashboard,boolean is_admin) {
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.droewr_layout);
        ImageButton drawer_btn = (ImageButton) activity.findViewById(R.id.drower_btn);
        drawer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        clickhome(activity, home, drawerLayout);
        clickUser(activity, user, drawerLayout);
        clickAbout(activity, about, drawerLayout);
        clickLogOut(activity, about, drawerLayout);
        clickDashboard(activity,dashboard,is_admin,drawerLayout);
    }
    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        activity.startActivity(intent);
    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {
        //close drawer layout
        //check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //when drawer is open
            //close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }
    public static void clickhome(Activity activity, boolean inhome, DrawerLayout drawerLayout) {
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.home_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inhome) {
                    closeDrawer(drawerLayout);
                } else {
                    redirectActivity(activity, MainActivity.class);

                }
            }
        });

    }
    public static void clickUser(Activity activity, boolean inuser, DrawerLayout drawerLayout) {
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.user_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inuser) {
                    closeDrawer(drawerLayout);
                } else {
                    Toast.makeText(activity, "dierect to user activity", Toast.LENGTH_SHORT).show();
                    redirectActivity(activity, UserActivity.class);

                }
            }
        });

    }
    public static void clickAbout(Activity activity, boolean inabout, DrawerLayout drawerLayout) {
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.about_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inabout) {
                    closeDrawer(drawerLayout);
                } else {
                    Toast.makeText(activity, "dierect to about activity", Toast.LENGTH_SHORT).show();
                    redirectActivity(activity, About_us.class);

                }
            }
        });

    }
    public static void clickLogOut(Activity activity, boolean logout, DrawerLayout drawerLayout) {
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.logout_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logout) {
                    closeDrawer(drawerLayout);
                } else {
                        logout_dailog(activity);


                }
            }
        });

    }
    public static void clickDashboard(Activity activity, boolean dashboard,boolean is_admin, DrawerLayout drawerLayout) {
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.dashboard_layout);
        if (is_admin){
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dashboard) {
                        closeDrawer(drawerLayout);
                    } else {
                       redirectActivity(activity,Dashboard.class);
                    }
                }
            });
        }else {
            linearLayout.setVisibility(View.GONE);
        }


    }
    public static void logout_dailog(Activity activity) {
        //initialize alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("تسجيل الخروج");
        //set message
        builder.setMessage("هل انت متاكد انك تريد تسجيل خروج من حسابك ..؟");
        builder.setIcon(R.drawable.chocolate_bar);
        //positive yes button
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish activity
//                activity.finishAffinity();
                //exite app
//                System.exit(0);
                User_Model.deleteAll(User_Model.class);
                Intent intent=new Intent(activity, Sign_In.class);
                activity.startActivity(intent);
                Utils.go_online(activity,false);
            }
        });
        //Nagetive button
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss dialog
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();
    }
    public static void click_cart_btn(Activity activity){
        CardView redcard=(CardView)activity.findViewById(R.id.red_card) ;
        ImageView cart_bt = (ImageView) activity.findViewById(R.id.cart_bt);
        cart_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, Cart_Activity.class);
                activity.startActivity(intent);
            }
        });
        TextView cart_txt=(TextView) activity.findViewById(R.id.cart_count_txt);
        String cart_count= String.valueOf(Cart_Model.count(Cart_Model.class));
        if (cart_count.equals("0")){
            redcard.setVisibility(View.GONE);
        }else {
            redcard.setVisibility(View.VISIBLE);
            cart_txt.setText(cart_count);
        }

    }








}