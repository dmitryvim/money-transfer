package com.mikhaylovich.rest;


import com.mikhaylovich.account.NotEnoughMoneyException;
import com.mikhaylovich.application.AccountNotFoundException;
import com.mikhaylovich.application.AccountsService;
import org.eclipse.jetty.http.HttpStatus;
import spark.ExceptionHandler;
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

    private static <T extends Exception> ExceptionHandler<T> handleExceptionWithStatus(int statusCode) {
        return (exception, request, response) -> {
            response.status(statusCode);
            response.body(exception.getMessage());
        };
    }

    public void stopServer() {
        stop();
    }

    /*
        To keep code simple I skipped any json serializations and used query params and text answers.

        In RESTful production way its better to model json objects for commands like put, take and transfer money.
        Query params should be better used for filters and search queries.
    */
    public void startServer(int port) {
        // configure port
        port(port);

        // business endpoints
        post("/accounts", this::createAccount);
        post("/accounts/:id/put-money", this::putMoney);
        post("/accounts/:id/take-money", this::takeMoney);
        post("/transfer", this::transferMoney);
        get("/accounts/:id", this::account);

        // exception handling
        notFound("Not found");
        internalServerError("Internal server error");
        exception(AccountNotFoundException.class, handleExceptionWithStatus(HttpStatus.NOT_FOUND_404));
        exception(NotEnoughMoneyException.class, handleExceptionWithStatus(HttpStatus.BAD_REQUEST_400));
        exception(IllegalArgumentException.class, handleExceptionWithStatus(HttpStatus.BAD_REQUEST_400));
    }

    private Integer accountId(Request request) {
        return Integer.valueOf(request.params("id"));
    }

    private String createAccount(Request request, Response response) {
        int id = this.accountsService.createAccount();
        return String.valueOf(id);
    }

    /*
        In case of network disconnect it's better to identify request when you send it.
        For example the user can propose accountId to identify created account even if connection was broken and ok status missed.
    */
    private String account(Request request, Response response) {
        int id = accountId(request);
        int money = this.accountsService.moneyAtAccount(id);
        return String.valueOf(money);
    }

    private Integer intQueryParam(Request request, String param) {
        return Integer.valueOf(request.queryParamOrDefault(param, "0"));
    }

    private String putMoney(Request request, Response response) {
        int id = accountId(request);
        int amount = intQueryParam(request, "amount");
        this.accountsService.putMoneyToAccount(id, amount);
        return "Success.";
    }

    private String takeMoney(Request request, Response response) {
        int id = accountId(request);
        int amount = intQueryParam(request, "amount");
        this.accountsService.takeMoneyFromAccount(id, amount);
        return "Success.";
    }

    private String transferMoney(Request request, Response response) {
        int fromId = intQueryParam(request, "from");
        int toId = intQueryParam(request, "to");
        int amount = intQueryParam(request, "amount");
        this.accountsService.transferMoney(fromId, toId, amount);
        return "Money transferred.";
    }
}
