package com.company;

import jdk.jshell.execution.Util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Gity !
 * in this class we can create new Home page to run commands
 *
 * Network Project
 *
 *
 * @author Seyed Nami Modarressi
 * @version 1.0
 *
 * Spring 2021
 */
public class HomePage {

    private JFrame Home;
    private JLabel icon;
    private JLabel name;
    private JLabel loginDate;
    private JLabel Date;
    private JLabel version;
    private JLabel text_repo;
    private JTextArea view;
    private String username;
    private JButton run;
    private JTextField command;
    private JButton help;
    private JButton out;

    Color text = new Color(51, 51, 51);
    Color background = new Color(237, 237, 237);
    Color line = new Color(240, 81, 51);

    public HomePage(String username){

        this.username=username;
        Home = new JFrame("Gity");
        Home.setSize(700, 400);
        Home.setLocationRelativeTo(null);
        Home.setLayout(null);
        Home.setResizable(false);
        Home.getContentPane().setBackground(Color.white);
        Home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HomeElements();
        showHome();

    }

    /**
     * add Home page elements
     */
    public void HomeElements(){

        icon = new JLabel();
        icon.setIcon(new ImageIcon("images/user.png"));
        icon.setSize(125,125);
        icon.setLocation(0,0);
        Home.add(icon);

        name = new JLabel("User"+" : "+username);
        name.setLocation(130,0);
        name.setSize(200,70);
        name.setForeground(text);
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        Home.add(name);

        Date = new JLabel("Today's Date"+" : "+ java.time.LocalDate.now());
        Date.setLocation(130,20);
        Date.setSize(200,70);
        Date.setForeground(text);
        Date.setFont(new Font("Arial", Font.PLAIN, 15));
        Home.add(Date);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime now = LocalTime.now();
        loginDate = new JLabel("Login"+" : "+ dtf.format(now));
        loginDate.setLocation(130,40);
        loginDate.setSize(200,70);
        loginDate.setForeground(text);
        loginDate.setFont(new Font("Arial", Font.PLAIN, 15));
        Home.add(loginDate);

        version = new JLabel("Gity Version"+" : 1.01");
        version.setLocation(130,60);
        version.setSize(200,70);
        version.setForeground(text);
        version.setFont(new Font("Arial", Font.PLAIN, 15));
        Home.add(version);


        view = new JTextArea();
        view.setLocation(400,70);
        view.setSize(298,300);
        view.setFont(new Font("Arial", Font.PLAIN, 12));
        view.setEnabled(false);
        view.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, line));
        Home.add(view);

        text_repo = new JLabel("Console");
        text_repo.setLocation(400,0);
        text_repo.setSize(400,70);
        text_repo.setForeground(text);
        text_repo.setFont(new Font("Arial", Font.PLAIN, 30));
        Home.add(text_repo);

        command = new JTextField();
        command.setLocation(10,200);
        command.setSize(380,50);
        command.setForeground(text);
        command.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, background));
        command.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, background));
        command.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, line));
        command.setBackground(Color.white);
        command.setText("Enter Your Command ...");
        Home.add(command);

        run = new JButton("Run");
        run.setLocation(110, 280);
        run.setSize(200, 70);
        run.setForeground(text);
        run.setFont(new Font("Arial", Font.PLAIN, 20));
        run.setBounds(run.getX(), run.getY(), 180, 30);
        run.setBorder(new RoundBorder(20));
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client run = new Client(username,command.getText());
                    view.setText(run.run());
                    command.setText("Enter Your Command ...");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        Home.add(run);

        help = new JButton();
        help.setIcon(new ImageIcon("images/help.png"));
        help.setLocation(365,340);
        help.setSize(32,32);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        help.setBorder(emptyBorder);
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String help;
                help = "Commands : \n\nList of Users : ls -u\nRepositories of user : ls -r name_of_user\n" +
                        "Make new Repository : mkrepo -public(or private) name_of_repository\n" +
                        "Add new contributor : addc name_of_repo name_of_contributor\n"+
                        "Change Repository mode : chm name_of_repo public(or private)\n";
                view.setText(help);
            }
        });
        Home.add(help);

        out = new JButton();
        out.setIcon(new ImageIcon("images/out.png"));
        out.setLocation(5,340);
        out.setSize(32,32);
        out.setBorder(emptyBorder);
        out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home.dispose();
                JOptionPane.showMessageDialog(Home, "Sign out Successfully", "Gity", JOptionPane.PLAIN_MESSAGE);
                LoginAndRegisterPage next = new LoginAndRegisterPage();
            }
        });
        Home.add(out);
    }
    /**
     * show Home page
     */
    public void showHome(){
        Home.setVisible(true);
    }
}
