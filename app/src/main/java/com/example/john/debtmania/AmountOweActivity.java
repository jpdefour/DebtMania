package com.example.john.debtmania;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AmountOweActivity extends Activity {
    EditText etName = null;
    EditText etDescription = null;
    EditText etAmount = null;
    EditText etPhnum = null;
    TextView etError = null;
    Button btEnter = null;
    ListView listView = null;
    ArrayAdapter<String> adapter = null;
    ArrayList<String> data = new ArrayList<String>();

    ORMDatabaseHelper dbOrmAmount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_owe);
        etName = (EditText)findViewById(R.id.etName);
        etDescription = (EditText)findViewById(R.id.etDescription);
        etAmount = (EditText)findViewById(R.id.etAmount);
        etError = (TextView)findViewById(R.id.texteditError);
        btEnter = (Button)findViewById(R.id.btEnter);
        etPhnum = (EditText)findViewById(R.id.editTextphnum);
        dbOrmAmount = new ORMDatabaseHelper(this);

        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        UpdateListView();

        etError.setText("");       // Clear erroinfo text
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etError.setText("");       // Clear erroinfo text
                try
                {
                    String name = etName.getText().toString();
                    String description = etDescription.getText().toString();
                    Float  amount = Float.parseFloat(etAmount.getText().toString());
                    SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
                    String currentDateandTime = date.format(new Date());
                    String phnumber = etPhnum.getText().toString();
                    // ORM Style mapping
                    Debt dbamount = new Debt();
                    dbamount.setName(name);
                    dbamount.setDescription(description);
                    dbamount.setDate(currentDateandTime);
                    dbamount.setAmount(amount);

                    try {
                        dbOrmAmount.getAmountDao().create(dbamount);
                        //sendMoneyOwed(phnumber, name, description, amount);  // Send SMS to person
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    UpdateListView();
                    //TODO Notify the data sets that the data has changed.
                }
                catch (Exception e)
                {
                    etError.setText("Please Enter Correct Info..");
                }
            }
        });
    }

    //this sends an SMS to the given number, letting the person at that number know they owe money to userName
    //with given amount and description
    public void sendMoneyOwed(String number, String userName, String description, double amount) {
        String msg = "OWE:"+amount+":"+description+":"+userName;
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(number, null, msg, null, null);
    }

    private void UpdateListView() {
        adapter.clear();
        try {
            List<Debt> dbamounts = dbOrmAmount.getAmountDao().queryForAll();
            for(Debt dba : dbamounts)
            {
                if(dba.getAmount() < 0) {
                    adapter.add(dba.toString());
                }
            }
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_amount_owe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
