
package com.example.mandar.rideon;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;



public class Boot extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, final Intent intent){
        //String status = NetworkUtil.getConnectivityStatusString(context);
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
            Toast.makeText(context,"Thankyou for using Ride On!",Toast.LENGTH_LONG).show();


    }
}

