package com.mikhaylovich.application;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class AccountsServiceTest {

    private AccountsService service;

    @Before
    public void initService() {
        this.service = new AccountsService();
    }
    
    @Test
    public void shouldCreateAccount() {
        // when
        int firstId = this.service.createAccount();
        int secondId = this.service.createAccount();

        // then
        assertEquals(0, firstId);
        assertEquals(1, secondId);
    }

    @Test
    public void shouldPutMoneyToAccount() {
        // given
        int id = this.service.createAccount();
        int amount = 10;

        // when
        this.service.putMoneyToAccount(id, amount);

        // then
        assertEquals(amount, this.service.moneyAtAccount(id));
    }

    @Test
    public void shouldTakeMoneyFromAccount() {
        // given
        int id = this.service.createAccount();
        int putAmount = 10;
        this.service.putMoneyToAccount(id, putAmount);
        int takeAmount = 5;

        // when
        this.service.takeMoneyFromAccount(id, takeAmount);

        // then
        assertEquals(putAmount - takeAmount, service.moneyAtAccount(id));
    }

    @Test
    public void shouldTransferMoney() {
        // given
        int firstId = this.service.createAccount();
        int secondId = this.service.createAccount();
        int firstAmount = 10;
        int transferAmount = 4;
        this.service.putMoneyToAccount(firstId, firstAmount);

        // when
        this.service.transferMoney(firstId, secondId, transferAmount);

        // then
        assertEquals(firstAmount - transferAmount, service.moneyAtAccount(firstId));
        assertEquals(transferAmount, service.moneyAtAccount(secondId));
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowExceptionOnNotFoundAccount() {
        this.service.moneyAtAccount(42012);
    }
}