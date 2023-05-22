package base.connection.impl;


import base.connection.IServerConnection;
import base.observer.IObserver;
import base.observer.IPublishSubscribe;
import base.observer.Subject;

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