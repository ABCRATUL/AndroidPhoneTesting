package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Adapters.ManualGridAdapter;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.PriceGetter;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.BackButtonTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.CameraFrontTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.CameraRearTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.ChargerTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.FingerprintTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.HeadPhoneTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.HomeButtonTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.MicroPhoneTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.PowerButtonTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.ProximityTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.RGBTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.ScreenBrightnessTest;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.SpeakerTest;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.TouchScreenTest;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.VibrationTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.VolumeButtonDownTest;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.VolumeButtonUpTest;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.Test;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

public class ManualTestScreen extends AppCompatActivity {
    AppCompatImageButton _exitApp;
    AppCompatImageView _successBucket, _failedBucket;
    ImageView _currentTest;
    GridView _testGrid;
    ManualGridAdapter gridAdapter;
    Test currentTest;
    private String TAG_CLASS = ManualTestScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_test_screen);
        initializeViews();
        gridAdapter = new ManualGridAdapter(Constants.manualTestList,
                getApplicationContext());
        _testGrid.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                performTest();
            }
        }, 1000);
        _exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp();
            }
        });
    }

    /**
     * Method to initialize the widgets.
     */
    private void initializeViews() {
        _successBucket = findViewById(R.id.successTestManual);
        _failedBucket = findViewById(R.id.failedTestManual);
        _exitApp = findViewById(R.id.exitAppButtonManual);
        _currentTest = findViewById(R.id.currentManualTest);
        _testGrid = findViewById(R.id.manualTestGrid);
    }

    /**
     * Method to invoke individual test activities.
     * This method is called from Activity Result after completing one test.
     */
    private void performTest() {
        try {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void run() {
                    Intent intent;
                    if (Constants.manualTestList.size() > 0) {
                        currentTest = Constants.manualTestList.get(0);
                        _currentTest.setImageResource(currentTest.getTestIconID());
                        switch (currentTest.getTestName()) {
                            case "Touch Screen":
                                intent = new Intent(ManualTestScreen.this,
                                        TouchScreenTest.class);
                                startActivityForResult(intent, Constants.TOUCH_SCREEN_CODE);
                                break;
                            case "Speaker":
                                intent = new Intent(ManualTestScreen.this,
                                        SpeakerTest.class);
                                startActivityForResult(intent, Constants.SPEAKER_CODE);
                                break;
                            case "Volume Up":
                                intent = new Intent(ManualTestScreen.this,
                                        VolumeButtonUpTest.class);
                                startActivityForResult(intent, Constants.VOLUME_BUTTON_UP_CODE);
                                break;
                            case "Volume Down":
                                intent = new Intent(ManualTestScreen.this,
                                        VolumeButtonDownTest.class);
                                startActivityForResult(intent, Constants.VOLUME_BUTTON_DOWN_CODE);
                                break;
                            case "Proximity":
                                intent = new Intent(ManualTestScreen.this,
                                        ProximityTestActivity.class);
                                startActivityForResult(intent, Constants.PROXIMITY_CODE);
                                break;
                            case "Rear Camera":
                                intent = new Intent(ManualTestScreen.this,
                                        CameraRearTestActivity.class);
                                startActivityForResult(intent, Constants.REAR_CAMERA_CODE);
                                break;
                            case "Front Camera":
                                intent = new Intent(ManualTestScreen.this,
                                        CameraFrontTestActivity.class);
                                startActivityForResult(intent, Constants.FRONT_CAMERA_CODE);
                                break;
                            case "Back Button":
                                intent = new Intent(ManualTestScreen.this,
                                        BackButtonTestActivity.class);
                                startActivityForResult(intent, Constants.BACK_BUTTON_CODE);
                                break;
                            case "Home Button":
                                intent = new Intent(ManualTestScreen.this,
                                        HomeButtonTestActivity.class);
                                startActivityForResult(intent, Constants.HOME_BUTTON_CODE);
                                break;
                            case "Power Button":
                                intent = new Intent(ManualTestScreen.this,
                                        PowerButtonTestActivity.class);
                                startActivityForResult(intent, Constants.POWER_BUTTON_CODE);
                                break;
                            case "Vibration":
                                intent = new Intent(ManualTestScreen.this,
                                        VibrationTestActivity.class);
                                startActivityForResult(intent, Constants.VIBRATION_CODE);
                                break;
                            case "Charger":
                                intent = new Intent(ManualTestScreen.this,
                                        ChargerTestActivity.class);
                                startActivityForResult(intent, Constants.CHARGER_CODE);
                                break;
                            case "Headphone":
                                intent = new Intent(ManualTestScreen.this,
                                        HeadPhoneTestActivity.class);
                                startActivityForResult(intent, Constants.HEADPHONE_CODE);
                                break;
                            case "RGB":
                                intent = new Intent(ManualTestScreen.this,
                                        RGBTestActivity.class);
                                startActivityForResult(intent, Constants.RGB_CODE);
                                break;
                            case "MicroPhone":
                                intent = new Intent(ManualTestScreen.this,
                                        MicroPhoneTestActivity.class);
                                startActivityForResult(intent, Constants.MICROPHONE_CODE);
                                break;
                            case "Brightness":
                                intent = new Intent(ManualTestScreen.this,
                                        ScreenBrightnessTest.class);
                                startActivityForResult(intent, Constants.SCREEN_BRIGHTNESS_CODE);
                                break;
                            case "Fingerprint":
                                intent = new Intent(ManualTestScreen.this,
                                        FingerprintTestActivity.class);
                                startActivityForResult(intent, Constants.FINGER_PRINT_CODE);

                                break;
                        }
                        //Constants.manualTestList.remove(currentTest);
                        gridAdapter.notifyDataSetChanged();
                    } else {
                        goToTestStatus(true);
                    }
                }
            }, 2000);
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
            Message.toastMessage(getApplicationContext(),
                    "Something went Wrong.", "");
            goToTestStatus(false);
        }
    }

    /**
     * Method to go to Test Status Screen.
     *
     * @param status: The Status of the test, true if successful, else false.
     */
    private void goToTestStatus(boolean status) {
        Intent statusIntent = new Intent(ManualTestScreen.this,
                ManualTestStatusActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.TEST_STATUS_KEY, status);
        statusIntent.putExtras(bundle);
        startActivity(statusIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Constants.successManualTestList.add(currentTest);
            YoYo.with(Techniques.Shake).duration(1000).playOn(_successBucket);
        } else if (resultCode == RESULT_CANCELED) {
            YoYo.with(Techniques.Shake).duration(1000).playOn(_failedBucket);
            Constants.failedManualTestList.add(currentTest);
        }
        Constants.manualTestList.remove(currentTest);
        if (Constants.manualTestList.size() >= 0) {
            gridAdapter = new ManualGridAdapter(Constants.manualTestList, getApplicationContext());
            Message.logMessage(TAG_CLASS, "FAILED:" + Constants.failedManualTestList.size() + "");
            Message.logMessage(TAG_CLASS, "SUCCESS:" + Constants.successManualTestList.size() + "");
            _testGrid.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
            performTest();
        } else {
            goToTestStatus(true);
        }
    }

    /**
     * This is the method to exit the app.
     */
    @SuppressLint("ObsoleteSdkInt")
    private void exitApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.finishAffinity();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.finishAndRemoveTask();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Message.toastMessage(getApplicationContext(),
                    "Please wait while we perform the diagnostics.", "");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}