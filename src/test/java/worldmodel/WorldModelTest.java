package worldmodel;

import base.connection.ConnectionException;
import base.connection.IServerConnection;
import base.connection.impl.ServerConnection;
import base.observer.IObserver;
import connection.SimStateTest;
import monitor.msgparser.IMonitorMessageParser;
import monitor.msgparser.impl.MonitorMessageParser;
import monitor.worldmodel.IMonitorWorldModel;
import monitor.worldmodel.impl.MonitorWorldModel;

public class WorldModelTest {
    public static void main(String[] args) {
        IServerConnection connection = new ServerConnection("localhost",3200);
        connection.attach(new RuntimeTest());
        try {
            connection.establishConnection();
            connection.startReceiveLoop();
        } catch (ConnectionException e) {
            throw new RuntimeException(e);
        }
    }
    private static class RuntimeTest implements IObserver<byte[]> {
        private IMonitorWorldModel worldModel = new MonitorWorldModel();
        private IMonitorMessageParser parser = new MonitorMessageParser();

        @Override
        public void update(byte[] var1) {
            parser.update(var1);
            worldModel.update(parser);
        }
    }

}
