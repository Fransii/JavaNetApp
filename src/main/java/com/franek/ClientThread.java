package com.franek;

import org.json.JSONObject;
import sun.security.util.Length;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by dbialy on 01.03.16.
 */
public class ClientThread extends Thread {

    public Socket sock;
    public BufferedReader inp;
    public PrintWriter outp;

    public ArrayList<String> usersList;

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

    public void communication() throws IOException,NullPointerException {
        String tekst = "";
        do {
            // komunikacja - czytanie danych ze strumienia
            String str;
            str = this.inp.readLine();

            JSONObject msg = new JSONObject(str);
            try {
                this.usersList.add(msg.getString("nickName"));
            }
            catch (NullPointerException ee){}

            System.out.println(this.usersList);

            if (str != "") {
                System.out.println("<Nadeszlo:> " + msg.get("msg") + " od : " + msg.get("nickName") + this.sock.getInetAddress());
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
