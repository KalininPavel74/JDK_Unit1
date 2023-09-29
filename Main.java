package org.unit1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 Java Development Kit (семинары)
 Урок 1. Графические интерфейсы

 Калинин Павел
 29.09.2023

 1. Собрать графический интерфейс проекта месседжера
    (скриншоты можно посмотреть в материалах к уроку)
 2. Отправлять сообщения из текстового поля сообщения в лог по нажатию кнопки
    или по нажатию клавиши Enter на поле ввода сообщения;
 3. Продублировать импровизированный лог (историю) чата в файле;
 4. При запуске клиента чата заполнять поле истории из файла,
    если он существует. Обратите внимание,
    что чаще всего история сообщений хранится на сервере
    и заполнение истории чата лучше делать при соединении с сервером,
    а не при открытии окна клиента.
 */

public class Main {
    public static void main(String[] args) throws IOException {

        List<String> chatLog = null;
        String chatFile = "chat.log";
        if (Path.of(chatFile).toFile().exists())
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader( new FileInputStream(chatFile), StandardCharsets.UTF_8 ) )
                ) {
                chatLog = br.lines().collect(Collectors.toList());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        FileWriter fw = new FileWriter(chatFile, StandardCharsets.UTF_8, true);
        Consumer<String> writeToFileLog = s-> {
            try {
                fw.append(s+"\n");
                fw.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        FrameServer frameServer = new FrameServer(chatLog);
        FrameClient frameClient = new FrameClient(chatLog, frameServer::addLog, writeToFileLog);

        System.out.println("Hello world!");
    }
}