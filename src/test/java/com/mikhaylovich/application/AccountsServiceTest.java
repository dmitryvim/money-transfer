package com.mikhaylovich.application;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class AccountsServiceTest {

    @Test
    public void shouldCreateAccount() {
        // given
        AccountsService service = new AccountsService();

        // when
        int firstId = service.createAccount();
        int secondId = service.createAccount();

        // then
        assertEquals(0, firstId);
        assertEquals(1, secondId);
    }

    @Test
    public void shouldPutMoneyToAccount() {

        // given
        AccountsService service = new AccountsService();
        int id = service.createAccount();
        int amount = 10;

        // when
        service.putMoneyToAccount(id, amount);

        // then
        assertEquals(amount, service.moneyAtAccount(id));
    }

    @Test
    public void shouldTakeMoneyFromAccount() {
        // given
        AccountsService service = new AccountsService();
        int id = service.createAccount();
        int putAmount = 10;
        service.putMoneyToAccount(id, putAmount);
        int takeAmount = 5;

        // when
        service.takeMoneyFromAccount(id, takeAmount);

        // then
        assertEquals(putAmount - takeAmount, service.moneyAtAccount(id));
    }

    @Test
    public void shouldTransferMoney() {
        // given
        AccountsService service = new AccountsService();
        int firstId = service.createAccount();
        int secondId = service.createAccount();
        int firstAmount = 10;
        int transferAmount = 4;
        service.putMoneyToAccount(firstId, firstAmount);

        // when
        service.transferMoney(firstId, secondId, transferAmount);

        // then
        assertEquals(firstAmount - transferAmount, service.moneyAtAccount(firstId));
        assertEquals(transferAmount, service.moneyAtAccount(secondId));
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowExceptionOnNotFoundAccount() {
        // given
        AccountsService service = new AccountsService();

        // expect
        service.moneyAtAccount(42);
    }
}