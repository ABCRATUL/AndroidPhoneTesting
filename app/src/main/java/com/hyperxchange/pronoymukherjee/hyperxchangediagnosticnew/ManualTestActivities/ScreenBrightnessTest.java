package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.KeyEvent;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.Timer;
import java.util.TimerTask;

public class ScreenBrightnessTest extends AppCompatActivity {
    AppCompatSeekBar seekBar;
    int defaultBrightness;
    Timer timer;
    int defaultBrightnessMode;
    private String TAG_CLASS = ScreenBrightnessTest.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_brightness_test);
        this.setFinishOnTouchOutside(false);
        setTitle("");
        initializeViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canWriteSettings()) {
                changeWriteSettings();
            } else {
                try {
                    getDefaultBrightnessState();
                    changeBrightness();
                } catch (Settings.SettingNotFoundException e) {
                    Message.logMessage(TAG_CLASS, e.toString());
                }
            }
        } else {
            try {
                getDefaultBrightnessState();
                changeBrightness();
            } catch (Settings.SettingNotFoundException e) {
                Message.logMessage(TAG_CLASS, e.toString());
            }
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                completeActivity(false);
            }
        }, Constants.TEST_TIMER);
    }

    /**
     * This is the method to get the Brightness mode.
     * <p>
     * throws: Settings.SettingNotFoundException
     */
    private void getDefaultBrightnessState() throws Settings.SettingNotFoundException {
        defaultBrightnessMode = Settings.System.getInt(getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE);
        if (defaultBrightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            defaultBrightness = 70;
        } else
            defaultBrightness = getDefaultBrightness();
    }

    /**
     * Method to initialize the views.
     */
    private void initializeViews() {
        seekBar = findViewById(R.id.seekBar);
        timer = new Timer();
    }

    /**
     * Method to check settings write permissions.
     *
     * @return true if permissions provided.
     */
    private boolean canWriteSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.System.canWrite(getApplicationContext());
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeWriteSettings() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        startActivityForResult(intent, Constants.WRITE_SETTINGS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.WRITE_SETTINGS_CODE) {
            if (resultCode == RESULT_OK) {
                defaultBrightness = getDefaultBrightness();
                Settings.System.putInt(getContentResolver(), Settings.System
                                .SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                changeBrightness();
            } else {
                Message.toastMessage(getApplicationContext(),
                        "Please allow to modify system Settings",
                        "");
            }
        }
    }

    /**
     * The Method which is changing the brightness.
     */
    private void changeBrightness() {
        CountDownTimer countDownTimer = new
                CountDownTimer(9000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        millisUntilFinished = 9000 - millisUntilFinished;
                        Message.logMessage(TAG_CLASS, millisUntilFinished + "");
                        int brightnessValue = (int) ((millisUntilFinished / 1000) * 10);
                        seekBar.setProgress(brightnessValue);
                        Settings.System.putInt(getContentResolver(),
                                Settings.System.SCREEN_BRIGHTNESS, brightnessValue);
                    }

                    @Override
                    public void onFinish() {
                        Settings.System.putInt(getContentResolver(),
                                Settings.System.SCREEN_BRIGHTNESS, defaultBrightness);
                        if (defaultBrightnessMode == Settings
                                .System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                            Settings.System.putInt(getContentResolver(),
                                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                        }
                        completeActivity(true);
                    }
                };
        countDownTimer.start();
    }

    /**
     * The method which is getting the default brightness.
     *
     * @return default brightness.
     */
    private int getDefaultBrightness() {
        try {
            return Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
        return 70;
    }

    private void completeActivity(boolean status) {
        Message.logMessage(TAG_CLASS, status + "");
        if (status) {
            setResult(RESULT_OK);
            timer.cancel();
        } else {
            setResult(RESULT_CANCELED);
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
