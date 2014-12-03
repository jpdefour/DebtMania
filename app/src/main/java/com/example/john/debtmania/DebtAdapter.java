package com.example.john.debtmania;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(int i, View v, ViewGroup viewGroup) {
            if(v == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                v = inflater.inflate(R.layout.list_debt, null);
            }

            TextView person = (TextView) v.findViewById(R.id.textPerson);
            TextView description = (TextView) v.findViewById(R.id.textDescription);
            TextView date = (TextView) v.findViewById(R.id.textDate);
            TextView money = (TextView) v.findViewById(R.id.textMoney);
            ImageButton delete = (ImageButton) v.findViewById(R.id.deleteButton);

            delete.setVisibility(View.GONE);

            Debt debt = debts.get(i);

            person.setText(debt.getName());
            description.setText(debt.getDescription());
            date.setText(debt.getDate());
            money.setText(String.format( "$%.2f", debt.getAmount()));

            if(debt.getAmount() > 0){
                v.setBackgroundColor(0x5F3BEA2A);
                delete.setVisibility(View.VISIBLE);
            }

            delete.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //TODO Have to pop up AlertDialog box and ask if the user wants to delete.
                    Toast toast = Toast.makeText(view.getContext(), "DELETE", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
            });


            return v;
        }
    }

