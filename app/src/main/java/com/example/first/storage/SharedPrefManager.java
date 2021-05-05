package com.example.first.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.first.models.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {

        this.mCtx = mCtx;
    }

    public static synchronized  SharedPrefManager getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new SharedPrefManager(mCtx);
        }

        return mInstance;
    }

    public void saveUser(User user){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("username",user.getUsername());
        editor.putString("email",user.getEmail());
        editor.putString("phone_number",user.getPhone_number());


        editor.apply();

    }


    public boolean isLoggedIn(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        if(sharedPreferences.getInt("id",-1) != -1){
            return true;
        }
        return false;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        User user = new User(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("username",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("phone_number",null)
        );

        return user;
    }

    public void clear(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

        editor.apply();

    }
}
