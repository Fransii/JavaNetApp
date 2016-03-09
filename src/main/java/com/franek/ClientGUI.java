package com.franek;


import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.*;

/**
 * Created by franciszekdanes on 07.03.2016.
 */
public class ClientGUI
{
    public JFrame frame = new JFrame("JavaNetApp");
    public JPanel panel = new JPanel();

    public JLabel activeUsersLabel = new JLabel("Active users list :");

    public JButton sendButton = new JButton("Send");
    public JButton changeFriendButton = new JButton("-> Change friend nickname <-");

    public JTextArea textToSend = new JTextArea(3,30);
    public JTextArea messagesText = new JTextArea(20,40);
    public JTextArea activeUsersList = new JTextArea(20,20);

    public ClientGUI()
    {
        panel.setLayout(new MigLayout());

        panel.add(activeUsersLabel, "north, gapleft 565, gaptop 30,  wrap, push, grow");

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
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),"gapright 30, gapbottom 20, wrap, push, grow");

        panel.add(changeFriendButton);


        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
}
