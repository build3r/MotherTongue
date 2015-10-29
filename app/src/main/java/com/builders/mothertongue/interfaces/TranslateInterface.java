package com.builders.mothertongue.interfaces;

import android.util.Log;

import com.builders.mothertongue.Constants.Langauge;
import com.builders.mothertongue.Events.TranslateOutput;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import de.greenrobot.event.EventBus;

/**
 * Created by prabhasatya on 29/10/15.
 */
public class TranslateInterface {
  public static final String LOG_TAG = TranslateInterface.class.getSimpleName();

  public void translate(String text, String Lang) throws Exception{
    String translatedText="hello";
    String base = "https://www.googleapis.com/language/translate/v2?key=AIzaSyAv5VxIekPodp44C0__vAVIJ7vAWO9PRVE&q=";
    base = base + URLEncoder.encode(text, "UTF-8")+"&target="+ Langauge.targetMap.get(Lang);
    OkHttpClient client = new OkHttpClient();
    Request request =  new Request.Builder()
        .url(base)
        .build();
     client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Request request, IOException e) {
        Log.d(LOG_TAG,Log.getStackTraceString(e));
      }

      @Override
      public void onResponse(Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String responseString = response.body().string();
        JSONObject json = null;
        try {
          json = new JSONObject(responseString);
          json = json.getJSONObject("data");
          JSONArray translations = json.getJSONArray("translations");
          json = translations.getJSONObject(0);
          String translatedText = json.getString("translatedText");
          EventBus.getDefault().post(new TranslateOutput(translatedText));
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });

  }


}
