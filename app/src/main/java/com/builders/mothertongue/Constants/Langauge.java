package com.builders.mothertongue.Constants;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by prabhasatya on 29/10/15.
 */
public class Langauge {

  public static final Map<String,String> targetMap;
  public static final Map<String,Integer> sourceMap;

  static{
    targetMap = new TreeMap<String,String>();
    targetMap.put(Strings.ENGLISH, "en");
    targetMap.put(Strings.HINDI, "hi");
    targetMap.put(Strings.BENGALI, "bn");
    targetMap.put(Strings.GUJARATI, "gu");
    targetMap.put(Strings.KANNADA, "kn");
    targetMap.put(Strings.TAMIL, "ta");
    targetMap.put(Strings.TELUGU, "te");
    targetMap.put(Strings.URDU, "ur");
    targetMap.put(Strings.SPANISH, "es");
    targetMap.put(Strings.ARABIC, "ar");
    targetMap.put(Strings.FRENCH, "fr");
    targetMap.put(Strings.DUTCH, "da");
    targetMap.put(Strings.ITALIAN, "it");
    targetMap.put(Strings.GERMAN, "de");
    targetMap.put(Strings.RUSSIAN, "ru");

    sourceMap = new TreeMap<String,Integer>();

    sourceMap.put(Strings.HINDI,0);
    sourceMap.put(Strings.GUJARATI,1);
    sourceMap.put(Strings.PUNJABI,2);
    sourceMap.put(Strings.MALAYALAM,3);
    sourceMap.put(Strings.TAMIL,4);
    sourceMap.put(Strings.TELUGU,5);
    sourceMap.put(Strings.KANNADA,6);
    sourceMap.put(Strings.BENGALI,7);
    sourceMap.put(Strings.ASSAMESE,8);
    sourceMap.put(Strings.ENGLISH,20);
    sourceMap.put("LANG_CONTEXT_DEFAULT",0);
  }

}
