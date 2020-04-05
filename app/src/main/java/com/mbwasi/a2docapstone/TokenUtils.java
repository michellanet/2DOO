package com.mbwasi.a2docapstone;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public abstract class TokenUtils {

    public static final String DOO_PREFS = "2DOO_PREFS";
    public static final String LOGIN_TOKEN = "LOGIN_TOKEN";

    public static boolean storeLoginToken(String tokenString, Context context){
        try {
            SharedPreferences pref = context.getSharedPreferences(DOO_PREFS, 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();

            editor.putString(LOGIN_TOKEN, tokenString); // Storing string
            editor.commit();
            return true;
        }catch(Exception ex){
            Log.e("TokenUtils",ex.toString());
            return false;
        }
    }

    static public String getLoginToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(DOO_PREFS, 0); // 0 - for private mode
        return pref.getString(LOGIN_TOKEN,null);
    }
}
