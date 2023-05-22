package commander;

import base.connection.ConnectionException;
import base.connection.IServerConnection;
import base.connection.impl.ServerConnection;
import base.observer.IObserver;
import monitor.command.ServerCommander;
import monitor.enums.PlayMode;
import monitor.msgparser.IMonitorMessageParser;
import monitor.msgparser.impl.MonitorMessageParser;
import monitor.worldmodel.IMonitorWorldModel;
import monitor.worldmodel.impl.MonitorWorldModel;

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
