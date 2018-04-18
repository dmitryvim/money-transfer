package com.mikhaylovich.application;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(int accountId) {
        super("Unable to find account with id " + accountId);
    }
}
