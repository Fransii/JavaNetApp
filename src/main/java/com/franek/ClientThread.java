package com.franek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by dbialy on 01.03.16.
 */
public class ClientThread extends Thread {

    public Socket sock;
    public BufferedReader inp;
    public PrintWriter outp;

    private final List<ClientThread> threads;


    public ClientThread(Socket socket, List<ClientThread> socketList) {
        this.sock = socket;
        this.threads = socketList;
    }

    private void init() {
        try {
            this.makeBufferedReader();
            this.makeBufferedPrinter();
            this.communication();
            this.closeConnection(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnection() {
        return this.sock.isConnected();
    }

    public void makeBufferedPrinter() throws IOException {
        this.outp = new PrintWriter(this.sock.getOutputStream());
    }

    public void makeBufferedReader() throws IOException {
        // tworzenie strumienia danych pobieranych z gniazda sieciowego
        this.inp = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
    }

    public void communication() throws IOException {
        String tekst = "";
        do {
            // komunikacja - czytanie danych ze strumienia
            String str;
            str = this.inp.readLine();
            if (str != "") {
                System.out.println("<Nadeszlo:> " + str + " od : " + this.sock.getInetAddress());
            }
            tekst = str;
            this.outp.println("Odsylam: " + "\"" + str + "\"" );
            this.outp.flush();
        } while (tekst.compareTo("END") != 0);
    }

    public void closeConnection(ServerSocket serv) throws IOException {
        // zamykanie polaczenia
        System.out.println("Polaczenie z " + this.sock.getInetAddress() + " zakończone !");
        this.inp.close();
        this.sock.close();
    }

    @Override
    public void run() {
        init();
    }
}
