package com.eyalengel.blurdemo.Controller;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by eyal on 9/21/17.
 */


// Check internet connection, initialize Facebook SDK, write exceptions to log file, keep LruBitmapCache, Volley Methods
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

//    private RequestQueue mRequestQueue; add when adding Volley Library

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Iconify.with(new FontAwesomeModule());
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
}
