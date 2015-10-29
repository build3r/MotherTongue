package com.builders.mothertongue;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by prabhasatya on 29/10/15.
 */
public class TranslateInterface {
  private static final String LOG_TAG = TranslateInterface.class.getSimpleName();
  Map<String,String> langMap = new TreeMap<String,String>();

  private void createMap() {
    langMap.put("Arabic", "ar");
    langMap.put("French", "fr");
    langMap.put("Bengali", "bn");
    langMap.put("English", "en");
    langMap.put("Dutch", "da");
    langMap.put("French", "fr");
    langMap.put("Hindi", "hi");
    langMap.put("Italian", "it");
    langMap.put("German", "de");
    langMap.put("Gujarati", "gu");
    langMap.put("Kannada", "kn");
    langMap.put("Russian", "ru");
    langMap.put("Tamil", "ta");
    langMap.put("Telugu", "te");
    langMap.put("Urdu", "ur");
    langMap.put("Spanish", "es");

  }
  public String translate(String text, String Lang) throws Exception{
    String translatedText="hello";
    String base = "https://www.googleapis.com/language/translate/v2?key=AIzaSyCxALdoLCRd1MlWwEHzxuTK0tPBZ6_0SNs&q=";
    createMap();
    base = base + URLEncoder.encode(text, "UTF-8")+"&target="+langMap.get(Lang);
    String responseString="default";
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

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
          Log.d(LOG_TAG,responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        Log.d(LOG_TAG,response.body().string());
      }
    });

    return translatedText;
  }


}
