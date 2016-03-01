package com.example.texttospeakexperiment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String TAG = "info:";

    // this is heavily inspired from. https://www.youtube.com/watch?v=sU38Yhux-3g


    TextToSpeech ttsObject;
    int result;
    //Initialize edit text , "et" for edit text.
    EditText et;
    // the text that is spoken
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // saves the text that the user types into the text as type edit
        // text still not extracted happens in method doSomething(View v)
        et = (EditText) findViewById(R.id.editText);

        //  TextToSpeech(Context context, oninitListener ) // 'MainActivity.this'  is the context
        // remember context is the "assistant"
        ttsObject = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener()
        {
            // it creates this by it self..
            @Override
            public void onInit(int status) {

                if(status == TextToSpeech.SUCCESS)
                {
                    // set language:
                    // returns a value to result.
                    result = ttsObject.setLanguage(Locale.UK);
                }
                else // if its not working: prevents from crashing
                {
                    Toast.makeText(getApplicationContext(),
                            "Feature not supported in your Device",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    // when any button is clickeed this method will be executed.
    // has to be public, we need accces from the sentence in the  content_main.xml file ->
    // android:onClick="doSomething"
    public void doSomething(View v)

    {
        switch (v.getId())
        {
            // when speak is clicked
            case R.id.btnSpeak:

                Log.i(TAG, "Speak is clicked");
                // is there missing data or language?


                if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA)
                {
                    Toast.makeText(getApplicationContext(),
                            "Feature not supported in your Device",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // extract text from et , from what the user typed
                    text = et.getText().toString();
                    //text = "hi you suck";
                    ttsObject.speak(text, TextToSpeech.QUEUE_FLUSH, null );
                    Log.i(TAG,text);
                    //



                    //ttsObject.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            // when stop speak is clicked
            case R.id.btnStopSpeaking:
                Log.i(TAG,"stop is clicked");
                break;

            default:
                Log.i(TAG,"error");
                break;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
