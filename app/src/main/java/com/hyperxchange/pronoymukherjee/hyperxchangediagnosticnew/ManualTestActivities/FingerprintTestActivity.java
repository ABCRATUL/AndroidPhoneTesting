package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.VoiceSpeak;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

import java.util.Timer;
import java.util.TimerTask;


@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintTestActivity extends AppCompatActivity implements FingerPrintAuthCallback {
    FingerPrintAuthHelper fingerPrintAuthHelper;
    Timer timer;
    boolean hasFingerPrint = true;
    private String TAG_CLASS = FingerprintTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_test);
        setTitle("");
        fingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        fingerPrintAuthHelper.startAuth();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                completeActivity(false);
            }
        }, Constants.TEST_TIMER);
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Message.logMessage(TAG_CLASS, "NO finger print hardware ");
        Message.toastMessage(getApplicationContext(),
                "Your device doesn't support fingerprint", "");
        hasFingerPrint = false;
        completeActivity(false);
    }

    @Override
    public void onNoFingerPrintRegistered() {
        Message.logMessage(TAG_CLASS, "Please register some finger print.");
        Message.toastMessage(getApplicationContext(),
                "You will need to register some finger prints to test.", "");
        completeActivity(false);
    }

    @Override
    public void onBelowMarshmallow() {
        Message.logMessage(TAG_CLASS, "API Level doesn't support finger_print.");
        Message.toastMessage(getApplicationContext(),
                "Your device doesn't support fingerprint", "");
        completeActivity(false);
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Message.toastMessage(getApplicationContext(), "Finger print Checked.", "");
        completeActivity(true);
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        Message.toastMessage(getApplicationContext(),
                "Finger Print Detected", "");
        completeActivity(true);
    }

    /**
     * Method to complete the Activity.
     *
     * @param status: true if successful, else false.
     */
    private void completeActivity(boolean status) {
        fingerPrintAuthHelper.stopAuth();
        if (status) {
            timer.cancel();
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fingerPrintAuthHelper != null)
            fingerPrintAuthHelper = null;
        if (timer != null)
            timer = null;
        Runtime.getRuntime().gc();
    }
}
