package com.sinch.messagingtutorial.app;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import android.app.Application;

public class InitializeParse extends Application
{
    //Got off Parse.com
    @Override
    public void onCreate()
    {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "Ehs35Hcp1jO3xgBLkVS5bvKlcGOmpEmaQHR9nURu", "uLBDa7EIyo6PuaCfs5KLzY0RqxqB9RZLfGjDoOqs");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}
