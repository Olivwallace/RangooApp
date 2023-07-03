package com.example.rangoo.Utils;

import android.app.Activity;
import android.content.Intent;

import com.example.rangoo.Activities.DetailsActivity;
import com.example.rangoo.Activities.EmptyHomeActivity;
import com.example.rangoo.Activities.HomeListActivity;
import com.example.rangoo.Activities.LoginActivity;
import com.example.rangoo.Activities.ProfileActivity;
import com.example.rangoo.Activities.WeekMenuActivity;
import com.example.rangoo.Model.Food;
import com.example.rangoo.R;

public class GoTo {

    private static String UID;

    public static void setUID(String uid){
        UID = uid;
    }

    public  static void homeView(Activity activity){
        activity.startActivity(new Intent(activity, HomeListActivity.class).putExtra("UID", UID));
        //activity.startActivity(new Intent(activity, WeekMenuActivity.class));
        activity.finish();
    }

    private static void homeViewEmpty(Activity activity){
        activity.startActivity(new Intent(activity, EmptyHomeActivity.class));
        activity.finish();
    }

    private static void homeViewList(Activity activity){
        activity.startActivity(new Intent(activity, HomeListActivity.class));
        activity.finish();
    }

    public static void signInView(Activity activity){
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

    public static void weekMenu(Activity activity){
        activity.startActivity(new Intent(activity, WeekMenuActivity.class));
        activity.finish();
    }

    public static void detailsView(Activity activity, Food food){
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(activity.getString(R.string._FOOD_TO_DETAIL), food);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void profileView(Activity activity){
        activity.startActivity(new Intent(activity, ProfileActivity.class));
        activity.finish();
    }

}
