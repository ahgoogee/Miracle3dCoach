package com.miracle3d.coach.monitor.msgparser;

import com.miracle3d.coach.base.observer.IObserver;
import com.miracle3d.coach.utils.scene.impl.SceneGraph;

public interface IMonitorMessageParser extends IObserver<byte[]> {
    ISimulationState getSimulationState();
    SceneGraph getSceneGraph();
}
