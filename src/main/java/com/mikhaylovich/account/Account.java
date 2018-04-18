package com.mikhaylovich.account;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class Account {

    private final int id;

    /*
    In order to follow rule #1 (keep code as simple as possible)
    I have ignored ideas of class Currency and Money to make code domain specific
     */
    private int money;

    public Account(int id) {
        this.id = id;
        this.money = 0;
    }

    public void put(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Unable to put negative amount of money.");
        }
        this.money += amount;
    }

    public void take(int amount) {
        if (!available(amount)) {
            throw new NotEnoughMoneyException(this);
        }
        this.money -= amount;
    }

    public boolean available(int amount) {
        return amount <= this.money;
    }

    public int getId() {
        return this.id;
    }

    public int getMoney() {
        return this.money;
    }
}
