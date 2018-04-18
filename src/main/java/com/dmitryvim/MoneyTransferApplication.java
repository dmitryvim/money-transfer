package com.dmitryvim;

import com.dmitryvim.application.AccountsService;
import com.dmitryvim.rest.AccountsController;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class MoneyTransferApplication {

    public static void main(String[] args) {
        AccountsService accountsService = new AccountsService();
        AccountsController accountsController = new AccountsController(accountsService);
        //TODO add readme, how to test app
        accountsController.startServer(8080);
    }
}
