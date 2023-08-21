package worldmodel;

import com.miracle3d.coach.base.connection.ConnectionException;
import com.miracle3d.coach.base.connection.IServerConnection;
import com.miracle3d.coach.base.connection.impl.ServerConnection;
import com.miracle3d.coach.base.observer.IObserver;
import com.miracle3d.coach.monitor.enums.PlayMode;
import com.miracle3d.coach.monitor.msgparser.IMonitorMessageParser;
import com.miracle3d.coach.monitor.msgparser.impl.MonitorMessageParser;
import com.miracle3d.coach.monitor.worldmodel.IMonitorWorldModel;
import com.miracle3d.coach.monitor.worldmodel.impl.MonitorWorldModel;

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
