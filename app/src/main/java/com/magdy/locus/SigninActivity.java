package com.magdy.locus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.magdy.locus.interfacs.Isregested;
import com.magdy.locus.mysql.database.Sign_in_dp;
import com.magdy.locus.resorces.Company_userinfo;
import com.magdy.locus.resorces.Place;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText companyname = (EditText) findViewById(R.id.ed_companyname);
        final EditText pass = (EditText) findViewById(R.id.ed_password);
        final Sign_in_dp signInDp = new Sign_in_dp(this);
        Button signin = (Button) findViewById(R.id.btn_dosignin);
        Button signup = (Button) findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, SignUpActivity.class));
                finish();

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInDp.fetchUserDataAsyncTask(new Company_userinfo(1, companyname.getText().toString(), pass.getText().toString()), new Isregested() {
                    @Override
                    public void regester_done(Company_userinfo user) {
                       try {
                           Log.e("id", user.getRoomnum() + "");
                           Toast.makeText(getApplicationContext(),"Wellcom "+user.getPlacename()+" :)",Toast.LENGTH_LONG).show();
                           Splash_screen.companyUserinfo=user;
                           Splash_screen.companyUserinfo.setDidsignin(true);
                           startActivity(new Intent(SigninActivity.this, CompanyActivity.class));
                            finish();

                       }catch (Exception e)
                       {
                           Toast.makeText(getApplicationContext(),"Wrong company name or password",Toast.LENGTH_LONG).show();
                           Log.e("error", e.toString() + "");
                       }


                    }

                    @Override
                    public void place_added(Place place) {

                    }
                });
            }


        });

    }
}