package monitor.msgparser;

import base.observer.IObserver;
import utils.scene.impl.SceneGraph;

public interface IMonitorMessageParser extends IObserver<byte[]> {
    ISimulationState getSimulationState();
    SceneGraph getSceneGraph();
}
