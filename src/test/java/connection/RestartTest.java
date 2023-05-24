package connection;

import base.connection.ConnectionException;
import base.connection.IServerConnection;
import base.connection.impl.ServerConnection;
import base.observer.IObserver;

import java.io.UnsupportedEncodingException;

public class RestartTest {
    public static void main(String[] args) {
        RuntimeTest runtimeTest = new RuntimeTest();

        while (true){
            try {
                runtimeTest.startLoop();
            } catch (ConnectionException e) {
                System.out.println("连接出错");
            }
            if(runtimeTest.connection.isConnected())
                break;
        }

    }

    public static class RuntimeTest implements IObserver<byte[]> {
        public IServerConnection connection;
        RuntimeTest(){
            connection = new ServerConnection("localhost",3200);
            connection.attach(this);
        }

        public void startLoop() throws ConnectionException {
            connection.establishConnection();
            System.out.println("开启循环");
            connection.startReceiveLoop();
        }


        @Override
        public void update(byte[] var1) {
        }
    }
}
