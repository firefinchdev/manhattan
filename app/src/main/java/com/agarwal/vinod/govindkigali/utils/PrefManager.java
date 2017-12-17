package com.agarwal.vinod.govindkigali.utils;

/**
 * Created by Anirudh Gupta on 12/11/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class PrefManager {


    ArrayList<String> lang = new ArrayList<>(Arrays.asList(
            "en","hi"
    ));

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "manhattanPref";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String USER_NAME= "UserName";
    private static final String USER_LANGUAGE= "UserLanguage";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserName(String name){
        editor.putString(USER_NAME, name);
        editor.commit();
    }

    public String getUserName(){
        return  pref.getString(USER_NAME, null);
    }

    public void setLanguage(String name){
        editor.putInt(USER_LANGUAGE, lang.indexOf(name));
        editor.commit();
    }
    public void setLanguage(int id){
        editor.putInt(USER_LANGUAGE, id);
        editor.commit();
    }

    public String getUserLanguage(){
        return  lang.get(pref.getInt(USER_LANGUAGE, 0));
    }



}