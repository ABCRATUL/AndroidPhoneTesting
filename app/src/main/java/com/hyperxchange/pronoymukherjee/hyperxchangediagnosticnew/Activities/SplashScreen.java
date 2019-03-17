package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.VibrationTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Constants.fillAutomatedTestList();
        Constants.fillManualTestList();
        Constants.fillOSNames();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent changeActivityIntent = new Intent(SplashScreen.this,
                        AutoTestScreen.class);
                startActivity(changeActivityIntent);
                finish();
            }
        }, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Runtime.getRuntime().gc();
    }
}
