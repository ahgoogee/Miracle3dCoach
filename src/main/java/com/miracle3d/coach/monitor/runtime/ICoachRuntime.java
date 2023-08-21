package com.miracle3d.coach.monitor.runtime;

import com.miracle3d.coach.base.connection.IServerConnection;
import com.miracle3d.coach.monitor.benchmarks.IUpdateListener;
import com.miracle3d.coach.monitor.worldmodel.IMonitorWorldModel;
import com.miracle3d.coach.monitor.command.ServerCommander;

public interface ICoachRuntime {

    IServerConnection getConnection();
    ServerCommander getCommander();
    IMonitorWorldModel getWorldModel();

    void addUpdateListener(IUpdateListener listener);
    void startConnection();
    void stopConnection();
}
