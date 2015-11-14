package com.builders.mothertongue.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;

import com.builders.mothertongue.Constants.Strings;
import com.builders.mothertongue.R;
import com.builders.mothertongue.Utility.SharedPreferencesUtility;
import com.tooleap.sdk.TooleapActivities;

public class FloatingActivity extends TooleapActivities.Activity
{
    NumberPicker input;
    NumberPicker output;
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
        input = (NumberPicker) findViewById(R.id.input);
        final String[] inputArray = getResources().getStringArray(R.array.input_lang_array);

        input.setMinValue(0);
        input.setMaxValue(inputArray.length - 1);
        input.setDisplayedValues(inputArray);
        String input_lang = SharedPreferencesUtility.getSourceLanguage(this);
        input.setValue(getInputPosition(input_lang));
        input.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                String userSelection = inputArray[newValue];
                SharedPreferencesUtility.setSourceLangauge(FloatingActivity.this,userSelection);
            }
        });
        final String[] outputArray = getResources().getStringArray(R.array.output_lang_array);
        output = (NumberPicker) findViewById(R.id.output);
        output.setMinValue(0);
        output.clearFocus();
        output.setMaxValue(outputArray.length - 1);

        output.setDisplayedValues(outputArray);
        String output_lang = SharedPreferencesUtility.getSourceLanguage(this);
        input.setValue(getOutputPosition(output_lang));
        output.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue)
            {
                String userSelection = outputArray[newValue];
                SharedPreferencesUtility.setTargetLangauge(FloatingActivity.this, userSelection);
            }
        });

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
