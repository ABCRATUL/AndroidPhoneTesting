package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.VoiceSpeak;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestHelper.PixelGridView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TouchScreenTest extends Activity {

    public ArrayList<Path> pointerPath = new ArrayList<>();
    public ArrayList<Paint> pointerPaint = new ArrayList<>();
    public String[] pointerColor = {"#ff9900", "#990088", "#f00099", "#9999ff", "#888800"};
    FrameLayout mDrawingLayout;
    LinearLayout mTextLayout;
    TextView TouchCount_text;
    TextView pointer1_text;
    TextView pointer2_text;
    TextView pointer3_text;
    TextView pointer4_text;
    TextView pointer5_text;
    Timer timer;
    int numberOfPointers = 0;
    int maxPointers = 5;
    public Path[] circlePath = new Path[maxPointers];
    Paint mPaint;
    int pointerIndex;
    int pointerID;
    int touchRadius = 120;
    int score = 0;
    float mX;
    float mY;
    Canvas mCanvas;
    ArrayList<pointerView> pointerViewList = new ArrayList<>();
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Please swipe the entire screen with you finger to change the color")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        //Creating dialog box
        alert = builder.create();
        //Setting the title manually
        alert.setTitle("TouchScreen Test");
        try {
            alert.show();
        } catch (Exception e) {
            alert.dismiss();
            Message.logMessage(TouchScreenTest.class.getSimpleName(), e.toString());
        }

        PixelGridView pixelGrid = new PixelGridView(this);
        pixelGrid.setNumColumns(4);
        pixelGrid.setNumRows(6);

        setContentView(pixelGrid);
    }

    private void resetTextFields() {
        String pointerTextFill = " ";
        pointer1_text.setText(pointerTextFill);
        pointer2_text.setText(pointerTextFill);
        pointer3_text.setText(pointerTextFill);
        pointer4_text.setText(pointerTextFill);
        pointer5_text.setText(pointerTextFill);
    }

    private void setTextFields(int ID, float x, float y) {
        String pointerTextFill = "P: " + (ID + 1) + " (" + x + " , " + y + ")";
        if (ID == 0) {
            pointer1_text.setText(pointerTextFill);
        }
        if (ID == 1) {
            pointer2_text.setText(pointerTextFill);
        }
        if (ID == 2) {
            pointer3_text.setText(pointerTextFill);
        }
        if (ID == 3) {
            pointer4_text.setText(pointerTextFill);
        }
        if (ID == 4) {
            pointer5_text.setText(pointerTextFill);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        alert.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class pointerView extends View {

        private final Paint circlePaint = new Paint();
        Bitmap mBitmap;
        int pID;
        float LocX;
        float LocY;

        public pointerView(Context context, int pointer) {
            super(context);
            setFocusable(true);
            setFocusableInTouchMode(true);

            pID = pointer;
            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setColor(Color.parseColor(pointerColor[pID]));
            circlePaint.setStrokeJoin(Paint.Join.ROUND);
            circlePaint.setStrokeCap(Paint.Cap.ROUND);
            circlePaint.setAntiAlias(true);
        }

        public void setmX(float x) {
            this.LocX = x;
        }

        public void setmY(float y) {
            this.LocY = y;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            //Log.e("TOUCH", "In OnSizeChanged method");
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //Log.e("TOUCH", "In onDraw method: " +pID);
            super.onDraw(canvas);
            score = 1;
            canvas.drawBitmap(mBitmap, 0f, 0f, null);
            mCanvas = canvas;
            if (numberOfPointers >= 1) {
                canvas.drawPath(pointerPath.get(pID), pointerPaint.get(pID));
                canvas.drawPath(circlePath[pID], circlePaint);
            }
        }
    }
}
