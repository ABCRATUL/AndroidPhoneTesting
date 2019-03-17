package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;
import java.util.logging.Handler;

public class VoiceSpeak {
    private TextToSpeech textToSpeech;

    public VoiceSpeak(Context context) {
        textToSpeech = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
    }

    /**
     * Method to speak.
     *
     * @param data: The String Data to be spoken.
     */
    public void speakVoice(String data) {
        textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
    }
}
