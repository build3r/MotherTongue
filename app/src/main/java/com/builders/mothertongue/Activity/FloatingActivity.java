package com.builders.mothertongue.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.builders.mothertongue.Constants.Strings;
import com.builders.mothertongue.R;
import com.builders.mothertongue.Utility.SharedPreferencesUtility;
import com.tooleap.sdk.TooleapActivities;

public class FloatingActivity extends TooleapActivities.Activity
{
    Spinner input;
    Spinner output;
    AdapterView.OnItemSelectedListener inputItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
        {
            String userSelection = parent.getItemAtPosition(position).toString();
            SharedPreferencesUtility.setSourceLangauge(FloatingActivity.this, userSelection);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {
            SharedPreferencesUtility.setSourceLangauge(FloatingActivity.this, Strings.HINDI);
        }
    };

    AdapterView.OnItemSelectedListener outputItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l)
        {
            String userSelection = parent.getItemAtPosition(position).toString();
            SharedPreferencesUtility.setTargetLangauge(FloatingActivity.this, userSelection);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {
            SharedPreferencesUtility.setTargetLangauge(FloatingActivity.this, Strings.ENGLISH);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating);
        input = (Spinner) findViewById(R.id.input);

        ArrayAdapter<CharSequence> inputAdapter = ArrayAdapter.createFromResource(this,
                R.array.input_lang_array,  android.R.layout.simple_spinner_item);
        inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input.setAdapter(inputAdapter);
        String input_lang = SharedPreferencesUtility.getSourceLanguage(this);
        input.setSelection(getInputPosition(input_lang));
        input.setOnItemSelectedListener(inputItemSelectedListener);
        output = (Spinner) findViewById(R.id.output);
        ArrayAdapter<CharSequence> outputAdapter = ArrayAdapter.createFromResource(this,
                R.array.output_lang_array, android.R.layout.simple_spinner_item);
        outputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
}
