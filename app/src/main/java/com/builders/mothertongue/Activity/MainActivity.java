package com.builders.mothertongue.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.builders.mothertongue.Constants.Langauge;
import com.builders.mothertongue.R;
import com.builders.mothertongue.Utility.SharedPreferencesUtility;
import com.builders.mothertongue.listeners.ResultCallBack;
import com.builders.mothertongue.interfaces.TranslateInterface;
import com.builders.mothertongue.interfaces.TransliterateInterface;

import com.builders.mothertongue.Events.TranslateOutput;
import de.greenrobot.event.EventBus;


public class MainActivity extends Activity{

  EditText input;
  TextView output;
  Button translate;
  ResultCallBack resultCallBack = new ResultCallBack() {
    @Override
    public void onResultCallBack(String resultText) {
      output.setText(resultText);
      TranslateInterface translateInterface = new TranslateInterface();
      try {
        resultText = resultText.trim();
        String targetLang = Langauge.targetMap.get(SharedPreferencesUtility.getTargetLangauge(MainActivity.this));
        translateInterface.translate(resultText, targetLang);
      } catch (Exception e) {
        e.printStackTrace();
      }
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
    input = (EditText) findViewById(R.id.input);
    output = (TextView) findViewById(R.id.output);
    translate = (Button) findViewById(R.id.translate);
    translate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int lang = Langauge.sourceMap.get(SharedPreferencesUtility.getSourceLanguage(MainActivity.this));
        int domain = 0;
        transliterateInterface.getTrasliterate(MainActivity.this,
            input.getText().toString().split(" "), domain, lang, lang, resultCallBack);
      }
    });
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

  @Override
  public void onStart(){
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop(){
    EventBus.getDefault().unregister(this);
    super.onStop();
  }

  public void onEventMainThread(TranslateOutput translateOutput){
    output.setText(translateOutput.getTranslatedString());
  }
}
