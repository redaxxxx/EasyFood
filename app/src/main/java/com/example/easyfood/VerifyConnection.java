package com.example.easyfood;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class VerifyConnection {

    private Context mContext;
    public boolean isInternetConnected;

    public VerifyConnection(Context context){
        this.mContext=context;

    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager= (ConnectivityManager)this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null){
            return isInternetConnected= activeNetwork.isConnected();
        }else {
            return isInternetConnected=false;
        }
    }
}
