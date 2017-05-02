package de.belau_online.patter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String text;
    EditText editText_text;
    TextToSpeech tts;
    String [] items ={"Hallo", "Ich", "Du", "Er", "Machen", "Probieren"};
    LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText_text =  (EditText) findViewById(R.id.editText_text);
        ll = (LinearLayout) findViewById(R.id.layout_LinLayout);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.GERMAN);
                }
            }
        });

        Button button = (Button) findViewById(R.id.button_say);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = editText_text.getText().toString();
                say(text);
            }
        });

        createButtons(items);
    }

    @Override
    protected void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    void showMyToast(CharSequence _text){
        Context context = getApplicationContext();
        CharSequence text = _text;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    void createButtons(String[] str){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i<(str.length); i++) {
            final Button myButton = new Button(this);
            myButton.setText(str[i]);
            myButton.setId(str[i].hashCode());
            ll.addView(myButton, lp);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v == myButton) {
                        say(myButton.getText().toString());
                    }

                }
            });
        }
    }
    void say(String text){
        showMyToast(text);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}
