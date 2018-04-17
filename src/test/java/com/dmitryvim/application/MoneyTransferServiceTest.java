package com.dmitryvim.application;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class MoneyTransferServiceTest {

    @Test
    public void shouldCreateAccount() {
        // given
        MoneyTransferService service = new MoneyTransferService();

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
        MoneyTransferService service = new MoneyTransferService();
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
        MoneyTransferService service = new MoneyTransferService();
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
        MoneyTransferService service = new MoneyTransferService();
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
        MoneyTransferService service = new MoneyTransferService();

        // expect
        service.moneyAtAccount(42);
    }
}