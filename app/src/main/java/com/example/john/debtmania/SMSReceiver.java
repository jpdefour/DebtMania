package com.example.john.debtmania;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.*;

public class SMSReceiver extends BroadcastReceiver {
    static IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    Main main = null;
    SMSReceiver(Main _main)
    {
        main = _main;
    }
    String name, description,type = null;
    Double amount = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        final Object[] pdusObj = (Object[]) bundle.get("pdus");
        SmsMessage message = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
        String msgString = message.getDisplayMessageBody();
        String phoneNumber = message.getDisplayOriginatingAddress();

        //Messages format:
        //"OWE:XX.XX:description:NAME"
        //"COLLECTED:XX.XX:description:NAME"


        StringTokenizer tokens = new StringTokenizer(msgString, ":");
        type = tokens.nextToken();
        amount = Double.valueOf(tokens.nextToken());
        description = tokens.nextToken();
        name = tokens.nextToken();


        if(type.equals("OWE")) {
            Toast toast = Toast.makeText(context, "$" + amount + " owed for " + description + " to " + name, Toast.LENGTH_LONG);
            toast.show();

            main.moneyOwed(name, description, amount);
        }
        else if(type.equals("COLLECTED")) {
            Toast toast = Toast.makeText(context, "$" + amount + " collected for " + description + " to " + name, Toast.LENGTH_LONG);
            toast.show();

            main.debtCollected(name, description, amount);
        }
    }
}