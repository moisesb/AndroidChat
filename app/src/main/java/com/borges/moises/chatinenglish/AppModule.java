package com.borges.moises.chatinenglish;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Moisés on 02/07/2016.
 */
@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Application providesApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    public Context providesApplicationContext(){
        return mApplication.getApplicationContext();
    }
}
