package com.example.sisteminformasikliniik.shared.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private Context context;
    private int PRIVATE_MODE = 0;
    private String PREF_NAME = "SharedPreferences";
    private String IS_LOGIN  = "is_login";
    private SharedPreferences sh;
    private SharedPreferences.Editor editor;
    public PrefManager(Context context) {
        sh = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sh.edit();
    }

    public void setLoggin(boolean isLogin){
        editor.putBoolean(IS_LOGIN,isLogin);
        editor.commit();
    }

    public void setUsername(String username) {
        editor.putString("username",username);
        editor.commit();
    }

    public void setName(String name) {
        editor.putString("name",name);
        editor.commit();
    }

    public void setRoleUser(String roleUser) {
        editor.putString("roleUser",roleUser);
        editor.commit();
    }

    public void setNikUser(String nikUser) {
        editor.putString("nikUser",nikUser);
        editor.commit();
    }

    public boolean isLogin() {
        return sh.getBoolean(IS_LOGIN,false);
    }

    public String getUsername(){
        return sh.getString("username","");
    }

    public String getName(){
        return sh.getString("name","");
    }

    public String getRoleUser(){
        return sh.getString("roleUser","");
    }

    public String getNikUser(){
        return sh.getString("nikUser","");
    }


    public void removeDate(){
        editor.clear();
        editor.commit();
    }
}
