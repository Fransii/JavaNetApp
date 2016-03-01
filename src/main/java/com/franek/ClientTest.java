package com.franek;

import java.io.IOException;

public class ClientTest {

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.connect();
            client.makeBufferedReader();
            client.communication();
            client.closeConnection();
        } catch (IOException e) {
            System.out.print("Brzydkie slowo :(- " + ClientTest.class.getSimpleName() + ": " + e.getMessage());
        }
    }
}