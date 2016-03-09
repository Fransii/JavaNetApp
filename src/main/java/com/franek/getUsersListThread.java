package com.franek;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by franciszekdanes on 09.03.2016.
 */
public class getUsersListThread extends Thread {

    public static boolean stopFlag;
    public PrintWriter outp;
    public String nickName;

    public getUsersListThread(PrintWriter outp, String nickname)
    {
        this.outp = outp;
        this.nickName = nickname;
    }


    public void run()
    {
        while(!stopFlag)
        {
            JSONObject msg1 = new JSONObject();
            msg1.put("msg","!USERS");
            msg1.put("nickName",this.nickName);
            msg1.put("deliveryHost",this.nickName);
            String msg1J = msg1.toString();
            this.outp.println(msg1J);
            this.outp.flush();

            try {
                Thread.sleep(5000);
            }catch(InterruptedException ghg){}
        }
    }
}
