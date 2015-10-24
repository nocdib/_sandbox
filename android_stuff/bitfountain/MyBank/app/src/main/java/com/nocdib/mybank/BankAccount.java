package com.nocdib.mybank;

import android.util.Log;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class BankAccount {

    protected double mBalance;
    protected static final double OVERDRAFT_FEE = -30;
    protected ArrayList<Double> mTransactions;


    public BankAccount(double balance){
        this.mBalance = balance;
        mTransactions = new ArrayList<Double>();
    }

    public BankAccount(){
        this(0.0);
    }


    public void withdraw(Double amount){
        mTransactions.add(-amount);
        mBalance -= amount;
    }

    public void deposit(Double amount){
        mTransactions.add(amount);
        mBalance += amount;
    }

    public double getBalance(){
        mBalance = 0.0;
        for(int x=0; x<mTransactions.size(); x++)
            mBalance += mTransactions.get(x);
        return mBalance;
    }

    protected int countWithdrawals(){
        int count = 0;
        for (int i = 0; i < mTransactions.size(); i++) {
                count = (mTransactions.get(i) < 0) ? ++count : count;
        }
        return count;
    }

    public String toString(){
        return String.valueOf(getBalance());
    }
}
