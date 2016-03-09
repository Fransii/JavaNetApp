package com.franek;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by franciszekdanes on 09.03.2016.
 */
public class ClientGUITest {
    public static void main(String[] args) {

        final ClientForGUI clientForGUI = new ClientForGUI();

        final loginGUI loginGUI = new loginGUI();

        loginGUI.frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    clientForGUI.closeConnection();
                } catch (IOException kl) {
                }
                loginGUI.frame.setVisible(false);
            }
        });

        loginGUI.enterNickName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                clientForGUI.nickName = loginGUI.nickNameField.getText();
                loginGUI.frame.setVisible(false);
                final ClientGUI clientGUI = new ClientGUI();
                final ChangeFriendNickNameGUI changeFriendNickNameGUI = new ChangeFriendNickNameGUI();
                changeFriendNickNameGUI.frame.setVisible(false);
                clientGUI.changeFriendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        changeFriendNickNameGUI.frame.setVisible(true);
                    }
                });
                changeFriendNickNameGUI.enterNickName.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clientForGUI.deliveryHost = changeFriendNickNameGUI.nickNameField.getText();
                        changeFriendNickNameGUI.frame.setVisible(false);
                    }
                });


                try {
                    clientForGUI.connect();
                    clientForGUI.setGUI(clientGUI);
                    clientGUI.messagesText.append("Succesfull Conected\n");
                    clientForGUI.makeBufferedReader();
                    clientForGUI.sendMSG();


                    // Exit application handle
                    clientGUI.frame.addWindowListener(new WindowAdapter() {

                        @Override
                        public void windowClosing(WindowEvent e) {
                            try {
                                clientForGUI.closeConnection();
                            } catch (IOException kl) {
                            }
                            clientGUI.frame.setVisible(false);
                            clientGUI.frame.dispose();
                            loginGUI.frame.dispose();
                            changeFriendNickNameGUI.frame.dispose();
                        }
                    });

                    clientForGUI.communication();
                    clientForGUI.getUsersList();

                } catch (IOException bb) {}

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }
}
