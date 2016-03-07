package com.franek;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by franciszekdanes on 07.03.2016.
 */

public class ReceiveMessages extends Thread {
    public BufferedReader inp;
    public String str;
    public boolean stopFlag;
    public ReceiveMessages(BufferedReader inpp)
    {
        this.stopFlag = false;
        this.inp = inpp;
    }

    public void stopThread()
    {
        this.stopFlag = true;
    }

    public void run()
    {

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
                        System.out.println(this.str);
                    }
            }
        }
    }
}
