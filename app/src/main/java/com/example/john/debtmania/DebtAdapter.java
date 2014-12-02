package com.example.john.debtmania;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 12/1/2014.
 */
public class DebtAdapter extends BaseAdapter {
    Context context = null;
    ArrayList<Debt> debts = null;

    public DebtAdapter(Context c, ArrayList<Debt> _debts) {
        context = c;
        debts = _debts;
    }

    @Override
    public int getCount() {
        return debts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null) {
            View v = inflater.inflate(R.layout.list_debt, null);
            TextView person = (TextView) v.findViewById(R.id.textPerson);
            TextView description = (TextView) v.findViewById(R.id.textDescription);
            TextView date = (TextView) v.findViewById(R.id.textDate);
            TextView money = (TextView) v.findViewById(R.id.textMoney);

            Debt amount = debts.get(i);
            person.setText(amount.getName());
            description.setText(amount.getDescription());
            date.setText(amount.getDate());
            money.setText(Float.toString(amount.getAmount()));

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO
                }
            });


            return v;
        }
        return view;
    }
}
