package com.mikhaylovich.account;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class Account {

    private final int id;

    /*
        In order to keep code simple I preferred to model money as just integer value.
        In production way it's better to follow java overcomplicated objects and model enum Currency and class Money
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
