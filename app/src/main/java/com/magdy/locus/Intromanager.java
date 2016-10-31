package com.magdy.locus;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by engma on 10/12/2016.
 */
public class Intromanager {
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    Context context ;
    public Intromanager(Context context)
    {

        this.context = context ;
        pref = context.getSharedPreferences("first", 0);
        editor = pref.edit();


    }
    public void setFirst(boolean isFirst)
    {
        editor.putBoolean("check",isFirst);
        editor.commit();
        editor.apply();


    }
    public boolean check()
    {

        return pref.getBoolean("check",false);

    }
}
