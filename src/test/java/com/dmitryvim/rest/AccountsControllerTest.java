package com.dmitryvim.rest;

import com.dmitryvim.application.AccountsService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class AccountsControllerTest {

    private int port = findAvailablePort();

    private AccountsService service;

    private AccountsController controller;

    @Before
    public void startService() {
        this.service = Mockito.mock(AccountsService.class);
        this.controller = new AccountsController(this.service);
        this.controller.startServer(this.port);
    }

    @Test
    public void checkResource() throws Exception {
        // when
        HttpResponse<String> response = Unirest.post(url("accounts")).asString();

        // then
        assertEquals(200, response.getStatus());
        Mockito.verify(this.service).createAccount();
    }

    //TODO написать простые тесты для других endpoints

    @After
    public void stopService() {
        this.controller.stopServer();
    }

    private String url(String path) {
        return "http://localhost:" + this.port + "/" + path;
    }

    private static int findAvailablePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("Some exception occurred on random port search.", e);
        }
    }
}