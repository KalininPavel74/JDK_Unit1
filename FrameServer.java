package org.unit1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FrameServer extends JFrame {
    private JTextArea taLog;
    public FrameServer(List<String> chatLog) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocation(100,100);
        setSize(400,400);
        setTitle("Чат Сервер");
        setResizable(false);

        this.taLog = new JTextArea();
        JScrollPane spLog = new JScrollPane(taLog);
        taLog.setEditable(false);
        if(chatLog != null && chatLog.size()>0) {
            StringBuilder sb = new StringBuilder();
            for (String s : chatLog)
                sb.append(s).append("\n");
            taLog.setText(sb.toString());
        }
        add(spLog, BorderLayout.CENTER);

        JPanel panBottom = new JPanel(new GridLayout(1,2));
        JButton btnStart = new JButton("Запустить");
        JButton btnStop = new JButton("Остановить");
        panBottom.add(btnStart);
        panBottom.add(btnStop);

        add(panBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void addLog(String s) {
        this.taLog.append(s+"\n");
    }
}
