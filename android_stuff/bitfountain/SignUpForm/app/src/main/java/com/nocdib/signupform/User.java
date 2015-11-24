package com.nocdib.signupform;

import android.util.Log;

/**
 * Created by nocdib on 10/25/15.
 */
public class User {

    public static String TAG = "USER";
    private String mUsername;
    private String mPassword;

    public User(String username, String password){
        mUsername = username;
        mPassword = password;
        Log.d(TAG, "Created " + mUsername + ":" + mPassword);
    }

}
