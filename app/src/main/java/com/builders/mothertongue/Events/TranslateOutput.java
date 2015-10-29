package com.builders.mothertongue.Events;

/**
 * Created by prabhasatya on 29/10/15.
 */
public class TranslateOutput {
  public String getTranslatedString() {
    return translatedString;
  }

  String translatedString;

  public TranslateOutput(String output){
    this.translatedString = output;
  }

}
