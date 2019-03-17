package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ListView;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Adapters.TestStatusItemAdapter;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

public class TestStatusDialogActivity extends AppCompatActivity {
    AppCompatImageView _testDialogIcon;
    AppCompatImageButton _closeDialogButton;
    AppCompatTextView _emptyView;
    ListView _testList;
    TestStatusItemAdapter statusItemAdapter;
    int statusIconID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_status_dialog);
        this.setFinishOnTouchOutside(false);
        setTitle("");
        initializeViews();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            boolean success = bundle.getBoolean(Constants.TEST_STATUS_DIALOG_KEY);
            boolean isManual = bundle.getBoolean(Constants.TEST_IS_MANUAL);
            if (isManual) {
                setAdapterForManual(success);
            } else {
                setAdapterForAuto(success);
            }
        }
        _closeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeViews() {
        _testDialogIcon = findViewById(R.id.testStatusDialogIcon);
        _closeDialogButton = findViewById(R.id.testStatusDialogClose);
        _testList = findViewById(R.id.testDialogList);
        _emptyView = findViewById(R.id.testStatusEmptyView);
    }

    /**
     * Method to set the adapter for the manual test.
     *
     * @param status: true if Success button is pressed.
     */
    private void setAdapterForManual(boolean status) {
        boolean isEmpty = false;
        if (status) {
            if (Constants.successManualTestList.size() > 0) {
                statusItemAdapter = new
                        TestStatusItemAdapter(this,
                        Constants.successManualTestList);
                isEmpty = false;
                _emptyView.setVisibility(View.GONE);
            } else {
                isEmpty = true;
            }
            statusIconID = R.drawable.ic_sucess_bucket;
        } else {
            if (Constants.failedManualTestList.size() > 0) {
                statusItemAdapter = new TestStatusItemAdapter(this,
                        Constants.failedManualTestList);
                _emptyView.setVisibility(View.GONE);
            } else
                isEmpty = true;
            statusIconID = R.drawable.ic_failed_bucket;
        }
        if (!isEmpty) {
            _testList.setAdapter(statusItemAdapter);
            statusItemAdapter.notifyDataSetChanged();
        } else {
            _testList.setEmptyView(_emptyView);
        }
        _testDialogIcon.setImageResource(statusIconID);
    }

    /**
     * Method to set the adapter for the Auto Test.
     *
     * @param status: true if success button is pressed, else false.
     */
    private void setAdapterForAuto(boolean status) {
        boolean isEmpty = false;
        if (status) {
            if (Constants.successTestList.size() > 0) {
                statusItemAdapter = new
                        TestStatusItemAdapter(this,
                        Constants.successTestList);
                _emptyView.setVisibility(View.GONE);
                isEmpty = false;
            } else {
                isEmpty = true;
            }
            statusIconID = R.drawable.ic_sucess_bucket;
        } else {
            if (Constants.failedTestList.size() > 0) {
                statusItemAdapter = new TestStatusItemAdapter(this,
                        Constants.failedTestList);
                _emptyView.setVisibility(View.GONE);
            } else
                isEmpty = true;
            statusIconID = R.drawable.ic_failed_bucket;
        }
        if (!isEmpty) {
            _testList.setAdapter(statusItemAdapter);
            statusItemAdapter.notifyDataSetChanged();
        } else {
            _testList.setEmptyView(_emptyView);
        }
        _testDialogIcon.setImageResource(statusIconID);
    }
}

