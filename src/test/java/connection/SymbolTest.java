package connection;

import com.miracle3d.coach.base.connection.ConnectionException;
import com.miracle3d.coach.base.connection.IServerConnection;
import com.miracle3d.coach.base.connection.impl.ServerConnection;
import com.miracle3d.coach.base.observer.IObserver;
import com.miracle3d.coach.base.symbol.SymbolNode;
import com.miracle3d.coach.base.symbol.SymbolTreeParser;

import java.io.UnsupportedEncodingException;

public class SymbolTest {
    public static void main(String[] args) {
        IServerConnection connection = new ServerConnection("localhost",3200);
        connection.attach(new SymbolRuntimeTest());
        try {
            connection.establishConnection();
            connection.startReceiveLoop();
        } catch (ConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    public static class SymbolRuntimeTest implements IObserver<byte[]> {
        private SymbolTreeParser parser = new SymbolTreeParser();
        @Override
        public void update(byte[] var1) {
            String content = null;
            try {
                content = new String(var1, 0, var1.length, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            SymbolNode root = parser.parse(content);
        }
    }

}
