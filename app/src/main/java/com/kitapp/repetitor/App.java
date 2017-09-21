package com.kitapp.repetitor;

import android.app.Application;

/**
 * Created by denis on 9/20/17.
 */

public class App extends Application {

    private static App mApp;
    private RepetitiorDB repetitiorDB;
    private Api api;

    public static synchronized App getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        repetitiorDB = new RepetitiorDB(getApplicationContext());
        api = new Api(repetitiorDB);

    }

    public RepetitiorDB getRepetitiorDB() {
        return repetitiorDB;
    }

    public Api getApi() {
        return api;
    }
}
