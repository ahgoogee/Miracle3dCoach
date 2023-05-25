package monitor.runtime;

import base.connection.IServerConnection;
import monitor.benchmarks.IUpdateListener;
import monitor.command.ServerCommander;
import monitor.worldmodel.IMonitorWorldModel;

public interface ICoachRuntime {

    IServerConnection getConnection();
    ServerCommander getCommander();
    IMonitorWorldModel getWorldModel();

    void addUpdateListener(IUpdateListener listener);
    void startConnection();
    void stopConnection();
}
