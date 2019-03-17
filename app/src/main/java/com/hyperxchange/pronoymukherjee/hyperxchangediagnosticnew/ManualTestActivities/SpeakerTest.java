package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.ManualTestActivities;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Activities.PermissionExplainDialog;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Constants;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper.Message;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SpeakerTest extends AppCompatActivity {
    public String TAG_CLASS = SpeakerTest.class.getSimpleName();
    AppCompatButton _okayButton, _replayButton;
    AppCompatEditText _userInput;
    TextToSpeech textToSpeech;
    int generatedNumber;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_test);
        setTitle("");
        initializeViews();
        this.setFinishOnTouchOutside(false);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                completeActivity(false);
            }
        }, Constants.TEST_TIMER);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
        showDialogWarning();
        _okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int inputNumber = Integer.parseInt(_userInput.getText().toString());
                    if (inputNumber == generatedNumber) {
                        completeActivity(true);
                        timer.cancel();
                        Message.logMessage(TAG_CLASS, "true");
                    }
                } catch (NumberFormatException e) {
                    Message.toastMessage(getApplicationContext(), "Please provide number only",
                            "");
                }
            }
        });
        _replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakAndCheckNumber();
            }
        });
    }

    private void initializeViews() {
        _okayButton = findViewById(R.id.speakerOkayButton);
        _replayButton = findViewById(R.id.speakerReplayButton);
        _userInput = findViewById(R.id.userInputSpeaker);
    }

    /**
     * This is the method to generate Random Number.
     *
     * @return randomNumber.
     */
    private int generateRandomNumber() {
        Random random = new Random();
        int high = 100;
        int low = 1;
        return random.nextInt(high + low) + low;
    }

    /**
     * This is the method to show the dialog to user.
     */
    private void showDialogWarning() {
        Intent warningIntent = new Intent(SpeakerTest.this, PermissionExplainDialog.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DIALOG_MSG, Constants.SPEAKER_VOLUME_MSG);
        warningIntent.putExtras(bundle);
        startActivityForResult(warningIntent, Constants.SPEAKER_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SPEAKER_CODE) {
            if (resultCode == RESULT_OK) {
                speakAndCheckNumber();
            } else {
                Message.toastMessage(getApplicationContext(),
                        "Please increase the Media volume.", "");
            }
        }
    }

    /**
     * This is the method to play the number.
     */
    private void speakAndCheckNumber() {
        generatedNumber = generateRandomNumber();
        textToSpeech.speak(String.valueOf(generatedNumber), TextToSpeech.QUEUE_FLUSH, null);
    }

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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
