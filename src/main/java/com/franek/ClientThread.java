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
    public String clientName;

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
            this.closeConnection();
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

    //Send list with active users to client.
    public void sendUserList()
    {
        String stringUserList = new String();
        for(int i =0; i<Server.usersList.size();i++)
        {
            stringUserList = stringUserList + " " + Server.usersList.get(i);
        }

        System.out.println(stringUserList);
        this.outp.println(stringUserList);
        this.outp.flush();
    }
    public void communication() throws IOException, NullPointerException {
        String tekst = "";
        String str;
        JSONObject msg;
        do {
            // komunikacja - czytanie danych ze strumienia,
            str = this.inp.readLine();
            msg = new JSONObject(str);

            if (!Server.usersList.contains(msg.getString("nickName"))) {
                try {
                    this.clientName = msg.getString("nickName");
                    Server.usersList.add(msg.getString("nickName"));
                    Server.userInfo.put(msg.getString("nickName"), this.sock.getInetAddress());
                    System.out.println(Server.userInfo);
                    } catch (NullPointerException eee){}
                }

            if (str != "")
            {
                System.out.println("<Nadeszlo:> " + msg.get("msg") + " od : " + msg.get("nickName") + this.sock.getInetAddress());
            }
            tekst = msg.getString("msg");

            if (str.compareTo("!USERS") != 0)
            {
                sendUserList();
            }
        } while (tekst.compareTo("END") != 0);
    }

    public void closeConnection() throws IOException {
        System.out.println("Polaczenie z " + this.clientName + "("+this.sock.getInetAddress() +")" + " zakończone !");
        this.inp.close();
        this.sock.close();

        for(int i =0; i<Server.usersList.size();i++)
        {
            if(Server.usersList.get(i).compareTo(this.clientName) != 0) {
            }else
            {
                Server.usersList.remove(i);
            }
        }
    }

    @Override
    public void run() {
        init();
    }
}
