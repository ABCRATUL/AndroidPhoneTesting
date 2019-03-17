package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.VoiceSpeak;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class VibrationTestActivity extends AppCompatActivity {
    AppCompatEditText _numberVibration;
    AppCompatButton _vibrateButton, _submitButton;
    long pattern1[] = {0, 300, 200};
    long pattern2[] = {0, 300, 200, 300, 200};
    long pattern3[] = {0, 300, 200, 300, 200, 300, 200};
    Vibrator vibrator;
    int generatedPattern;
    Timer timer;
    private String TAG_CLASS = VibrationTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibration_test);
        this.setFinishOnTouchOutside(false);
        setTitle("");
        initializeViews();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                completeActivity(false);
            }
        }, Constants.TEST_TIMER);
        _vibrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                generatedPattern = random.nextInt(2 + 1) + 1;
                playVibration(generatedPattern);
            }
        });
        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int inputPattern = Integer.parseInt(_numberVibration.getText().toString());
                    if (inputPattern == generatedPattern) {
                        Message.logMessage(TAG_CLASS, "true");
                        completeActivity(true);
                    } else {
                        completeActivity(false);
                        timer.cancel();
                    }
                } catch (NumberFormatException e) {
                    Message.toastMessage(getApplicationContext(),
                            "Please enter the number correctly.",
                            "");
                }
            }
        });
    }

    /**
     * Method to initialize the Views.
     */
    private void initializeViews() {
        _numberVibration = findViewById(R.id.vibrationEnter);
        _vibrateButton = findViewById(R.id.vibrateButton);
        _submitButton = findViewById(R.id.submitVibration);
        timer = new Timer();
    }

    /**
     * Method to play the vibration.
     *
     * @param pattern: The vibration pattern.
     */
    private void playVibration(int pattern) {
        Message.logMessage(TAG_CLASS, pattern + "");
        switch (pattern) {
            case 1:
                vibrator.vibrate(pattern1, -1);
                break;
            case 2:
                vibrator.vibrate(pattern2, -1);
                break;
            case 3:
                vibrator.vibrate(pattern3, -1);
                break;
            default:
                vibrator.vibrate(pattern3, -1);
        }
    }

    /**
     * Method to complete the Activity.
     *
     * @param status: True if successful, else false.
     */
    private void completeActivity(boolean status) {
        if (status) {
            setResult(RESULT_OK);
            timer.cancel();
        } else {
            setResult(RESULT_CANCELED);
            Message.logMessage(TAG_CLASS, "FALSE");
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
}