package com.dmitryvim.application;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class AccountNotFoundException extends RuntimeException {
    AccountNotFoundException(int accountId) {
        super("Unable to find account with id " + accountId);
    }
}
