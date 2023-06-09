package connection;

import base.connection.ConnectionException;
import base.connection.IServerConnection;
import base.connection.impl.ServerConnection;
import base.observer.IObserver;
import base.symbol.SymbolTreeParser;

import java.io.UnsupportedEncodingException;

public class ConnectionTest {
    public static void main(String[] args) {
        IServerConnection connection = new ServerConnection("localhost",3200);

        //附加观察者
        connection.attach(new RuntimeTest());
        try {
            //建立连接
            connection.establishConnection();
            //开启循环
            connection.startReceiveLoop();
        } catch (ConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    public static class RuntimeTest implements IObserver<byte[]>{
        //服务器只会发送更新信息,如果没有数据变化,则发送'nd'
        @Override
        public void update(byte[] var1) {
            String content = null;
            try {
                content = new String(var1, 0, var1.length, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            System.out.println(content);
            System.out.println("////////////////////////////////////////////////////////////////////////////////");
        }
    }
}
