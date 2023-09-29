package org.unit1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class FrameClient extends JFrame {
    public FrameClient(List<String> chatLog, Consumer<String> serverLog, Consumer<String> writeToFileLog) {

        setLocation(500, 100);
        setSize(400, 400);
        setTitle("Чат Клиент");
        setResizable(false);

        JTextArea taLog = new JTextArea();
        JScrollPane spLog = new JScrollPane(taLog);
        taLog.setEditable(false);
        if(chatLog != null && chatLog.size()>0) {
            StringBuilder sb = new StringBuilder();
            for (String s : chatLog)
                sb.append(s).append("\n");
            taLog.setText(sb.toString());
        }
        add(spLog, BorderLayout.CENTER);

        JPanel panTop = new JPanel(new GridLayout(2, 3));
        JTextField tfIp = new JTextField("172.0.0.1");
        JTextField tfPort = new JTextField("8189");
        JTextField tfLogin = new JTextField("Павел Калинин");
        JPasswordField pfPassword = new JPasswordField("12345");
        JButton btnLogin = new JButton("Подключиться");
        panTop.add(tfIp);
        panTop.add(tfPort);
        panTop.add(new JPanel());
        panTop.add(tfLogin);
        panTop.add(pfPassword);
        panTop.add(btnLogin);
        add(panTop, BorderLayout.NORTH);

        JPanel panBottom = new JPanel(new BorderLayout());
        JTextField tfMessage = new JTextField();
        JButton btnSend = new JButton("Отправить");

        panBottom.add(tfMessage, BorderLayout.CENTER);
        panBottom.add(btnSend, BorderLayout.EAST);
        add(panBottom, BorderLayout.SOUTH);
        setVisible(true);

        ActionListener listenerBtnSend =  e -> {
                    Optional.ofNullable(tfMessage.getText())
                            .filter(s -> !s.trim().isBlank())
                            .ifPresent(s -> {
                                tfMessage.setText("");
                                System.out.println(s);
                                taLog.append(s + "\n");
                                serverLog.accept(s);
                                writeToFileLog.accept(s);
                            });
                };

        btnSend.addActionListener(listenerBtnSend);
        tfMessage.addKeyListener(new java.awt.event.KeyAdapter() {
                  @Override public void keyReleased(java.awt.event.KeyEvent evt) {
                    if ( evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER )
                        listenerBtnSend.actionPerformed(null);
                  }
        });
    }

}
