package com.mikhaylovich.rest;


import com.mikhaylovich.account.NotEnoughMoneyException;
import com.mikhaylovich.application.AccountNotFoundException;
import com.mikhaylovich.application.AccountsService;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    public void startServer(int port) {
        port(port);
        post("/accounts", this::createAccount);
        post("/accounts/:id/put-money", this::putMoney);
        post("/accounts/:id/take-money", this::takeMoney);
        post("/transfer", this::transferMoney);
        get("/accounts/:id", this::account);

        // common cases
        notFound("Not found");
        internalServerError("Internal server error");
        exception(AccountNotFoundException.class, (exception, request, response) -> {
            response.status(404);
            response.body(exception.getMessage());
        });
        exception(NotEnoughMoneyException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
        exception(IllegalArgumentException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
    }

    public void stopServer() {
        stop();
    }

    private String account(Request request, Response response) {
        int id = accountId(request);
        int money = this.accountsService.moneyAtAccount(id);
        return String.valueOf(money);
    }

    private Integer accountId(Request request) {
        return Integer.valueOf(request.params("id"));
    }

    private String createAccount(Request request, Response response) {
        int id = this.accountsService.createAccount();
        return String.valueOf(id);
    }

    private String putMoney(Request request, Response response) {
        int id = accountId(request);
        int amount = intQueryParam(request, "amount");
        this.accountsService.putMoneyToAccount(id, amount);
        return "Success";
    }

    private Integer intQueryParam(Request request, String param) {
        return Integer.valueOf(request.queryParamOrDefault(param, "0"));
    }

    private String takeMoney(Request request, Response response) {
        int id = accountId(request);
        int amount = intQueryParam(request, "amount");
        this.accountsService.takeMoneyFromAccount(id, amount);
        return "Success";
    }

    private String transferMoney(Request request, Response response) {
        int fromId = intQueryParam(request, "from");
        int toId = intQueryParam(request, "to");
        int amount = intQueryParam(request, "amount");
        this.accountsService.transferMoney(fromId, toId, amount);
        return "Money transfered";
    }
}
