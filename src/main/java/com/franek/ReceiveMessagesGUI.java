package com.franek;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by franciszekdanes on 09.03.2016.
 */
public class ReceiveMessagesGUI extends Thread{

    public BufferedReader inp;
    public String str;
    public static boolean stopFlag;

    public ClientGUI clientGUI;


    public ReceiveMessagesGUI(BufferedReader inpp,ClientGUI clientGUI)
    {
        this.stopFlag = false;
        this.inp = inpp;
        this.clientGUI = clientGUI;
    }

    public void stopThread()
    {
        this.stopFlag = true;
    }

    public void run()
    {
        JSONObject mss = new JSONObject();
        while(!this.stopFlag) {

            //Reading info from server.

            try {
                this.str = this.inp.readLine();
            }catch (IOException yy){System.out.println("lipton");}
            //Check if string is empty.
            if (this.str != "")
            {
                // Check if string is null (protect from printing null on end connection).
                if(this.str != null)
                {
                    mss = new JSONObject(str);
                    if (mss.getString("whatToDo").compareTo("!USERS") == 0)
                        this.clientGUI.activeUsersList.setText(mss.getString("msg").replace(" ","\n"));
                    else
                        this.clientGUI.messagesText.append(mss.getString("msg")+"\n");
                }
            }
        }
    }
}
