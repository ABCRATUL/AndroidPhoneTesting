package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

public class ManualTestStatusActivity extends AppCompatActivity {
    AppCompatImageButton _successButton, _failedButton, _nextButton;
    ImageView _imageView;
    AppCompatTextView _passedNumber, _failedNumber;
    Intent intent;
    private String TAG_CLASS = ManualTestStatusActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_test_status);
        initializeViews();
        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            boolean isSuccess = bundle.getBoolean(Constants.TEST_STATUS_KEY);
            if (isSuccess) {
                _imageView.setImageResource(R.drawable.ic_test_complete);
            } else
                _imageView.setImageResource(R.drawable.ic_test_failed);
        }
        _successButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openDialogIntent = new Intent(ManualTestStatusActivity.this,
                        TestStatusDialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.TEST_STATUS_DIALOG_KEY, true);
                bundle.putBoolean(Constants.TEST_IS_MANUAL, true);
                openDialogIntent.putExtras(bundle);
                startActivity(openDialogIntent);
            }
        });
        _failedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openDialogIntent = new Intent(ManualTestStatusActivity.this,
                        TestStatusDialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.TEST_STATUS_DIALOG_KEY, false);
                bundle.putBoolean(Constants.TEST_IS_MANUAL, true);
                openDialogIntent.putExtras(bundle);
                startActivity(openDialogIntent);
            }
        });
        _nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManualTestStatusActivity.this,
                        TestScoreScreen.class);
                startActivity(intent);
            }
        });
        try {
            _passedNumber.setText(String.valueOf(Constants.successManualTestList.size()));
            _failedNumber.setText(String.valueOf(Constants.failedManualTestList.size()));
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
    }

    /**
     * Method to initialize the Views.
     */
    private void initializeViews() {
        _successButton = findViewById(R.id.successManualTestButton);
        _failedButton = findViewById(R.id.failedManualTestButton);
        _nextButton = findViewById(R.id.manualTestNextButton);
        _imageView = findViewById(R.id.manualTestStatusImage);
        _passedNumber = findViewById(R.id.manualPassedNumber);
        _failedNumber = findViewById(R.id.manualFailedNumber);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (_imageView != null)
            _imageView = null;
        if (_successButton != null)
            _successButton = null;
        if (_failedButton != null)
            _failedButton = null;
        if (_nextButton != null)
            _nextButton = null;
        if (intent != null)
            intent = null;
        if (_passedNumber != null)
            _passedNumber = null;
        if (_failedNumber != null)
            _failedNumber = null;
        Constants.manualTestList = null;
        Runtime.getRuntime().gc();
    }
}
