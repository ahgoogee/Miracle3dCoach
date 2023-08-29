package com.miracle3d.coach.monitor.runtime.impl;

import com.miracle3d.coach.api.API;
import com.miracle3d.coach.base.connection.ConnectionException;
import com.miracle3d.coach.base.connection.IServerConnection;
import com.miracle3d.coach.base.connection.impl.ServerConnection;
import com.miracle3d.coach.base.observer.IObserver;
import com.miracle3d.coach.monitor.benchmarks.IUpdateListener;
import com.miracle3d.coach.monitor.msgparser.IMonitorMessageParser;
import com.miracle3d.coach.monitor.msgparser.impl.MonitorMessageParser;
import com.miracle3d.coach.monitor.runtime.ICoachRuntime;
import com.miracle3d.coach.monitor.worldmodel.IMonitorWorldModel;
import com.miracle3d.coach.monitor.worldmodel.impl.MonitorWorldModel;
import com.miracle3d.coach.monitor.command.ServerCommander;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoachRuntime implements IObserver<byte[]> , ICoachRuntime,Runnable {
    public CoachRuntime(String host,Integer port){
        connection = new ServerConnection(host,port);
        connection.attach(this);
        commander = new ServerCommander(connection);
        worldModel = new MonitorWorldModel();
        parser = new MonitorMessageParser();
        listeners = new ArrayList<>();
    }


    private final IServerConnection connection;
    private final ServerCommander commander;
    private final IMonitorWorldModel worldModel;
    private final IMonitorMessageParser parser;
    private Thread thread;

    private final List<IUpdateListener> listeners;
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
    public void startConnection() {
        if (Objects.isNull(thread)) {
            thread = new Thread(this,"coach");
            thread.start();
        }
    }

    @Override
    public void stopConnection() {
        connection.stopReceiveLoop();
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
    public void run() throws RuntimeException{
        try {
            connection.establishConnection();
            connection.startReceiveLoop();
        } catch (ConnectionException e) {
            throw new RuntimeException(e);
        }
    }
}
