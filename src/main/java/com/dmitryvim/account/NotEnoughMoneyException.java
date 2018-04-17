package com.dmitryvim.account;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class NotEnoughMoneyException extends RuntimeException {
    NotEnoughMoneyException(Account account) {
        super("There is no enough money on account " + account.getId());
    }
}
