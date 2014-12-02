package com.example.john.debtmania;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Register the SMS Receiver
        SMSReceiver receiver = new SMSReceiver(this);
        registerReceiver(receiver, SMSReceiver.filter);

        //send SMS message to notify that money is owed, or money collected
        //sendMoneyOwed("5556","Brandon","things",55.99);
        //sendDebtCollected("5556","Brandon","things",55.99);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //this is called when a user receives notification that they owe money
    public void moneyOwed(String name, String description, double amount) {
        //do something
    }
    //this is called when user receives notification that they no longer owe money
    public void debtCollected(String name, String description, double amount) {
        //do something
    }

    //this sends an SMS to the given number, letting the person at that number know they owe money to userName
    //with given amount and description
    public void sendMoneyOwed(String number, String userName, String description, double amount) {
        String msg = "OWE:"+amount+":"+description+":"+userName;
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(number, null, msg, null, null);
    }

    //sends an SMS to the given number, letting that person know they no longer owe money to userName
    //for the given amount and description
    public void sendDebtCollected(String number, String userName, String description, double amount) {
        String msg = "COLLECTED:"+amount+":"+description+":"+userName;
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(number, null, msg, null, null);
    }


}
