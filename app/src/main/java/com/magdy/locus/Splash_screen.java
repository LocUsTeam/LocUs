package com.magdy.locus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.magdy.locus.mysql.database.Place_data;
import com.magdy.locus.resorces.Company_userinfo;

public class Splash_screen extends AppCompatActivity {

    public static Company_userinfo companyUserinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        companyUserinfo=new Company_userinfo(0,null,null);
        // Log.e("places", dpoepnhelper.getCount()+"");
        companyUserinfo.setDidsignin(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                new Place_data().getarrays(getApplicationContext());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //img.setImageBitmap(bitmapList.get(1));
                        Intent intent=new Intent(Splash_screen.this,MainActivity.class);
                       // intent.putExtra("list", (Serializable) bitmapList);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).start();

    }

}
