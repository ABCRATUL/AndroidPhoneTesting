package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.PriceGetter;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.VoiceSpeak;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.ArrayList;

public class TestStatusActivity extends AppCompatActivity {
    ImageView _statusIcon;
    AppCompatImageButton _successBucket, _failedBucket, _nextButton;
    AppCompatTextView _successNumber, _failedNumber;
    int statusIconID;
    private String TAG_CLASS = TestStatusActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_test_status);
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
        initializeViews();
        final Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            boolean isCompleted = bundle.getBoolean(Constants.TEST_STATUS_KEY);
            if (isCompleted)
                statusIconID = R.drawable.ic_test_complete;
            else statusIconID = R.drawable.ic_test_failed;
            try {
                _statusIcon.setImageResource(statusIconID);
            } catch (Exception e) {
                Message.logMessage(TAG_CLASS, e.toString());
            }
        }
        try {
            _successBucket.setBackground(getResources().getDrawable(R.drawable.ic_sucess_bucket));
            _failedBucket.setBackground(getResources().getDrawable(R.drawable.ic_failed_bucket));
            _successNumber.setText(String.valueOf(Constants.successTestList.size()));
            _failedNumber.setText(String.valueOf(Constants.failedTestList.size()));

            _successBucket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openDialogIntent = new Intent(TestStatusActivity.this,
                            TestStatusDialogActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(Constants.TEST_STATUS_DIALOG_KEY, true);
                    bundle.putBoolean(Constants.TEST_IS_MANUAL, false);
                    openDialogIntent.putExtras(bundle);
                    startActivity(openDialogIntent);
                }
            });
            _failedBucket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openDialogIntent = new Intent(TestStatusActivity.this,
                            TestStatusDialogActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(Constants.TEST_STATUS_DIALOG_KEY, false);
                    bundle.putBoolean(Constants.TEST_IS_MANUAL, false);
                    openDialogIntent.putExtras(bundle);
                    startActivity(openDialogIntent);
                }
            });
            _nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO:Go to Manual Test.
                    Constants.automatedTestList = new ArrayList<>();
                    Constants.fillAutomatedTestList();
                    Intent manualTestIntent = new Intent(TestStatusActivity.this,
                            ManualTestScreen.class);
                    startActivity(manualTestIntent);
                    finish();
                }
            });
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                PriceGetter priceGetter = new PriceGetter(getApplicationContext());
                priceGetter.getPhoneId();
            }
        });
        thread.start();
    }

    /**
     * Method to initialize the Views.
     */
    private void initializeViews() {
        _statusIcon = findViewById(R.id.testStatusIcon);
        _successBucket = findViewById(R.id.successTestSet);
        _failedBucket = findViewById(R.id.failedTestSet);
        _nextButton = findViewById(R.id.next);
        _successNumber = findViewById(R.id.passedNumber);
        _failedNumber = findViewById(R.id.failedNumber);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.automatedTestList = null;
        if (_statusIcon != null)
            _statusIcon = null;
        if (_successBucket != null)
            _successBucket = null;
        if (_failedBucket != null)
            _failedBucket = null;
        if (_nextButton != null)
            _nextButton = null;
        Runtime.getRuntime().gc();
    }
}
