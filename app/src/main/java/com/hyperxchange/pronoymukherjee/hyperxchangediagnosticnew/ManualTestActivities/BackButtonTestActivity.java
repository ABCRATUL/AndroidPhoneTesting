package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.Timer;
import java.util.TimerTask;

public class BackButtonTestActivity extends AppCompatActivity {
    Timer timer;

    private String TAG_CLASS = BackButtonTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_back_button_test);
            setTitle("");
            this.setFinishOnTouchOutside(false);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    completeActivity(false);
                }
            }, Constants.TEST_TIMER);
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
            Message.toastMessage(getApplicationContext(),
                    "Couldn't Load images due to low memory", "");
        }
    }

    /**
     * Method to complete the Activity.
     *
     * @param status: true if Successful.
     */
    private void completeActivity(boolean status) {
        if (status) {
            setResult(RESULT_OK);
            try {
                timer.cancel();
            } catch (NullPointerException e) {
                Message.logMessage(TAG_CLASS, e.toString());
            }
        } else
            setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Message.toastMessage(getApplicationContext(), "Back Button Pressed.", "");
            completeActivity(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null)
            timer = null;
        System.gc();
    }
}