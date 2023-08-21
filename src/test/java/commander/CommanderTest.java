package commander;

import com.miracle3d.coach.base.connection.ConnectionException;
import com.miracle3d.coach.base.connection.IServerConnection;
import com.miracle3d.coach.base.connection.impl.ServerConnection;
import com.miracle3d.coach.base.observer.IObserver;
import com.miracle3d.coach.monitor.command.ServerCommander;
import com.miracle3d.coach.monitor.enums.PlayMode;
import com.miracle3d.coach.monitor.msgparser.IMonitorMessageParser;
import com.miracle3d.coach.monitor.msgparser.impl.MonitorMessageParser;
import com.miracle3d.coach.monitor.worldmodel.IMonitorWorldModel;
import com.miracle3d.coach.monitor.worldmodel.impl.MonitorWorldModel;

import java.util.Objects;

public class CommanderTest {
    public static void main(String[] args) {

        RuntimeTest runtime = new RuntimeTest();
        runtime.startCoach();
    }
    private static class RuntimeTest implements IObserver<byte[]> {
        private final IServerConnection connection = new ServerConnection("localhost",3200);
        private final ServerCommander commander = new ServerCommander(connection);
        private final IMonitorWorldModel worldModel = new MonitorWorldModel();
        private final IMonitorMessageParser parser = new MonitorMessageParser();

        private PlayMode lastPlayMode = PlayMode.NONE;

        private Float startTime = null;

        RuntimeTest(){
            connection.attach(this);
        }

        public void startCoach(){
            try {
                connection.establishConnection();
                connection.startReceiveLoop();
            } catch (ConnectionException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void update(byte[] var1) {
            parser.update(var1);
            worldModel.update(parser);

            if(worldModel.getPlayMode() != lastPlayMode)
                System.out.println(worldModel.getPlayMode());
            lastPlayMode = worldModel.getPlayMode();

            if(Objects.isNull(startTime))
                startTime = worldModel.getTime();
            else{
                if(worldModel.getTime()-startTime>3.0)
                {
                    System.out.println("trigger");
                    commander.beamBall((float) Math.sin(startTime), (float) Math.cos(startTime));
                    startTime = worldModel.getTime();
                }
            }



        }
    }
}
