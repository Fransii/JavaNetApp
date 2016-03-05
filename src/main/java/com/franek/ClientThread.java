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
    public String deliveryName;
    public Socket deliverySocket;

    private final List<ClientThread> threads;

    public ClientThread(Socket socket, List<ClientThread> socketList)
    {
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
    // Make buffered printer to send msg.
    public void makeBufferedPrinter() throws IOException
    {
        this.outp = new PrintWriter(this.sock.getOutputStream());
    }
    // Make buffered writer to get msg.
    public void makeBufferedReader() throws IOException
    {
        this.inp = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
    }

    // Send message from one user by server to destination host.
    public void sendMsgTo(String completeMsg,String msg)
    {
        try {
            //Set new socket of destination user
            this.outp = new PrintWriter(this.deliverySocket.getOutputStream());
        }catch(IOException ww){
            System.out.println("blad 1");
        }
        if (msg.compareTo(" ") == 0)
        {   // Send empty msg.
            this.outp.println(msg);
            this.outp.flush();
        }else
        {   // Send complete msg.
            this.outp.println(completeMsg);
            this.outp.flush();
        }
        try {
            //Set old socket
            this.outp = new PrintWriter(this.sock.getOutputStream());
        }catch(IOException rr){
            System.out.println("blad 2");
        }
    }
    //Send list with active users to client.
    public void sendUserList()
    {   // Build list of users.
        String stringUserList = new String();
        for(int i =0; i<Server.usersList.size();i++)
        {
            stringUserList = stringUserList + " " + Server.usersList.get(i);
        }

        // Send rdy list.
        System.out.println(stringUserList);
        this.outp.println(stringUserList);
        this.outp.flush();
    }
    // Main communication between server and user
    public void communication() throws IOException, NullPointerException {
        String tekst = "";
        String str;
        JSONObject msg;
        do {
            str = this.inp.readLine();
            msg = new JSONObject(str);

            // Check if user exist on servers ACTIVE users list.
            if (!Server.usersList.contains(msg.getString("nickName"))) {
                try {
                    this.clientName = msg.getString("nickName");
                    Server.usersList.add(msg.getString("nickName"));
                    Server.userInfo.put(msg.getString("nickName"), this.sock);
                    } catch (NullPointerException eee){}
                }

            if (str != "")
            {
                System.out.println("<Nadeszlo:> " + msg.get("msg") + " od : " + msg.get("nickName") + this.sock.getInetAddress());
            }

            // Variable for check what to do.
            tekst = msg.getString("msg");

            // Set destination user info
            this.deliveryName = msg.getString("deliveryHost");
            this.deliverySocket = (Socket)Server.userInfo.get(deliveryName);

            // Select what to do.
            if (tekst.compareTo("!USERS") == 0)
            {
                sendUserList();
            }else
            {
                sendMsgTo(msg.getString("nickName")+" napisal: "+tekst,tekst);
            }
            // Check if user want to close connection.
        } while (tekst.compareTo("!END") != 0);
    }

    //Close connection between server and user.
    public void closeConnection() throws IOException
    {
        System.out.println("Polaczenie z " + this.clientName + "("+this.sock.getInetAddress() +")" + " zakończone !");
        // Close buffered reader, socket.
        this.inp.close();
        this.sock.close();

        // Remove user nickname from server active users list on close connection.
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
