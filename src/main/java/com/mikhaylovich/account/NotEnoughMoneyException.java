package com.mikhaylovich.account;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(Account account) {
        super("Account " + account.getId() + " has not enough money.");
    }
}
