package com.builders.mothertongue.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.builders.mothertongue.Constants.Strings;
import com.builders.mothertongue.R;
import com.builders.mothertongue.Utility.SharedPreferencesUtility;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapPopOutMiniApp;


public class MainActivity extends Activity
{

    public static long miniAppId = -1;
    Spinner input;
    Spinner output;
    AdapterView.OnItemSelectedListener inputItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
        {
            String userSelection = parent.getItemAtPosition(position).toString();
            SharedPreferencesUtility.setSourceLangauge(MainActivity.this, userSelection);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {
            SharedPreferencesUtility.setSourceLangauge(MainActivity.this, Strings.HINDI);
        }
    };

    AdapterView.OnItemSelectedListener outputItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
        {
            String userSelection = parent.getItemAtPosition(position).toString();
            SharedPreferencesUtility.setTargetLangauge(MainActivity.this, userSelection);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {
            SharedPreferencesUtility.setTargetLangauge(MainActivity.this, Strings.ENGLISH);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
        TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(MainActivity.this, intent);
        miniApp.contentTitle = "Dragoman";
        miniApp.notificationText = "Hello! I'm Dragoman";
        miniApp.bubbleBackgroundColor = 0x78FFFFFF;
        Tooleap tooleap = Tooleap.getInstance(MainActivity.this);
        tooleap.removeAllMiniApps();
        miniAppId = tooleap.addMiniApp(miniApp);

        input = (Spinner) findViewById(R.id.input);

        ArrayAdapter<CharSequence> inputAdapter = ArrayAdapter.createFromResource(this,
                R.array.input_lang_array, R.layout.textview);
        inputAdapter.setDropDownViewResource(R.layout.textview);
        input.setAdapter(inputAdapter);
        String input_lang = SharedPreferencesUtility.getSourceLanguage(this);
        input.setSelection(getInputPosition(input_lang));
        input.setOnItemSelectedListener(inputItemSelectedListener);
        output = (Spinner) findViewById(R.id.output);
        ArrayAdapter<CharSequence> outputAdapter = ArrayAdapter.createFromResource(this,
                R.array.output_lang_array, R.layout.textview);
        outputAdapter.setDropDownViewResource(R.layout.textview);
        String output_lang = SharedPreferencesUtility.getSourceLanguage(this);
        input.setSelection(getOutputPosition(output_lang));
        output.setOnItemSelectedListener(outputItemSelectedListener);
        output.setAdapter(outputAdapter);
    }

    private int getInputPosition(String input_lang)
    {

        String[] list = getResources().getStringArray(R.array.output_lang_array);
        int len = list.length;
        Log.d("SharedPref", "input_lang = " + input_lang);
        for (int i = 0; i < len; i++)
        {
            if (list[i].equals(input_lang))
            {
                Log.d("SharedPref", "Position = " + i);
                return i;
            }
        }
        return 0;
    }

    private int getOutputPosition(String input_lang)
    {

        String[] list = getResources().getStringArray(R.array.input_lang_array);
        int len = list.length;
        Log.d("SharedPref", "input_lang = " + input_lang);
        for (int i = 0; i < len; i++)
        {
            if (list[i].equals(input_lang))
            {
                Log.d("SharedPref", "Position = " + i);
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
