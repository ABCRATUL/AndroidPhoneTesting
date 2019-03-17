package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CameraRearTestActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceView mPreview;
    boolean isSafeToTakePicture = false;
    private String TAG_CLASS = CameraRearTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_rear_test);
        mPreview = findViewById(R.id.cameraSurface);
        mPreview.getHolder().addCallback(CameraRearTestActivity.this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        final Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @SuppressWarnings("ResultOfMethodCallIgnored")
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                File hyperXchangeDir = new File(Environment.getExternalStorageDirectory()
                        + File.separator + Constants.HX_FOLDER_NAME + File.separator
                        + Constants.HX_CAMERA_FOLDER);
                if (!hyperXchangeDir.exists()) {
                    hyperXchangeDir.mkdirs();
                }
                try {
                    if (bytes != null) {
                        File rearImage = new File(hyperXchangeDir, Constants.HX_CAMERA_FILE_NAME);
                        FileOutputStream fileOutputStream = new FileOutputStream(rearImage);
                        fileOutputStream.write(bytes);
                        fileOutputStream.close();
                        hyperXchangeDir = null;
                        rearImage = null;
                        completeActivity(true);
                        //camera.release();
                    } else {
                        isSafeToTakePicture = true;
                    }
                } catch (Exception e) {
                    Message.logMessage(TAG_CLASS, e.toString());
                }
                isSafeToTakePicture = true;
            }
        };
        Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] bytes, final Camera camera) {
                try {
                    if (isSafeToTakePicture) {
                        try {
                            camera.takePicture(null, null, pictureCallback);
                        } catch (RuntimeException e) {
                            Message.logMessage(TAG_CLASS, e.toString());
                        }
                        isSafeToTakePicture = false;
                        //completeActivity(true);
                    } else {
                        Message.logMessage(TAG_CLASS, "Not able to take picture.");
                    }

                } catch (RuntimeException e) {
                    Message.logMessage(TAG_CLASS, e.toString());
                }
            }
        };
        try {
            camera = Camera.open(0);    // For back_Camera
            camera.setDisplayOrientation(90);
            camera.setPreviewCallback(previewCallback);
            isSafeToTakePicture = true;
            Message.logMessage(TAG_CLASS, "Preview started");
            //completeActivity(true);
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
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
        isSafeToTakePicture = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            Message.logMessage(TAG_CLASS, "Surface Destroyed.");
        } catch (RuntimeException e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
    }

    private void completeActivity(final boolean status) {
        if (status) {
            setResult(RESULT_OK);
        } else
            setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        try {
            if (camera != null) {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.release();
                camera = null;
            }
            if (mPreview != null)
                mPreview = null;
        } catch (RuntimeException e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
        Runtime.getRuntime().gc();
    }
}
