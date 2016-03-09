package com.franek;

import org.json.JSONObject;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by franciszekdanes on 09.03.2016.
 */
public class ClientForGUI
{
    public static final int PORT = 50007;
    public static final String HOST = "127.0.0.1";

    public Socket sock;
    public BufferedReader klaw;
    public BufferedReader inp;
    public PrintWriter outp;

    public ClientGUI clientGUI;

    public ReceiveMessages receiveMsg;
    public boolean firstConnection;

    public String nickName;
    public String deliveryHost;

    public void connect() throws IOException
    {
        sock = new Socket(HOST, PORT);
        System.out.println("Nawiazalem polaczenie: " + this.sock);
    }

    public void setGUI(ClientGUI clientGUI)
    {
        this.clientGUI = clientGUI;
    }

    //Make buffered reader to read fom input and write to output.
    public void makeBufferedReader() throws IOException
    {
        this.inp = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
        this.klaw = new BufferedReader(new InputStreamReader(System.in));
        this.outp = new PrintWriter(this.sock.getOutputStream());
    }

    //Close connection to server, buffered reader&writer,  end application.
    public void closeConnection() throws IOException
    {
        JSONObject msg1 = new JSONObject();

        // Building JSON object to send.
        msg1.put("msg","!END");
        msg1.put("nickName",nickName);
        msg1.put("deliveryHost",deliveryHost);

        // Convert JSON object to string.
        String msg1J = msg1.toString();
        outp.println(msg1J);
        outp.flush();

        ReceiveMessagesGUI.stopFlag = true;
        getUsersListThread.stopFlag = true;

        // Close buffered reader&writer, socket
        this.klaw.close();
        this.outp.close();
        this.sock.close();


    }
    public  void getUsersList()
    {
        getUsersListThread getUsersList = new getUsersListThread(this.outp,this.nickName);
        getUsersList.start();
    }

    public void communication()
    {
        ReceiveMessagesGUI receiveMsgGUI = new ReceiveMessagesGUI(this.inp, this.clientGUI);
        receiveMsgGUI.start();

    }

    public void sendMSG()
    {
        this.firstConnection = true;

        this.clientGUI.sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(firstConnection) {
                    deliveryHost = nickName;
                    firstConnection = false;
                }

                String str = clientGUI.textToSend.getText();

                JSONObject msg1 = new JSONObject();

                // Building JSON object to send.
                msg1.put("msg", str);
                msg1.put("nickName", nickName);
                msg1.put("deliveryHost", deliveryHost);

                // Convert JSON object to string.
                String msg1J = msg1.toString();

                outp.println(msg1J);
                outp.flush();

                clientGUI.messagesText.append(nickName + " : " + str + "\n");
                clientGUI.textToSend.setText("");


            }
        });
    }
}
