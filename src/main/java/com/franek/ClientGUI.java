package com.franek;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;

/**
 * Created by franciszekdanes on 07.03.2016.
 */
public class ClientGUI
{
    JFrame frame = new JFrame("JavaNetApp");
    JPanel panel = new JPanel();


    JButton sendButton = new JButton("Send");

    JTextArea textToSend = new JTextArea(3,30);
    JTextArea messagesText = new JTextArea(20,40);
    JTextArea activeUsersList = new JTextArea(20,20);

    public ClientGUI()
    {
        panel.setLayout(new MigLayout());

        activeUsersList.setLineWrap(true);
        panel.add(new JScrollPane(activeUsersList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),"east, gapright 30, gaptop 30, gapbottom 80");

        messagesText.setLineWrap(true);
        panel.add(new JScrollPane(messagesText,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),"gapleft 30, gapright 30, gaptop 30, wrap, push, grow");

        panel.add(sendButton,"top, gapleft 30, split 2");

        textToSend.setLineWrap(true);
        panel.add(new JScrollPane(textToSend,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),"gapright 30, gapbottom 20, push, grow");





        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                new ClientGUI();
            }
        });
    }
}
