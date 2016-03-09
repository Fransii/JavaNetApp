package com.franek;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Created by franciszekdanes on 09.03.2016.
 */
public class loginGUI
{
    public JFrame frame = new JFrame("JavaNetApp");
    public JPanel panel = new JPanel();

    public JLabel loginLabel = new JLabel("Login");
    public JLabel nickNameText = new JLabel("Enter your nickname: ");

    public JTextField nickNameField = new JTextField(15);

    public JButton enterNickName = new JButton("Enter");

    public loginGUI()
    {
        panel.setLayout(new MigLayout());
        CC centerConstraint = new CC();
        centerConstraint.alignX("center").spanX();
        centerConstraint.wrap();

        panel.add(loginLabel,centerConstraint);
        panel.add(nickNameText,centerConstraint);
        panel.add(nickNameField,"wrap");
        panel.add(enterNickName,centerConstraint);


        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.getRootPane().setDefaultButton(enterNickName);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
