package com.nocdib.signupform;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by nocdib on 10/25/15.
 */
public class User implements Serializable{

    public static String TAG = "USER";
    private String mUsername;
    private String mPassword;

    public User(String username, String password){
        mUsername = username;
        mPassword = password;
        Log.d(TAG, "Created " + mUsername + ":" + mPassword);
    }

    public String getUsername(){
        return mUsername;
    }

    public void setPassword(String newPassword){
        mPassword = newPassword;
    }

}
