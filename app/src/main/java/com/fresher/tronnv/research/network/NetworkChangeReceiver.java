package com.fresher.tronnv.research.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
/**
 * Created by NGUYEN VAN TRON on 05/18/18.
 */

/**
 * register event when network changed
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isConnected()) {
            Toast.makeText(context, "Network is turned ON", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"Network is turned OFF",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}