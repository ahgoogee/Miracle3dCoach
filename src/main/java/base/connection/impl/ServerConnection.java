package base.connection.impl;

import base.connection.ConnectionException;

import java.io.IOException;

public class ServerConnection extends TCPConnection {
    private final boolean littleEndian;

    public ServerConnection(String host, int port) {
        this(host, port, true);
    }

    public ServerConnection(String host, int port, boolean littleEndian) {
        super(host, port);
        this.littleEndian = littleEndian;
    }

    public void sendMessage(byte[] msg) throws ConnectionException {
        int len = msg.length;
        int byte0 = len >> 24 & 255;
        int byte1 = len >> 16 & 255;
        int byte2 = len >> 8 & 255;
        int byte3 = len & 255;

        try {
            if (this.littleEndian) {
                this.out.write((byte)byte0);
                this.out.write((byte)byte1);
                this.out.write((byte)byte2);
                this.out.write((byte)byte3);
            } else {
                this.out.write((byte)byte3);
                this.out.write((byte)byte2);
                this.out.write((byte)byte1);
                this.out.write((byte)byte0);
            }

            this.out.write(msg);
            this.out.flush();
        } catch (IOException var8) {
            this.shutDown = true;
            throw new ConnectionException("写入套接字时出错，正在关机...", var8);
        }
    }

    protected byte[] receiveMessage() throws ConnectionException {
        try {
            int byte0 = this.in.read();
            int byte1 = this.in.read();
            int byte2 = this.in.read();
            int byte3 = this.in.read();
            int length;
            if (this.littleEndian) {
                length = byte0 << 24 | byte1 << 16 | byte2 << 8 | byte3;
            } else {
                length = byte3 << 24 | byte2 << 16 | byte1 << 8 | byte0;
            }

            int total = 0;
            if (length < 0) {
                this.shutDown = true;
                throw new ConnectionException("服务器关闭");
            } else {
                byte[] result;
                for(result = new byte[length]; total < length; total += this.in.read(result, total, length - total)) {
                }

                return result;
            }
        } catch (IOException var8) {
            this.shutDown = true;
            throw new ConnectionException("从套接字读取时出错，关闭...", var8);
        }
    }
}
