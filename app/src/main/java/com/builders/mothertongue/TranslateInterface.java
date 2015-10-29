package com.builders.mothertongue;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by prabhasatya on 29/10/15.
 */
public class TranslateInterface {
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
//  public String translate(String text, String Lang) throws Exception{
//    String translatedText="hello";
//    String base = "https://www.googleapis.com/language/translate/v2?key=AIzaSyCxALdoLCRd1MlWwEHzxuTK0tPBZ6_0SNs&q=";
//    base = base + URLEncoder.encode(text, "UTF-8")+"&target="+langMap.get(Lang);
//    String responseString="default";
//    OkHttpClient client = new OkHttpClient();
//    HttpResponse response = client.execute(new HttpGet(base));
//    StatusLine statusLine = response.getStatusLine();
//    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
//      ByteArrayOutputStream out = new ByteArrayOutputStream();
//      response.getEntity().writeTo(out);
//      responseString = out.toString();
//      Log.d("SHABAZ", responseString);
//      out.close();
//      JSONObject json = new JSONObject(responseString);
//      json = json.getJSONObject("data");
//      JSONArray translations = json.getJSONArray("translations");
//      json = translations.getJSONObject(0);
//      translatedText = json.getString("translatedText");
//
//    } else {
//      //Closes the connection.
//      response.getEntity().getContent().close();
//      throw new IOException(statusLine.getReasonPhrase());
//    }
//    Log.d("SHABAZ", translatedText);
//    //Toast.makeText(this, "Message " +responseString, Toast.LENGTH_LONG).show();
//
//    return translatedText;
//  }


}
