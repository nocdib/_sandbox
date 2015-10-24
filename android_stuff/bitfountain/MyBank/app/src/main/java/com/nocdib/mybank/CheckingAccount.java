package com.nocdib.mybank;

import android.util.Log;

/**
 * Created by nocdib on 10/24/15.
 */
public class CheckingAccount extends BankAccount {

    protected final String TAG = "Checking Account";

    public CheckingAccount(double amount){
        super(amount);
        Log.d(TAG, "Initializing Checking Account");
    }

    public CheckingAccount(){
        this(0.0);
    }

    @Override
    public void withdraw(Double amount) {
        // Apply overdraft fee when necessary
        if (mBalance - amount < 0) {
            mBalance -= OVERDRAFT_FEE;
            mTransactions.add(OVERDRAFT_FEE);
        }
        super.withdraw(amount);
    }
}
