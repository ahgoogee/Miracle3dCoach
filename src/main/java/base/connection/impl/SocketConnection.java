package base.connection.impl;

public abstract class SocketConnection extends ConnectionBase {
    protected final String host;
    protected final int port;

    public SocketConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String toString() {
        return this.host + ":" + this.port;
    }
}
