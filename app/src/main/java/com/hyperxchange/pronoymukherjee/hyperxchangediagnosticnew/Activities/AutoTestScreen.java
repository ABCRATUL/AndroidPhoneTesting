package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Adapters.TestItemAdapter;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.PriceGetter;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.TestApi;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.Test;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class AutoTestScreen extends AppCompatActivity {
    public String TAG_CLASS = AutoTestScreen.class.getSimpleName();
    RecyclerView testList;
    RecyclerView.LayoutManager layoutManager;
    ImageView _currentTestImage;
    AppCompatImageButton _exitApp;
    AppCompatImageView _successBucket, _failedBucket;
    CircularProgressBar _circularProgressBar;
    ProgressBar _progressBar;
    Context context;
    int score = 0, progress = 0;
    TestItemAdapter testItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_test_screen);
        initializeViews();
        context = getApplicationContext();
        layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        testList.setLayoutManager(layoutManager);
        testItemAdapter = new TestItemAdapter(Constants.automatedTestList);
        testList.setAdapter(testItemAdapter);
        _successBucket.setImageResource(R.drawable.ic_sucess_bucket);
        _failedBucket.setImageResource(R.drawable.ic_failed_bucket);
        _circularProgressBar.setProgress(0);
        _circularProgressBar.setProgressWithAnimation(1, 1500);
        _exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp();
            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long storage = Long.parseLong(Constants.DEVICE_STORAGE);
                Message.logMessage(TAG_CLASS, "RAW STORAGE: " + storage);
                if (storage >= 1 && storage <= 2)
                    storage = 2;
                else if (storage >= 3 && storage <= 4)
                    storage = 4;
                else if (storage >= 5 && storage <= 8)
                    storage = 8;
                else if (storage >= 10 && storage <= 15)
                    storage = 16;
                else if (storage >= 20 && storage <= 30)
                    storage = 32;
                else if (storage >= 40 && storage <= 64)
                    storage = 64;
                else if (storage >= 70 && storage <= 128)
                    storage = 128;
                else storage = 256;
                Constants.DEVICE_STORAGE = String.valueOf(storage);
                Message.logMessage(TAG_CLASS, "STORAGE: " + Constants.DEVICE_STORAGE);
            }
        });
        thread.start();
        performTest();
    }

    /**
     * Method to initialize all the views of the widgets.
     */
    private void initializeViews() {
        testList = findViewById(R.id.pendingTestList);
        _currentTestImage = findViewById(R.id.currentTestImage);
        _exitApp = findViewById(R.id.exitAppButton);
        _successBucket = findViewById(R.id.successTest);
        _failedBucket = findViewById(R.id.failedTest);
        _circularProgressBar = findViewById(R.id.circularProgressBar);
        _progressBar = findViewById(R.id.progressBar);
    }

    /**
     * This is the method which is used to perform the automated test.
     * This is a recursive method to perform all the automated tests.
     */
    private void performTest() {
        score = 0;
        _circularProgressBar.setProgressWithAnimation(20, 1500);
        try {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    if (Constants.automatedTestList.size() > 0) {
                        _progressBar.setProgress(progress);
                        progress += 12;
                        Test currentTest = Constants.automatedTestList.get(0);
                        try {
                            _currentTestImage.setImageResource(currentTest.getTestIconID());
                        } catch (NullPointerException e) {
                            Message.logMessage(TAG_CLASS, e.toString());
                        }
                        //YoYo.with(Techniques.Shake).duration(1500).playOn(_currentTestImage);
                        switch (currentTest.getTestName()) {
                            case "Ram":
                                score = TestApi.testRam(context);
                                Message.logMessage(TAG_CLASS, "RAM: " + Constants.DEVICE_RAM);
                                break;
                            case "Battery":
                                score = TestApi.testBattery(context);
                                break;
                            case "Wifi":
                                score = TestApi.testNetwork(context);
                                break;
                            case "Bluetooth":
                                score = TestApi.testBluetooth(context);
                                break;
                            case "NFC":
                                score = TestApi.testNFC(context);
                                break;
                            case "Flash":
                                score = TestApi.testFlashAvailability(context);
                                break;
                            case "Accelerometer":
                                score = TestApi.testAcclerometer(context);
                                break;
                            case "Gyroscope":
                                score = TestApi.testGyroscope(context);
                                break;
                            case "External Storage":
                                score = TestApi.testExternalStorage(context);
                                break;
                        }
                        Message.logMessage(TAG_CLASS, score + "");
                        try {
                            _circularProgressBar.setProgressWithAnimation(90, 1500);
                        } catch (NullPointerException e) {
                            Message.logMessage(TAG_CLASS, e.toString());
                        }
                        currentTest.setScore(score);
                        Constants.automatedTestList.remove(currentTest);
                        testItemAdapter.notifyDataSetChanged();
                        _circularProgressBar.setProgressWithAnimation(100, 1000);
                        if (score > 0) {
                            Constants.successTestList.add(currentTest);
                            YoYo.with(Techniques.Shake).duration(1500).playOn(_successBucket);
                        } else if (score == 0) {
                            Constants.failedTestList.add(currentTest);
                            YoYo.with(Techniques.Shake).duration(1500).playOn(_failedBucket);
                        }
                        performTest();
                    } else {
                        goToManualTest(true);
                    }
                    //_circularProgressBar.setProgressWithAnimation(100,1000);
                }
            }, 3000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        _circularProgressBar.setProgressWithAnimation(100, 1000);
                    } catch (NullPointerException e) {
                        Message.logMessage(TAG_CLASS, e.toString());
                    }
                }
            }, 2000);
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
            Message.toastMessage(getApplicationContext(),
                    "Something went Wrong.", "");
            goToManualTest(false);
        }
    }

    /**
     * Method to go to Manual Test activity.
     *
     * @param status: true if successful, else false.
     */
    private void goToManualTest(boolean status) {
        Intent getResultIntent = new Intent(AutoTestScreen.this,
                TestStatusActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.TEST_STATUS_KEY, status);
        getResultIntent.putExtras(bundle);
        startActivity(getResultIntent);
        finish();
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
    protected void onStop() {
        super.onStop();
        if (testList != null)
            testList = null;
        if (context != null)
            context = null;
        if (layoutManager != null)
            layoutManager = null;
        if (_currentTestImage != null)
            _currentTestImage = null;
        if (_circularProgressBar != null)
            _circularProgressBar = null;
        if (_failedBucket != null)
            _failedBucket = null;
        if (_successBucket != null)
            _successBucket = null;
        if (testItemAdapter != null)
            testItemAdapter = null;
        Runtime.getRuntime().gc();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Message.toastMessage(getApplicationContext(),
                    "Please wait while we are performing the diagnostics", "");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
