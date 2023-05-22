package base.connection;

import base.observer.ISubscribe;

public interface IServerConnection extends ISubscribe<byte[]> {
    void establishConnection() throws ConnectionException;

    void sendMessage(byte[] var1) throws ConnectionException;

    void startReceiveLoop() throws ConnectionException;

    void stopReceiveLoop();

    boolean isConnected();
}
