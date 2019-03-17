package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.telephony.TelephonyManager;
import android.view.View;

import com.github.anastr.speedviewlib.SpeedView;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.TestApi;
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
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.ScreenBrightnessTest;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.TouchScreenTest;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities.VibrationTestActivity;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.ArrayList;
import java.util.List;

public class StartTestScreen extends AppCompatActivity {
    public String TAG_CLASS = StartTestScreen.class.getSimpleName();
    String modelName, imeiNumber;
    AppCompatTextView _modelName, _imeiNumber, _batteryPercentage;
    AppCompatButton _marketPlace, _startTest;
    boolean permissionGranted = false;
    SpeedView _ramUsage;
    Thread thread;
    float percentage;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test_screen);
        modelName = Build.MODEL;
        initializeViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        } else {
            permissionGranted = true;
            getTelephoneDetails();
        }
        _startTest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    //TODO: First start the Auto Test.
                    Intent startTestIntent = new Intent(StartTestScreen.this,
                            AutoTestScreen.class);
                    //Getting the Storage Data of the Device.
                    StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                    long bytes = (long) statFs.getBlockSize() * (long) statFs.getBlockCount();
                    Constants.DEVICE_STORAGE = String.valueOf(bytes / 1000000000);
                    startActivity(startTestIntent);
                    //finish();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.DIALOG_MSG, "Please connect the device to the internet.");
                    Intent dialogIntent = new Intent(StartTestScreen.this,
                            PermissionExplainDialog.class);
                    dialogIntent.putExtras(bundle);
                    startActivityForResult(dialogIntent, Constants.DIALOG_INTERNET_CODE);
                }
            }
        });
        _marketPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager()
                        .getLaunchIntentForPackage("com.hyperxchange.hyperxchange");
                if (intent == null) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + "com.hyperxchange.hyperxchange"));
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        _modelName.setText(modelName);
        thread = new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                setRamUsage();
                TestApi.testBattery(getApplicationContext());
                _batteryPercentage.setText(Constants.DEVICE_BATTERY_PERCENTAGE + "%");
            }
        });
        thread.start();
    }

    /**
     * This is the method to get the IMEI Number of the device.
     */
    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getTelephoneDetails() {
        TelephonyManager telephonyManager = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        if (permissionGranted) {
            assert telephonyManager != null;
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    imeiNumber = telephonyManager.getDeviceId(0);
                } else {
                    imeiNumber = telephonyManager.getDeviceId();
                }
                _imeiNumber.setText(imeiNumber);
            } catch (SecurityException e) {
                Message.logMessage(TAG_CLASS, e.toString());
                Message.toastMessage(getApplicationContext(),
                        "Couldn't get teh IMEI Number.", "");
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canWriteSettings()) {
                Intent intent = new Intent(StartTestScreen.this,
                        PermissionExplainDialog.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.DIALOG_MSG, getResources()
                        .getString(R.string.allow_system_settings));
                intent.putExtras(bundle);
                telephonyManager = null;
                startActivityForResult(intent, Constants.WRITE_SETTINGS_CODE);
            }
        }
    }

    /**
     * Method to initialize the Views.
     */
    private void initializeViews() {
        _modelName = findViewById(R.id.modelName);
        _imeiNumber = findViewById(R.id.imeiNumber);
        _startTest = findViewById(R.id.startTestButton);
        _marketPlace = findViewById(R.id.marketPlaceButton);
        _ramUsage = findViewById(R.id.ramUsage);
        _batteryPercentage = findViewById(R.id.batteryPercentageShowValue);
    }

    /**
     * This is the method to check and request for the app permissions.
     */
    private void checkPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();
        int cameraPermission = ContextCompat
                .checkSelfPermission(this, Manifest.permission.CAMERA);
        int externalStoragePermission = ContextCompat
                .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int locationPermission = ContextCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int phoneStatePermission = ContextCompat
                .checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (cameraPermission != PackageManager.PERMISSION_GRANTED)
            permissionsNeeded.add(Manifest.permission.CAMERA);
        if (externalStoragePermission != PackageManager.PERMISSION_GRANTED)
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (locationPermission != PackageManager.PERMISSION_GRANTED)
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if (phoneStatePermission != PackageManager.PERMISSION_GRANTED)
            permissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        requestPermission(permissionsNeeded.toArray(new String[permissionsNeeded.size()]));
        permissionsNeeded = null;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(String[] permissionsNeeded) {
        Message.logMessage(TAG_CLASS, permissionsNeeded.length + "");
        if (permissionsNeeded.length > 0) {
            ActivityCompat.requestPermissions(this,
                    permissionsNeeded,
                    Constants.PERMISSION_REQUEST_CODE);
        } else {
            permissionGranted = true;
            getTelephoneDetails();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    Intent explainIntent = new Intent(StartTestScreen.this,
                            PermissionExplainDialog.class);
                    startActivityForResult(explainIntent, Constants.PERMISSION_REQUEST_CODE);
                    break;
                }
            }
            permissionGranted = true;
            getTelephoneDetails();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
                checkPermissions();
        } else if (requestCode == Constants.DIALOG_INTERNET_CODE) {
            //TODO: Start test after checking internet.
        } else if (requestCode == Constants.WRITE_SETTINGS_CODE) {
            if (resultCode == RESULT_OK) {
                changeWriteSettings();
            }
        }
    }

    /**
     * This is the method to check whether the device is connected to internet or not.
     *
     * @return true: if connected else false.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Method to check the write Settings permission.
     *
     * @return true: If Can write else false.
     */
    private boolean canWriteSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.System.canWrite(getApplicationContext());
        }
        return true;
    }

    /**
     * Method to change the System Settings write permissions.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeWriteSettings() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        startActivityForResult(intent, Constants.WRITE_SETTINGS_CODE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (_modelName != null)
            _modelName = null;
        if (_imeiNumber != null)
            _imeiNumber = null;
        if (_marketPlace != null)
            _marketPlace = null;
        if (_startTest != null)
            _startTest = null;
        Runtime.getRuntime().gc();
    }

    /**
     * Method to calculate and show the RAM Usage.
     */
    private void setRamUsage() {
        TestApi.testRam(getApplicationContext());
        int totalRam = Integer.parseInt(Constants.DEVICE_RAM);
        int availRam = Integer.parseInt(Constants.DEVICE_RAM_USAGE);
        percentage = ((float) availRam) / ((float) totalRam);
        percentage = percentage * 100;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _ramUsage.speedTo(percentage, 3000);
            }
        });
    }
}