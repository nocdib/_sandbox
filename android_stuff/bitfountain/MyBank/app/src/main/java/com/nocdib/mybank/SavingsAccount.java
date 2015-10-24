package com.nocdib.mybank;

import android.util.Log;

/**
 * Created by nocdib on 10/24/15.
 */
public class SavingsAccount extends BankAccount {

    protected final String TAG = "Savings Account";

    public SavingsAccount(double amount){
        super(amount);
        Log.d(TAG, "Initializing Savings Account");
    }

    public SavingsAccount(){
        this(0.0);
    }

    @Override
    public void withdraw(Double amount) {
        // Enforce a 3 withdrawal limit with savings accounts
        if(countWithdrawals() >= 3 || mBalance - amount < 0) {
            return;
        }
        super.withdraw(amount);
    }
}
