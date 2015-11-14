package com.builders.mothertongue.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import android.widget.TextView;
import android.widget.Toast;

import com.builders.mothertongue.Activity.MainActivity;
import com.builders.mothertongue.Constants.Langauge;
import com.builders.mothertongue.Events.TranslateOutput;
import com.builders.mothertongue.Utility.SharedPreferencesUtility;
import com.builders.mothertongue.interfaces.TranslateInterface;
import com.builders.mothertongue.interfaces.TransliterateInterface;
import com.builders.mothertongue.listeners.ResultCallBack;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapMiniApp;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Shabaz on 29-Oct-15.
 */
public class MyAccessibilityService extends AccessibilityService
{
    final static String  tag= MyAccessibilityService.class.getSimpleName();
    TransliterateInterface transliterateInterface=null;
    boolean connectedToReverie = false;
    ResultCallBack resultCallBack = new ResultCallBack() {
        @Override
        public void onResultCallBack(String resultText) {
            TranslateInterface translateInterface = new TranslateInterface();
            try {
                resultText = resultText.trim();
                Log.d(tag,"Result Call Back = "+resultText);

                String targetLang = Langauge.targetMap.get(SharedPreferencesUtility.getTargetLangauge(MyAccessibilityService.this));
                Log.d(tag,"Calling Google Translate with = "+targetLang);
                translateInterface.translate(resultText, targetLang);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public void onEventMainThread(TranslateOutput translateOutput){
        Tooleap tooleap = Tooleap.getInstance(this);
        Log.d(tag,"Google's callBack  = "+translateOutput.getTranslatedString());
        TooleapMiniApp miniApp = tooleap.getMiniApp(MainActivity.miniAppId);
        miniApp.notificationText = translateOutput.getTranslatedString();
        tooleap.updateMiniAppAndNotify(MainActivity.miniAppId, miniApp);
    }
    @Override
    public void onInterrupt() {
    }
    private String getEventType(AccessibilityEvent event)
    {
        switch (event.getEventType())
        {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                return "TYPE_NOTIFICATION_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                return "TYPE_VIEW_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                return "TYPE_VIEW_FOCUSED";
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                return "TYPE_VIEW_LONG_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                return "TYPE_VIEW_SELECTED";
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                return "TYPE_WINDOW_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                return "TYPE_VIEW_TEXT_CHANGED";
        }
        return "default";
    }
    ArrayList<String> text = new ArrayList<>();
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        //eventText = eventText + event.getContentDescription();

        text = new ArrayList<>();

        //Log.d(tag,"Refresh "+event.getSource().refresh());
        getTextFromNode(event.getSource());
        Log.d(tag, "Length = "+text.size());
        String message= "";
        if(text.size()==3)
        {
            message = text.get(1);
        }
        else if(text.size()==2)
        {
            message = text.get(0);
        } else if(text.size()==4)
        {
            message = text.get(2);
        }
        if(!message.equals("") && MainActivity.miniAppId!=-1)
        {

            int lang = Langauge.
                    sourceMap.
                    get(SharedPreferencesUtility.getSourceLanguage(this));
            int domain = 0;
            Log.d(tag,"Message ok: "+ message);
            if(connectedToReverie==false)
            {
                connectedToReverie = makeConnectionToReverie();
            }
            if(transliterateInterface!=null)
            {
                Log.d(tag, "Transliterating");
                String[] inarray = message.split("\\s+");
                transliterateInterface.getTrasliterate(this, new String[]{message}, domain, lang, lang, resultCallBack);
            }
            else
            {
                Toast.makeText(this,"Connection to reverie Failed",Toast.LENGTH_SHORT).show();
            }


        }
        String ussd_details = String
                .format("onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                        getEventType(event), event.getClassName(),
                        event.getPackageName(), event.getEventTime(), text.toString());
        AccessibilityRecord m;
        Log.d(tag, ussd_details);
        Log.d(tag , text.toString());
        Log.d(tag , "Message " + message);

        final int eventType = event.getEventType();


        //}
        // Do something nifty with this text, like speak the composed string
        // back to the user.
        //speakToUser(eventText);
        //...
    }
    public void getTextFromNode(AccessibilityNodeInfo accessibilityNodeInfo)
    {
        StringBuilder sb = new StringBuilder();
        if (accessibilityNodeInfo == null)
        {
            ////V10Log.d("TEST", "accessibilityNodeInfo is null");
            return ;
        }

        int j = accessibilityNodeInfo.getChildCount();
        Log.d(tag,"Number OObjects = "+j);
        //if(j==3)
        ////V10Log.d("TEST", "number of children = " + j);
        for (int i = 0; i < j; i++)
        {

            AccessibilityNodeInfo ac = accessibilityNodeInfo.getChild(i);

            if (ac == null)
            {
                Log.d(tag,"ac is null");
                continue;
            }
            if (ac.getChildCount() > 0)
            {
                Log.d(tag, "More than one subchild " + ac.getChildCount());
                Log.d(tag, "Text  " + ac.getText());
                getTextFromNode(ac);
            }
            Log.d(tag,ac.getClassName()+"");
            Log.d(tag, "Text  " + ac.getText());
            if (ac.getClassName().equals(TextView.class.getName()))
            {

                text.add(ac.getText().toString());
                ////V10Log.d("TEST", "Number:" + i + "   " + sb);
            }

        }
    }
    boolean makeConnectionToReverie()
    {
        transliterateInterface = new TransliterateInterface();
        String errorString = transliterateInterface.connectToReverie(this);
        if(!errorString.equals("")){
            Toast.makeText(this, errorString, Toast.LENGTH_LONG).show();
            return false;
        }else{
            Toast.makeText(this,"Connected To Reverie",Toast.LENGTH_LONG).show();
            return true;
        }
    }
    @Override
    public void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();

        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.packageNames = null;
        //info.packageNames = new String[]{"com.whatsapp","com.bsb.hike"};
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_LONG_CLICKED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        connectedToReverie = makeConnectionToReverie();
        EventBus.getDefault().register(this);
        // Set the type of events that this service wants to listen to.  Others
        // won't be passed to this service.
       /* info.eventTypes = AccessibilityEvent.TYPE_VIEW_LONG_CLICKED|AccessibilityEvent.TYPE_WINDOWS_CHANGED|AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.packageNames = new String[]{"com.whatsapp"};

        // If you only want this service to work with specific applications, set their
        // package names here.  Otherwise, when the service is activated, it will listen
        // to events from all applications.
        //info.packageNames = new String[]
        //        {"com.example.android.myFirstApp", "com.example.android.mySecondApp"};

        // Set the type of feedback your service will provide.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
*/
        // Default services are invoked only if no package-specific ones are present
        // for the type of AccessibilityEvent generated.  This service *is*
        // application-specific, so the flag isn't necessary.  If this was a
        // general-purpose service, it would be worth considering setting the
        // DEFAULT flag.

        // info.flags = AccessibilityServiceInfo.DEFAULT;

//        info.notificationTimeout = 100;
//
//        this.setServiceInfo(info);

    }
}
