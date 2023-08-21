# 连接模块


    //创建连接,需要服务器地址和端口
    IServerConnection connection = new ServerConnection("localhost",3200);

    //附加观察者(实现IObserver<byte[]>接口)
    connection.attach(new RuntimeTest());

    try {
        //建立连接
        connection.establishConnection();
        //开启循环
        connection.startReceiveLoop();
    } catch (ConnectionException e) {
        throw new RuntimeException(e);
    }