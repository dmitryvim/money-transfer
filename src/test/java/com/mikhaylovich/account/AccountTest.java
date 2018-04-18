package com.mikhaylovich.account;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class AccountTest {

    @Test
    public void shouldCreateAccountWithZeroMoney() {
        // given
        Account account = new Account(1);

        // expect
        assertEquals("should be no money", 0, account.getMoney());
    }

    @Test
    public void shouldPutMoneyToAccount() {
        // given
        int amount = 12;
        Account account = new Account(1);

        // when
        account.put(amount);

        // then
        assertEquals("should put money", amount, account.getMoney());
    }

    @Test
    public void shouldCheckAmountAvailability() {
        // given
        int putAmount = 10;
        int availableAmount = 5;
        int unavailableAmount = 15;
        Account account = new Account(1);
        account.put(putAmount);

        // expect
        assertFalse("unavailable money", account.available(unavailableAmount));
        assertTrue("available money", account.available(availableAmount));
    }

    @Test
    public void shouldTakeMoneyFromAccount() {
        // given
        int putAmount = 12;
        int takeAmount = 10;
        Account account = new Account(1);
        account.put(putAmount);

        // when
        account.take(takeAmount);

        // then
        assertEquals("should take money", putAmount - takeAmount, account.getMoney());
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowExceptionOnNotEnoughMoney() {
        // given
        int putAmount = 12;
        int takeAmount = 100;
        Account account = new Account(1);
        account.put(putAmount);

        // when
        account.take(takeAmount);
    }
}
