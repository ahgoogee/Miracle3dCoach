package com.miracle3d.coach.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    void connect(){
        String serverAddress = "127.0.0.1";  // 服务器地址
        int serverPort = 1234;              // 服务器端口

        try {
            // 创建Socket对象，连接到服务器
            Socket socket = new Socket(serverAddress, serverPort);

            // 获取输入输出流
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 发送消息
            String messageToSend = "Hello, server!";
            out.println(messageToSend);

            out.println("stop");


            // 关闭连接
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client().connect();
    }
}
