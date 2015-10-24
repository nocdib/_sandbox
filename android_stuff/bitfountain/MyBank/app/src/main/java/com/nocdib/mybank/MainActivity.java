package com.nocdib.mybank;

import android.app.Activity;
//import android.support.v7.app.ActionBarActivity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends Activity {

    EditText    amount;
    Button      withdrawButton,
                depositButton;
    TextView    accountBalance;
    BankAccount bankAccount;

    final NumberFormat FMT = NumberFormat.getCurrencyInstance(Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = (EditText) findViewById(R.id.amount);
        withdrawButton = (Button) findViewById(R.id.withdraw_button);
        depositButton = (Button) findViewById(R.id.deposit_button);
        accountBalance = (TextView) findViewById(R.id.balance);

        bankAccount = new CheckingAccount(0);

        Log.d("Account Balance is:", FMT.format(bankAccount.getBalance()));

        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankAccount.deposit(Double.parseDouble(amount.getText().toString()));
                accountBalance.setText(FMT.format(bankAccount.getBalance()));
            }
        });

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankAccount.withdraw(Double.parseDouble(amount.getText().toString()));
                accountBalance.setText(FMT.format(bankAccount.getBalance()));
            }
        });

        accountBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bankAccount.getBalance() < 0)
                    accountBalance.setTextColor(Color.RED);
                else
                    accountBalance.setTextColor(Color.GREEN);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
