package io.github.b_lam.controlforaurora;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.druk.rxdnssd.RxDnssd;
import com.github.druk.rxdnssd.RxDnssdBindable;
import com.github.druk.rxdnssd.RxDnssdEmbedded;

/**
 * Created by brandon on 6/17/17.
 */

public class AuroraApplication extends Application {

    private static final String TAG = "AuroraApplication";
    private RxDnssd mRxDnssd;
    private RegistrationManager mRegistrationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mRxDnssd = createDnssd();
        mRegistrationManager = new RegistrationManager();
    }

    public static RxDnssd getRxDnssd(@NonNull Context context){
        return ((AuroraApplication)context.getApplicationContext()).mRxDnssd;
    }

    public static RegistrationManager getRegistrationManager(@NonNull Context context){
        return ((AuroraApplication) context.getApplicationContext()).mRegistrationManager;
    }

    private RxDnssd createDnssd(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            Log.i(TAG, "Using embedded version of dns sd because of API < 16");
            return new RxDnssdEmbedded();
        }
        if (Build.VERSION.RELEASE.contains("4.4.2") && Build.MANUFACTURER.toLowerCase().contains("samsung")){
            Log.i(TAG, "Using embedded version of dns sd because of Samsung 4.4.2");
            return new RxDnssdEmbedded();
        }
        Log.i(TAG, "Using systems dns sd daemon");
        return new RxDnssdBindable(this);
    }
}
