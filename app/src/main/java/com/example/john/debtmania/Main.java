package com.example.john.debtmania;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main extends Activity {
    Button btCreate = null;
    DebtAdapter adapterUserOwes = null;
    DebtAdapter adapterOwesUser = null;
    String username = null;
    ListView listUserOwes = null;
    ListView listOwedToUser = null;
    ArrayList<Debt> debtsUserOwes = new ArrayList<Debt>();
    ArrayList<Debt> debtsOwedUser = new ArrayList<Debt>();
    ORMDatabaseHelper dbHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        username = i.getStringExtra("username");

        listUserOwes = (ListView) findViewById(R.id.listViewYouOwe);
        listOwedToUser = (ListView) findViewById(R.id.listViewOweYou);

        adapterUserOwes = new DebtAdapter(this, debtsUserOwes, this);
        adapterOwesUser = new DebtAdapter(this, debtsOwedUser, this);

        btCreate = (Button) findViewById(R.id.buttonCreate);

        listUserOwes.setAdapter(adapterUserOwes);
        listOwedToUser.setAdapter(adapterOwesUser);

        dbHelper = new ORMDatabaseHelper(this);

        //Register the SMS Receiver
        SMSReceiver receiver = new SMSReceiver(this);
        registerReceiver(receiver, SMSReceiver.filter);

        //send SMS message to notify that money is owed, or money collected
        //sendMoneyOwed("5556","Brandon","things",55.99);
        //sendDebtCollected("5556","Brandon","things",55.99);

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AmountOweActivity.class);
                i.putExtra("username", username);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                view.getContext().startActivity(i);
            }
        });
        UpdateListView();
    }




    private void UpdateListView() {

        //QueryBuilder<Debt, Integer> queryBuilder = dbHelper.getAmountDao().queryBuilder();
        try {
            /*queryBuilder
                    .where()
                    .gt("amount", 0);
            PreparedQuery<Debt> preparedQuery = queryBuilder.prepare();
            List<Debt> debtList = dbHelper.getAmountDao().query(preparedQuery);
            */

            List<Debt> debtList = dbHelper.getAmountDao().queryForAll();
            for(Debt d : debtList)
            {
                if(d.getAmount() < 0) {
                    debtsUserOwes.add(d);
                }
                else if(d.getAmount() > 0){
                    debtsOwedUser.add(d);
                }
            }
            adapterUserOwes.notifyDataSetChanged();
            adapterOwesUser.notifyDataSetChanged();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        debtsOwedUser.clear();
        debtsUserOwes.clear();
        adapterUserOwes.notifyDataSetChanged();
        adapterOwesUser.notifyDataSetChanged();
        UpdateListView();

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
    public void moneyOwed(String name, String description, float amount, String phoneNumber) {
        //do something
        try
        {
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
            String currentDateandTime = date.format(new Date());
            // ORM Style mapping
            Debt dbamount = new Debt();
            dbamount.setName(name);
            dbamount.setDescription(description);
            dbamount.setDate(currentDateandTime);
            dbamount.setAmount(-1 * amount);

            try {
                dbHelper.getAmountDao().create(dbamount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
        }

        Toast toast = Toast.makeText(this, "$" + amount + " owed for " + description + " to " + name, Toast.LENGTH_LONG);
        toast.show();

        debtsOwedUser.clear();
        debtsUserOwes.clear();
        adapterUserOwes.notifyDataSetChanged();
        adapterOwesUser.notifyDataSetChanged();
        UpdateListView();

    }
    //this is called when user receives notification that they no longer owe money
    public void debtCollected(String name, String description, float amount, String phoneNumber) {
        //do something
        debtsOwedUser.clear();
        debtsUserOwes.clear();
        adapterUserOwes.notifyDataSetChanged();
        adapterOwesUser.notifyDataSetChanged();
        UpdateListView();
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
