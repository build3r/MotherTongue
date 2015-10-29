package com.builders.mothertongue.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.builders.mothertongue.Constants.Strings;
import com.builders.mothertongue.R;
import com.builders.mothertongue.Utility.SharedPreferencesUtility;
import com.builders.mothertongue.interfaces.TransliterateInterface;


public class MainActivity extends Activity{

  Spinner input;
  Spinner output;
  Button translate;

  AdapterView.OnItemSelectedListener inputItemSelectedListener = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
      String userSelection = parent.getItemAtPosition(position).toString();
      SharedPreferencesUtility.setSourceLangauge(MainActivity.this,userSelection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
      SharedPreferencesUtility.setSourceLangauge(MainActivity.this, Strings.HINDI);
    }
  };

  AdapterView.OnItemSelectedListener outputItemSelectedListener = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
      String userSelection = parent.getItemAtPosition(position).toString();
      SharedPreferencesUtility.setTargetLangauge(MainActivity.this,userSelection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
      SharedPreferencesUtility.setTargetLangauge(MainActivity.this,Strings.ENGLISH);
    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final TransliterateInterface transliterateInterface = new TransliterateInterface();
    String errorString = transliterateInterface.connectToReverie(this);
    if(!errorString.equals("")){
      Toast.makeText(this,errorString,Toast.LENGTH_LONG).show();
    }else{
      Toast.makeText(this,"Connected To Reverie",Toast.LENGTH_LONG).show();
    }
    setContentView(R.layout.activity_main);
    input = (Spinner) findViewById(R.id.input);
    ArrayAdapter<CharSequence> inputAdapter = ArrayAdapter.createFromResource(this,
        R.array.input_lang_array, android.R.layout.simple_spinner_item);
    inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    input.setAdapter(inputAdapter);
    input.setOnItemSelectedListener(inputItemSelectedListener);
    output = (Spinner) findViewById(R.id.output);
    ArrayAdapter<CharSequence> outputAdapter = ArrayAdapter.createFromResource(this,
        R.array.output_lang_array, android.R.layout.simple_spinner_item);
    outputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    output.setOnItemSelectedListener(outputItemSelectedListener);
    output.setAdapter(outputAdapter);
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
