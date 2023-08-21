package com.miracle3d.coach.monitor.worldmodel;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import com.miracle3d.coach.utils.scene.IBaseNode;

public interface ISimulationObject {
    Vector3D getPosition();

    IBaseNode getGraphRoot();
}
