package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.media.AudioManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.View;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.VoiceSpeak;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.Timer;
import java.util.TimerTask;

public class HeadPhoneTestActivity extends AppCompatActivity {
    AppCompatButton _checkButton;
    Timer timer;
    private String TAG_CLASS = HeadPhoneTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_phone_test);
        this.setFinishOnTouchOutside(false);
        initializeViews();
        setTitle("");
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        _checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioManager.isWiredHeadsetOn()) {
                    Message.toastMessage(getApplicationContext(),
                            "Headset is connected.", "");
                    completeActivity(true);
                } else {
                    completeActivity(false);
                }
            }
        });
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                completeActivity(false);
            }
        }, Constants.TEST_TIMER);
    }

    /**
     * Method to initialize the Views.
     */
    private void initializeViews() {
        _checkButton = findViewById(R.id.headPhoneCheckButton);
        timer = new Timer();
    }

    /**
     * Method to complete the Activity.
     *
     * @param status: true if successful, else false.
     */
    private void completeActivity(boolean status) {
        Message.logMessage(TAG_CLASS, status + "");
        if (status) {
            timer.cancel();
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
            timer.cancel();
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (_checkButton != null)
            _checkButton = null;
        if (timer != null)
            timer = null;
        Runtime.getRuntime().gc();
    }
}
