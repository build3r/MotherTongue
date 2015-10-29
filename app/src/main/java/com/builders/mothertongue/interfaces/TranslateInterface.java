package com.builders.mothertongue.interfaces;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import com.builders.mothertongue.Events.TranslateOutput;
import de.greenrobot.event.EventBus;

/**
 * Created by prabhasatya on 29/10/15.
 */
public class TranslateInterface {
  private static final String LOG_TAG = TranslateInterface.class.getSimpleName();
  Map<String,String> langMap = new TreeMap<String,String>();

  private void createMap() {
    langMap.put("English", "en");
    langMap.put("Hindi", "hi");
    langMap.put("Bengali", "bn");
    langMap.put("Gujarati", "gu");
    langMap.put("Kannada", "kn");
    langMap.put("Tamil", "ta");
    langMap.put("Telugu", "te");
    langMap.put("Urdu", "ur");
    langMap.put("Spanish", "es");
    langMap.put("Arabic", "ar");
    langMap.put("French", "fr");
    langMap.put("Dutch", "da");
    langMap.put("Italian", "it");
    langMap.put("German", "de");
    langMap.put("Russian", "ru");
  }
  public void translate(String text, String Lang) throws Exception{
    String translatedText="hello";
    String base = "https://www.googleapis.com/language/translate/v2?key=AIzaSyAv5VxIekPodp44C0__vAVIJ7vAWO9PRVE&q=";
    createMap();
    base = base + URLEncoder.encode(text, "UTF-8")+"&target="+langMap.get(Lang);
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
