package com.builders.mothertongue;

import android.content.Context;

import com.reverie.lm.LM;
import com.reverie.transliteration_online.RevTransliteration_online;
import com.reverie.transliteration_online.SimpleTransliterationListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Interface to Reverie API
 */
public class TransliterateInterface {
  public String reverieSDKKey = "e5aaaacb7b15606413df7cfa0144c6324249";


  public void getTrasliterate(Context context, String inputText[], int domain, int originalLang,
                                int targetLang, final ResultCallBack resultCallBack) {

    RevTransliteration_online trans_on = new RevTransliteration_online(context);
    trans_on.setTransliterationListener(new SimpleTransliterationListener() {
      @Override
      public void onResult(HashMap map) {
        HashMap<String, String> outputMap = map;
        Set<String> keySet =outputMap.keySet();
        String arr[] = new String[keySet.size()];
        Iterator<String> iterator = keySet.iterator();
        int counter =0;
        while(iterator.hasNext())
        {
          arr[counter++] = iterator.next();
        }
        String output = "";
        for(int i=0;i<arr.length;i++){
          output = output.concat(map.get(arr[i]) + " ");
        }
        resultCallBack.onResultCallBack(output);
      }
    });
    trans_on.getTransliteratedText(inputText, domain, originalLang, targetLang);
  }

  public String connectToReverie(Context context) {
    int reverieConnectResult = new LM(context).RegisterSDK(reverieSDKKey);
    String errorString = "";
    if (reverieConnectResult == LM.REV_LM_INVALID_API_KEY) {
      errorString = "SDK Key is not valid"; //EXIT WITH SOME NOTIFICATION
//YOUR SDK IS NOT VALID. CHECK YOUR SDK_ID AND APPLICATION //PACKAGE NAME. IF DOES NOT RESOLVED
// PLEASE CONTACT SDK //PROVIDER.
    } else if (reverieConnectResult == LM.LC_NT_ERROR) {
      errorString =  "Network error"; // EXIT WITH NOTIFICATION.
// CHECK YOUR NETWORK CONNECTIVITY. IF THE INTERNET //CONNECTIVITY IS FINE, PLEASE CONTACT SDK PROVIDER.
    } else if (reverieConnectResult == LM.LC_NT_UNAVAILABLE) {
      errorString =  "Internet not available"; // NOTIFY USER TO ENABLE INTERNET CONNECTION.
    } else if (reverieConnectResult == LM.LC_UNKNOWN_ERROR) {
      errorString =  "Unknown Error"; // EXIT WITH NOTIFICATION.CONTACT SDK PROVIDER.
    }
    return errorString;
  }
}
