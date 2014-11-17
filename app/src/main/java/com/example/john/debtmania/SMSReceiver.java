package com.example.john.debtmania;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
    static IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    Main main = null;
    SMSReceiver(Main _main)
    {
        main = _main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        final Object[] pdusObj = (Object[]) bundle.get("pdus");
        SmsMessage message = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
        String msgString = message.getDisplayMessageBody();
        String phoneNumber = message.getDisplayOriginatingAddress();



        if(msgString.contains("OWE")) {
            //call something in main to add some info to database
        }
        else if(msgString.contains("COLLECTED")) {
            //call something in main to remove some info from the database
        }
    }
}