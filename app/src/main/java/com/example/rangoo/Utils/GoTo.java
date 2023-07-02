package com.example.rangoo.Utils;

import android.app.Activity;
import android.content.Intent;

import com.example.rangoo.Activities.EmptyHomeActivity;
import com.example.rangoo.Activities.HomeListActivity;
import com.example.rangoo.Activities.LoginActivity;
import com.example.rangoo.Activities.ProfileActivity;
import com.example.rangoo.Activities.WeekMenuActivity;

public class GoTo {

    public  static void homeView(Activity activity){
        //TODO: Verificar se o usuário possui uma lista e então chamar a tela correspondente.
        activity.startActivity(new Intent(activity, HomeListActivity.class));
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

    public static void detailsView(){

    }

    public static void profileView(Activity activity){
        activity.startActivity(new Intent(activity, ProfileActivity.class));
        activity.finish();
    }

}
