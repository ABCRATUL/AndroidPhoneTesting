package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.VoiceSpeak;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.Timer;
import java.util.TimerTask;

public class ProximityTestActivity extends AppCompatActivity implements SensorEventListener {
    AppCompatTextView _textView;
    Sensor sensor;
    SensorManager sensorManager;
    Timer timer;
    private String TAG_CLASS = ProximityTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_test);
        this.setFinishOnTouchOutside(false);
        setTitle("");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //assert sensorManager != null;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        initializeViews();
        _textView.setText(R.string.proximity_neg_msg);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                completeActivity(false);
            }
        }, Constants.TEST_TIMER);
    }

    /**
     * Method to initialize the views.
     */
    private void initializeViews() {
        _textView = findViewById(R.id.proximityText);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) {
            _textView.setText(R.string.proximity_msg);
            completeActivity(true);
        } else {
            _textView.setText(R.string.proximity_neg_msg);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * Method to complete the Activity.
     *
     * @param status: Whether positive or negative.
     */
    private void completeActivity(boolean status) {
        if (status) {
            setResult(RESULT_OK);
            timer.cancel();
        } else
            setResult(RESULT_CANCELED);
        Message.logMessage(TAG_CLASS, status + "");
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
