package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.android.volley.VolleyError;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.DeviceInformation;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.ExcelCreator;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.HTTPConnector;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.ParamsCreator;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.Phone;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class TestScoreScreen extends AppCompatActivity implements HTTPConnector.ResponseListener {
    AppCompatImageView _sadFace, _okayFace, _happyFace;
    AppCompatTextView _sadText, _okayText, _happyText, _testScore, _priceValue;
    AppCompatButton _submitButton, _exitButton;
    CircularProgressBar _circularProgressBar;
    int basePrice = 12000;
    int physicalStatus = -1;
    boolean isInsertPhone = false, isInsertReport = false;
    ProgressDialog _progressDialog;
    private String TAG_CLASS = TestScoreScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_score_screen);
        initializeViews();
        _circularProgressBar
                .setProgress(Constants.successManualTestList.size() +
                        Constants.successTestList.size());
        _testScore.setText(String.valueOf(Constants.successManualTestList.size()
                + Constants.successTestList.size()));
        try {
            basePrice = Integer.parseInt(Constants.DEVICE_PRICE);
        } catch (NumberFormatException e) {
            basePrice = 12000;
        }

        _priceValue.setText(String.valueOf(basePrice));
        _sadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _sadFace.setImageResource(R.drawable.ic_sad_face);
                _happyFace.setImageResource(R.drawable.ic_happy_black);
                _okayFace.setImageResource(R.drawable.ic_okay_black);
                physicalStatus = Constants.SAD_CODE;
                changePrice(physicalStatus);
            }
        });
        _okayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _sadFace.setImageResource(R.drawable.ic_sad_black);
                _happyFace.setImageResource(R.drawable.ic_happy_black);
                _okayFace.setImageResource(R.drawable.ic_okay_face);
                physicalStatus = Constants.OKAY_CODE;
                changePrice(physicalStatus);
            }
        });
        _happyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _sadFace.setImageResource(R.drawable.ic_sad_black);
                _happyFace.setImageResource(R.drawable.ic_happy_face);
                _okayFace.setImageResource(R.drawable.ic_okay_black);
                physicalStatus = Constants.HAPPY_CODE;
                changePrice(physicalStatus);
            }
        });
        _exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp();
            }
        });
        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (physicalStatus != -1) {
                    boolean isWrite = ExcelCreator.createExcel(getApplicationContext());
                    if (isWrite) {
                        Message.toastMessage(getApplicationContext(),
                                "You can find the report at: " +
                                        Environment.getExternalStorageDirectory()
                                        + Constants.HX_FOLDER_NAME + File.separator + Constants.HX_REPORT_FOLDER_NAME,
                                "long");
                        //Uploading the data to the server.
                        checkDevice();
                    } else {
                        Message.toastMessage(getApplicationContext(),
                                "Sorry, we couldn't generate the report.", "long");
                    }
                } else
                    Message.toastMessage(getApplicationContext(),
                            "Please select a condition of the device.", "");
            }
        });
    }

    /**
     * Method to initialize Widgets.
     */
    private void initializeViews() {
        _testScore = findViewById(R.id.testScore);
        _sadFace = findViewById(R.id.sadFace);
        _okayFace = findViewById(R.id.okayFace);
        _happyFace = findViewById(R.id.happyFace);
        _sadText = findViewById(R.id.sadText);
        _okayText = findViewById(R.id.okayText);
        _happyText = findViewById(R.id.happyText);
        _submitButton = findViewById(R.id.submitButtonTestScore);
        _exitButton = findViewById(R.id.exitAppButtonTestScore);
        _circularProgressBar = findViewById(R.id.testProgress);
        _priceValue = findViewById(R.id.priceValue);
        _progressDialog = new ProgressDialog(this);
        _progressDialog.setCancelable(false);
        _progressDialog.setMessage(getString(R.string.loading_message));
    }

    /**
     * Method to change the Price.
     *
     * @param CODE: If OKAY or Bad or Good.
     */
    @SuppressLint("SetTextI18n")
    private void changePrice(int CODE) {
        if (CODE == Constants.SAD_CODE)
            _priceValue.setText("5000/-");
        else if (CODE == Constants.OKAY_CODE)
            _priceValue.setText("10000/-");
        else if (CODE == Constants.HAPPY_CODE)
            _priceValue.setText("12000/-");
    }

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

    /**
     * Method to check whether this device has already be tested or not.
     */
    private void checkDevice() {
        DeviceInformation deviceInformation = new DeviceInformation(getApplicationContext());
        HTTPConnector connector = new HTTPConnector(TAG_CLASS,
                getApplicationContext(),
                Constants.QUERY_URL, ParamsCreator
                .createParamsForSelectPhone(deviceInformation.getIMEINumber(true)),
                this);
        connector.makeQuery(TAG_CLASS);
        _progressDialog.show();
        Message.logMessage(TAG_CLASS, "QUERY FOR CHECKING DEVICE");
        deviceInformation = null;
    }

    @Override
    public void onResponse(JSONObject response) {
        Message.logMessage(TAG_CLASS, response.toString());
        _progressDialog.dismiss();
        try {
            if (!isInsertPhone) {
                JSONArray array = response.getJSONArray(Constants.JSON_RESULT);
                if (array.length() == 0) {
                    isInsertPhone = true;
                    uploadPhoneToServer();
                } else {
                    Message.logMessage(TAG_CLASS, "Will Upload Report");
                    isInsertReport = true;
                    isInsertPhone = true;
                    uploadReportToServer();
                }
            } else if (isInsertPhone && !isInsertReport) {
                JSONObject res = response.getJSONObject(Constants.JSON_RESULT);
                int affectedRow = res.getInt(Constants.JSON_AFFECT_ROW);
                if (affectedRow > 0) {
                    uploadReportToServer();
                }
            } else if (isInsertReport) {
                /*Intent intent = new Intent(TestScoreScreen.this, SplashScreen.class);
                startActivity(intent);
                finish();*/
                Message.toastMessage(getApplicationContext(),
                        "Report Submitted Successfully.", "");
                exitApp();
            }
        } catch (JSONException e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Message.logMessage(TAG_CLASS, error.toString());
        _progressDialog.dismiss();
        Message.toastMessage(getApplicationContext(),
                "Ops! Something went wrong.", "");
    }

    /**
     * Method to upload the Phone Details to the server if not present.
     */
    private void uploadPhoneToServer() {
        DeviceInformation deviceInformation = new DeviceInformation(getApplicationContext());
        Phone phone = new Phone(deviceInformation.getDeviceManufacturer(),
                deviceInformation.getDeviceModelName(),
                deviceInformation.getSerialNumber(),
                deviceInformation.getIMEINumber(true),
                deviceInformation.getBSSID(),
                deviceInformation.getRegion(),
                deviceInformation.getUUID(),
                Integer.parseInt(Constants.DEVICE_STORAGE),
                String.valueOf(deviceInformation.getBatteryActualCapacity()));
        HTTPConnector httpConnector = new HTTPConnector(TAG_CLASS, getApplicationContext(),
                Constants.QUERY_URL, ParamsCreator.createParamsForInsertPhone(phone),
                this);
        httpConnector.makeQuery(TAG_CLASS);
        _progressDialog.show();
        deviceInformation = null;
    }

    /**
     * Method to upload the Report data to the Server.
     */
    private void uploadReportToServer() {
        isInsertReport = true;
        DeviceInformation deviceInformation = new DeviceInformation(getApplicationContext());
        HTTPConnector connector = new HTTPConnector(TAG_CLASS,
                getApplicationContext(),
                Constants.QUERY_URL, ParamsCreator.createParamsForInsertReport(deviceInformation),
                this);
        connector.makeQuery(TAG_CLASS);
        _progressDialog.show();
        deviceInformation = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (_sadFace != null)
            _sadFace = null;
        if (_okayFace != null)
            _okayFace = null;
        if (_happyFace != null)
            _happyFace = null;
        if (_submitButton != null)
            _submitButton = null;
        Runtime.getRuntime().gc();
    }
}