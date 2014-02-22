package com.pixsys.ribbit;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by shaun on 22/02/2014.
 */
public class SupApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "3vElODlMLGGJxM5cOBk9lLlgR7Y3f7BHK5fFvwnE", "liq7N7E0J9ecvX3CFFVpCqWB6WmVMetwGQ0mkpTZ");

    }

}
