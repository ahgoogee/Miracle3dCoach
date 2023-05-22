package monitor.worldmodel.impl;

import monitor.worldmodel.ISimulationObject;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import utils.scene.IBaseNode;

public abstract class SimulationObject implements ISimulationObject {
    protected IBaseNode graphRoot;
    protected Vector3D position;

    public SimulationObject() {
        this.position = Vector3D.ZERO;
    }

    public SimulationObject(IBaseNode graphRoot) {
        this.graphRoot = graphRoot;
    }

    public void setGraphRoot(IBaseNode graphRoot) {
        this.graphRoot = graphRoot;
        this.refresh(0.0F);
    }

    public IBaseNode getGraphRoot() {
        return this.graphRoot;
    }

    public Vector3D getPosition() {
        return this.position;
    }

    public abstract void refresh(float var1);
}
