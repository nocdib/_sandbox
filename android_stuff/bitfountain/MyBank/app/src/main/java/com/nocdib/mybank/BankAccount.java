package com.nocdib.mybank;

public class BankAccount {

    private double  balance;
    private static final int OVERDRAFT_FEE = 35;

    public BankAccount(double balance){
        this.balance = balance;
    }

    public BankAccount(){
        this(0);
    }

    public void withdraw(Double amount){
        balance -= amount;
        balance = (balance < 0) ? balance - OVERDRAFT_FEE : balance;
    }

    public void deposit(Double amount){
        balance += amount;
    }

    public double getBalance(){
        return balance;
    }

    public String toString(){
        return String.valueOf(getBalance());
    }
}
