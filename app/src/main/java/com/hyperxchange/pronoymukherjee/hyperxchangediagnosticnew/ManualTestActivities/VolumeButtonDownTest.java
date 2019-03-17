package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.VoiceSpeak;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.Timer;
import java.util.TimerTask;

public class VolumeButtonDownTest extends AppCompatActivity {
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_button_down_test);
        this.setFinishOnTouchOutside(false);
        setTitle("");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                completeActivity(false);
            }
        }, Constants.TEST_TIMER);
    }

    /**
     * Method to complete the Activity.
     *
     * @param status: true, if successful.
     */
    private void completeActivity(boolean status) {
        if (status) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Message.toastMessage(getApplicationContext(), "volume Down button pressed.", "");
            timer.cancel();
            completeActivity(true);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
