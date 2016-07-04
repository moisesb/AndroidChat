package com.borges.moises.chatinenglish;

import android.app.Application;

import com.borges.moises.chatinenglish.net.DaggerNetComponent;
import com.borges.moises.chatinenglish.net.NetComponent;
import com.borges.moises.chatinenglish.net.NetModule;

/**
 * Created by Moisés on 30/06/2016.
 */

public class App extends Application {

    private static final String HOST_NAME = "chat.safic.net";
    private static final String SERVICE_NAME = "safic.net";

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(HOST_NAME, SERVICE_NAME))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
