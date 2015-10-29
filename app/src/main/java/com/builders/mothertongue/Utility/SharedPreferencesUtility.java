package com.builders.mothertongue.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.builders.mothertongue.Constants.Strings;
import com.builders.mothertongue.Constants.UserInput;

/**
 * Created by prabhasatya on 29/10/15.
 */
public  class SharedPreferencesUtility {

  private static final String LOG_TAG = SharedPreferencesUtility.class.getSimpleName();
  static Context context;
  public static void setSourceLangauge(Context context, String srcLang){
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.clear().commit();
    editor.putString(UserInput.inputLanguage,srcLang);
    boolean commitResult = editor.commit();
    if (!commitResult) {
      Log.d(LOG_TAG,"Unable to commit source language");
    }
  }

  public static String getSourceLanguage(Context context){
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getString(UserInput.inputLanguage,Strings.HINDI);
  }

  public static void setTargetLangauge(Context context, String tgtLang){
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(UserInput.outputLanguage,tgtLang);
    boolean commitResult = editor.commit();
    if (!commitResult) {
      Log.d(LOG_TAG,"Unable to commit target language");
    }
  }

  public static String getTargetLangauge(Context context){
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return  sharedPreferences.getString(UserInput.outputLanguage, Strings.ENGLISH);
  }
}
