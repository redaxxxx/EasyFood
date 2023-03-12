package com.example.easyfood;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SharedPrefer {

    public static final String PREFS_Logger = "LoggerFile";
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private Context mContext;

    public SharedPrefer(Context context){
        mContext = context;
        pref = context.getSharedPreferences(PREFS_Logger, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setSignInfo(String username, String email){
        editor.putString(Constants.USERNAME_KEY, username);
        editor.putString(Constants.EMAIL_KEY, email);
        editor.commit();
    }

    public HashMap<String, String> getUserInfo(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.USERS_KEY, pref.getString(Constants.USERNAME_KEY, null));
        hashMap.put(Constants.EMAIL_KEY, pref.getString(Constants.EMAIL_KEY, null));
        return hashMap;
    }
}
