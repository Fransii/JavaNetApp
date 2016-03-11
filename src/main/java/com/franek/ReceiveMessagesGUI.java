package com.franek;

import org.json.JSONObject;
import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by franciszekdanes on 09.03.2016.
 */

public class ReceiveMessagesGUI extends Thread{

    public BufferedReader inp;
    public String str;
    public static boolean stopFlag;

    public String soundName = "/getMsgSound.wav";


    public ClientGUI clientGUI;

    public void playSound()
    {
        try {
            // Open an audio input stream.
            InputStream in = getClass().getResourceAsStream(soundName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


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
                    else{
                        this.clientGUI.messagesText.append(mss.getString("msg")+"\n");
                        playSound();
                    }
                }
            }
        }
    }
}
