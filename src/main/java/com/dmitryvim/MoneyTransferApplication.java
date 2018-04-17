package com.dmitryvim;

import com.dmitryvim.application.MoneyTransferService;
import com.dmitryvim.rest.AccountsController;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class MoneyTransferApplication {

    public static void main(String[] args) {
        MoneyTransferService moneyTransferService = new MoneyTransferService();
        AccountsController accountsController = new AccountsController(moneyTransferService);
        accountsController.startServer();
    }
}
