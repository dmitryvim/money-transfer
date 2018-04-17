package com.dmitryvim.rest;


import com.dmitryvim.application.MoneyTransferService;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class AccountsController {

    private final MoneyTransferService moneyTransferService;

    public AccountsController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    public void startServer() {
        port(8080);
        post("/accounts", this::createAccount);
        post("/accounts/:id/add-money", this::putMoney);
        post("/accounts/:id/take-money", this::takeMoney);
        post("/transfer", this::transferMoney);
        get("/accounts/:id", this::account);
    }

    public void stopServer() {
        stop();
    }

    private String account(Request request, Response response) {
        int id = accountId(request);
        int money = this.moneyTransferService.moneyAtAccount(id);
        return String.valueOf(money);
    }

    private Integer accountId(Request request) {
        return Integer.valueOf(request.params("id"));
    }

    private String createAccount(Request request, Response response) {
        int id = this.moneyTransferService.createAccount();
        return String.valueOf(id);
    }

    private String putMoney(Request request, Response response) {
        int id = accountId(request);
        int amount = intQueryParam(request, "amount");
        this.moneyTransferService.putMoneyToAccount(id, amount);
        return "Success";
    }

    private Integer intQueryParam(Request request, String param) {
        return Integer.valueOf(request.queryParamOrDefault(param, "0"));
    }

    private String takeMoney(Request request, Response response) {
        int id = accountId(request);
        int amount = intQueryParam(request, "amount");
        this.moneyTransferService.takeMoneyFromAccount(id, amount);
        return "Success";
    }

    private String transferMoney(Request request, Response response) {
        int fromId = intQueryParam(request, "from");
        int toId = intQueryParam(request, "to");
        int amount = intQueryParam(request, "amount");
        this.moneyTransferService.transferMoney(fromId, toId, amount);
        return "transfer money";
    }
}
