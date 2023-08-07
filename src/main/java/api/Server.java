package api;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private Socket socket;
    private Reader reader;
    private PrintWriter printWriter;
    private String stopSymbol = "$";

    public void start(Integer port,String stopSymbol){
        this.stopSymbol = stopSymbol;
        start(port);
    }
    public void start(Integer port){

        try {
            server = new ServerSocket(port);
            System.out.println("开始监听,端口:"+port);
            socket = server.accept();
            System.out.println("已连接");

            reader = new InputStreamReader(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream());

            String msg;
            while (!(msg=listen()).equals("stop"))
                processMsg(msg);

            stop();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try{
            System.out.println("结束监听");
            reader.close();
            printWriter.close();
            socket.close();
            server.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String listen(){
        try {
            StringBuilder builder = new StringBuilder();
            char[] chars = new char[1024];
            int len;

            do {
                while ((len = reader.read(chars))==-1);
                String temp = new String(chars, 0, len);
                builder.append(temp);
            } while (!(builder.length() >0 && builder.substring(builder.length() - 1, builder.length()).equals(stopSymbol)));
            return builder.substring(0,builder.length()-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "stop";
    }

    private void processMsg(String msg){
        System.out.println(msg);
    }


    public static void main(String[] args) {
        Server s = new Server();
        s.start(3698);
    }
}
