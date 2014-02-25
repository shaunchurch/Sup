package com.pixsys.fistbump;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

/**
 * Created by shaun on 22/02/2014.
 */
public class SupApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "3vElODlMLGGJxM5cOBk9lLlgR7Y3f7BHK5fFvwnE", "liq7N7E0J9ecvX3CFFVpCqWB6WmVMetwGQ0mkpTZ");

        PushService.setDefaultPushCallback(this, MainActivity.class);

        // Save the current Installation to Parse.
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("user", ParseUser.getCurrentUser());
        installation.put("userId", ParseUser.getCurrentUser().getObjectId());
        installation.saveInBackground();

    }

}
