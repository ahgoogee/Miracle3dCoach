package monitor.runtime.impl;

import base.connection.ConnectionException;
import base.connection.IServerConnection;
import base.connection.impl.ServerConnection;
import base.observer.IObserver;
import monitor.benchmarks.IUpdateListener;
import monitor.command.ServerCommander;
import monitor.msgparser.IMonitorMessageParser;
import monitor.msgparser.impl.MonitorMessageParser;
import monitor.runtime.ICoachRuntime;
import monitor.worldmodel.IMonitorWorldModel;
import monitor.worldmodel.impl.MonitorWorldModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoachRuntime implements IObserver<byte[]> , ICoachRuntime,Runnable {
    private static CoachRuntime instance;
    public static CoachRuntime getInstance(){

        if(Objects.isNull(instance))
            return null;
        return instance;

    }
    public static CoachRuntime getInstance(String host,Integer port){

        if (Objects.isNull(instance))
            instance = new CoachRuntime(host, port);
        return instance;

    }
    private CoachRuntime(String host,Integer port){
        connection = new ServerConnection(host,port);
        connection.attach(this);
        commander = new ServerCommander(connection);
        worldModel = new MonitorWorldModel();
        parser = new MonitorMessageParser();
        listeners = new ArrayList<>();
    }


    private IServerConnection connection;
    private ServerCommander commander;
    private IMonitorWorldModel worldModel;
    private IMonitorMessageParser parser;
    private Thread thread;

    private List<IUpdateListener> listeners;
    public IServerConnection getConnection() {return connection;}

    public ServerCommander getCommander() {
        return commander;
    }

    public IMonitorWorldModel getWorldModel() {return worldModel;}



    @Override
    public void addUpdateListener(IUpdateListener listener) {
        listeners.add(listener);
    }

    @Override
    public void startCoach() {
        if (Objects.isNull(thread)) {
            thread = new Thread(this,"coach");
            thread.start();
        }
    }

    @Override
    public void update(byte[] var1) {
        parser.update(var1);
        worldModel.update(parser);
        listeners.forEach(IUpdateListener::update);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            connection.establishConnection();
            connection.startReceiveLoop();
        } catch (ConnectionException e) {
            throw new RuntimeException(e);
        }
    }
}
