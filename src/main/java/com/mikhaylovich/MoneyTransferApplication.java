package com.mikhaylovich;

import com.mikhaylovich.application.AccountsService;
import com.mikhaylovich.rest.AccountsController;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class MoneyTransferApplication {

    public static void main(String[] args) {
        AccountsService accountsService = new AccountsService();
        AccountsController accountsController = new AccountsController(accountsService);

        /*
            To make app more configurable application.properties file can be added
            or at least we can args parser library and parse arg `--port=8081`
        */
        //configure port here
        accountsController.startServer(8081);
    }
}
