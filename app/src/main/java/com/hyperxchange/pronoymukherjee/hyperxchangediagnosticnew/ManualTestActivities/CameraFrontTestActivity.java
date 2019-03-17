package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.TestApi;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CameraFrontTestActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceView mPreview;
    boolean testStatus = false;
    Timer closeActivityTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_rear_test);
        mPreview = findViewById(R.id.cameraSurface);
        mPreview.getHolder().addCallback(CameraFrontTestActivity.this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        try {
            camera = Camera.open(1);

            camera.setDisplayOrientation(90);
            Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    testStatus = true;
                    completeActivity(true);
                }
            };
            camera.startPreview();
            camera.setPreviewCallback(previewCallback);
            closeActivityTimer = new Timer();
            closeActivityTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    completeActivity(false);
                }
            }, Constants.TEST_TIMER);
        } catch (RuntimeException e) {
            Message.logMessage(CameraFrontTestActivity.class.getSimpleName(), e.toString());
            completeActivity(false);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = camera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width, selected.height);
        camera.setParameters(params);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                camera.release();
                //completeActivity(true);
            }
        }, 2500);
    }

    private void completeActivity(boolean status) {
        if (status) {
            closeActivityTimer.cancel();
            camera.release();
            setResult(RESULT_OK);
        } else
            setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
