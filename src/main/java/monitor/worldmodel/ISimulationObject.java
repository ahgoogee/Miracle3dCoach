package monitor.worldmodel;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import utils.scene.IBaseNode;

public interface ISimulationObject {
    Vector3D getPosition();

    IBaseNode getGraphRoot();
}
