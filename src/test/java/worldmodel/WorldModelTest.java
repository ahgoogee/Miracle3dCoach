package worldmodel;

import base.connection.ConnectionException;
import base.connection.IServerConnection;
import base.connection.impl.ServerConnection;
import base.observer.IObserver;
import monitor.enums.PlayMode;
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
        private final IMonitorWorldModel worldModel = new MonitorWorldModel();
        private final IMonitorMessageParser parser = new MonitorMessageParser();

        private PlayMode lastPlayMode = PlayMode.NONE;

        @Override
        public void update(byte[] var1) {
            parser.update(var1);
            worldModel.update(parser);

            //测试比赛模式读取
            if(worldModel.getPlayMode() != lastPlayMode)
                System.out.println(worldModel.getPlayMode());
            lastPlayMode = worldModel.getPlayMode();
        }
    }

}
