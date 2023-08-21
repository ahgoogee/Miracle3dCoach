package com.miracle3d.coach.base.connection.impl;


import com.miracle3d.coach.base.connection.IServerConnection;
import com.miracle3d.coach.base.observer.IObserver;
import com.miracle3d.coach.base.observer.IPublishSubscribe;
import com.miracle3d.coach.base.observer.Subject;

public abstract class ConnectionBase implements IServerConnection {
    protected final IPublishSubscribe<byte[]> observer = new Subject();
    protected boolean shutDown = false;
    protected boolean connected = false;

    public ConnectionBase() {
    }

    public void stopReceiveLoop() {
        this.shutDown = true;
    }

    public void attach(IObserver<byte[]> newObserver) {
        this.observer.attach(newObserver);
    }

    public boolean isConnected() {
        return this.connected;
    }
}