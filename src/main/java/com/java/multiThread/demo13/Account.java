package com.java.multiThread.demo13;

/**
 * Created by mgupta on 8/18/15.
 */
public class Account {

    private int balance = 10000;

    public static void transfer(Account a, Account b, int amount) {
        a.withdraw(amount);
        b.deposit(amount);
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

}
