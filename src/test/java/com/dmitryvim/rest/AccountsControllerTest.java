package com.dmitryvim.rest;

import com.dmitryvim.application.AccountsService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class AccountsControllerTest {

    private static int port;

    private static AccountsService accountsService;

    private static AccountsController controller;

    @BeforeClass
    public static void startService() {
        port = findAvailablePort();
        accountsService = Mockito.mock(AccountsService.class);
        controller = new AccountsController(accountsService);
        controller.startServer(port);
    }

    @AfterClass
    public static void stopService() {
        controller.stopServer();
    }

    @Test
    public void checkCreateAccountResource() throws Exception {
        // when
        HttpResponse<String> response = Unirest.post(url("accounts")).asString();

        // then
        assertEquals(200, response.getStatus());
        Mockito.verify(accountsService).createAccount();
    }

    @Test
    public void checkReceiveAccountResource() throws Exception {
        // given
        int id = 12;
        int money = 100;
        Mockito.when(accountsService.moneyAtAccount(id)).thenReturn(money);

        // when
        HttpResponse<String> response = Unirest.get(url("accounts/" + id)).asString();

        // then
        assertEquals(200, response.getStatus());
        assertEquals(String.valueOf(money), response.getBody());
    }

    @Test
    public void checkPutMoneyResource() throws Exception {
        // given
        int id = 12;
        int amount = 100;

        // when
        HttpResponse<String> response = Unirest.post(url("accounts/" + id + "/put-money?amount=" + amount)).asString();

        //then
        assertEquals(200, response.getStatus());
        Mockito.verify(accountsService).putMoneyToAccount(id, amount);
    }

    @Test
    public void checkTakeMoneyResource() throws Exception {
        // given
        int id = 12;
        int amount = 100;

        // when
        HttpResponse<String> response = Unirest.post(url("accounts/" + id + "/take-money?amount=" + amount)).asString();

        //then
        assertEquals(200, response.getStatus());
        Mockito.verify(accountsService).takeMoneyFromAccount(id, amount);
    }

    @Test
    public void checkTransferMoneyResource() throws Exception {
        // given
        int from = 12;
        int to = 10;
        int amount = 100;

        // when
        HttpResponse<String> response = Unirest.post(url("transfer?from=" + from + "&to=" + to + "&amount=" + amount)).asString();

        //then
        assertEquals(200, response.getStatus());
        Mockito.verify(accountsService).transferMoney(from, to, amount);
    }

    private String url(String path) {
        return "http://localhost:" + port + "/" + path;
    }

    private static int findAvailablePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("Some exception occurred on random port search.", e);
        }
    }
}