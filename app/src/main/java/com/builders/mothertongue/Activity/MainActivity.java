package com.builders.mothertongue.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.builders.mothertongue.Events.TranslateOutput;
import com.builders.mothertongue.R;
import com.builders.mothertongue.interfaces.TranslateInterface;
import com.builders.mothertongue.interfaces.TransliterateInterface;
import com.builders.mothertongue.listeners.ResultCallBack;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapPopOutMiniApp;

import de.greenrobot.event.EventBus;


public class MainActivity extends Activity{

  public static long miniAppId = -1;
  EditText input;
  TextView output;
  Button translate;


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
    translate.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            transliterateInterface.getTrasliterate(MainActivity.this,
                    input.getText().toString().split(" "), 0,
                    5, 5, resultCallBack);
        }
    });
      Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
      TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(MainActivity.this, intent);
      miniApp.contentTitle = "My First Mini App";
      miniApp.notificationText = "Hello! I'm the Tooleap bubble";
      miniApp.bubbleBackgroundColor = 0x78FFFFFF;
      Tooleap tooleap = Tooleap.getInstance(MainActivity.this);
      tooleap.removeAllMiniApps();
      miniAppId = tooleap.addMiniApp(miniApp);
  }
    ResultCallBack resultCallBack = new ResultCallBack() {
        @Override
        public void onResultCallBack(String resultText) {
            output.setText(resultText);
            TranslateInterface translateInterface = new TranslateInterface();
            try {
                resultText = resultText.trim();
                translateInterface.translate(resultText,"Kannada");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
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
  public void onStop()
  {
      EventBus.getDefault().unregister(this);
      super.onStop();
  }

  public void onEvent(TranslateOutput translateOutput){
    output.setText(translateOutput.getTranslatedString());
  }
}
