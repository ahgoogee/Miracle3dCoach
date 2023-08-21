package com.miracle3d.coach.monitor.worldmodel.impl;

import com.miracle3d.coach.monitor.worldmodel.ISoccerBall;
import com.miracle3d.coach.monitor.enums.Foul;
import com.miracle3d.coach.monitor.enums.PlayMode;
import com.miracle3d.coach.monitor.msgparser.IMonitorMessageParser;
import com.miracle3d.coach.monitor.msgparser.ISimulationState;
import com.miracle3d.coach.monitor.worldmodel.IMonitorWorldModel;
import com.miracle3d.coach.monitor.worldmodel.ISoccerAgent;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import com.miracle3d.coach.utils.scene.IBaseNode;
import com.miracle3d.coach.utils.scene.IMeshNode;
import com.miracle3d.coach.utils.scene.ISceneGraph;
import com.miracle3d.coach.utils.scene.impl.SceneGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MonitorWorldModel implements IMonitorWorldModel {
    protected SceneGraph sceneGraph = null;
    protected boolean graphStructureChanged;
    protected Vector3D fieldDimensions;
    protected Vector3D goalDimensions;
    protected float time;
    protected float deltaT;
    protected String leftTeam;
    protected String rightTeam;
    protected int scoreLeft;
    protected int scoreRight;
    protected PlayMode playMode;
    protected int half;
    protected SoccerBall ball;
    protected ArrayList<SoccerAgent> agents;
    protected ArrayList<Foul> fouls;

    public MonitorWorldModel() {
        this.fieldDimensions = Vector3D.ZERO;
        this.goalDimensions = Vector3D.ZERO;
        this.leftTeam = "";
        this.rightTeam = "";
        this.ball = new SoccerBall();
        this.agents = new ArrayList();
        this.fouls = new ArrayList();
    }

    public void update(IMonitorMessageParser content) {
        ISimulationState stateUpdate = content.getSimulationState();
        ISceneGraph sceneGraphUpdate = content.getSceneGraph();
        if (stateUpdate != null) {
            this.updateState(stateUpdate);
        }

        if (sceneGraphUpdate != null) {
            if ("RSG".equals(sceneGraphUpdate.getHeader().getType())) {
                this.sceneGraph = new SceneGraph(sceneGraphUpdate);
                this.createSimulationObjects();
                this.graphStructureChanged = true;
            } else if (this.sceneGraph != null) {
                this.sceneGraph.update(sceneGraphUpdate);
                this.refreshSimulationObjects();
                this.graphStructureChanged = false;
            }
        }

    }

    private void updateState(ISimulationState stateUpdate) {
        Float fieldLength = stateUpdate.getFieldLength();
        Float fieldWidth = stateUpdate.getFieldWidth();
        Float fieldHeight = stateUpdate.getFieldHeight();
        if (fieldLength != null && fieldWidth != null && fieldHeight != null) {
            this.fieldDimensions = new Vector3D((double)fieldLength, (double)fieldWidth, (double)fieldHeight);
        }

        Float goalDepth = stateUpdate.getGoalDepth();
        Float goalWidth = stateUpdate.getGoalWidth();
        Float goalHeight = stateUpdate.getGoalHeight();
        if (goalDepth != null && goalWidth != null && goalHeight != null) {
            this.goalDimensions = new Vector3D((double)goalDepth, (double)goalWidth, (double)goalHeight);
        }

        Float newTime = stateUpdate.getTime();
        if (newTime != null) {
            this.deltaT = newTime - this.time;
            this.time = newTime;
        }

        String newLeftTeam = stateUpdate.getLeftTeam();
        if (newLeftTeam != null) {
            this.leftTeam = newLeftTeam;
        }

        String newRightTeam = stateUpdate.getRightTeam();
        if (newRightTeam != null) {
            this.rightTeam = newRightTeam;
        }

        Integer newScoreLeft = stateUpdate.getLeftScore();
        if (newScoreLeft != null) {
            this.scoreLeft = newScoreLeft;
        }

        Integer newScoreRight = stateUpdate.getRightScore();
        if (newScoreRight != null) {
            this.scoreRight = newScoreRight;
        }

        Integer newPlayMode = stateUpdate.getPlayMode();
        if (newPlayMode != null) {
            if (newPlayMode >= 0 && newPlayMode < PlayMode.values().length) {
                this.playMode = PlayMode.values()[newPlayMode];
            } else {
                this.playMode = PlayMode.NONE;
            }
        }

        Integer newHalf = stateUpdate.getHalf();
        if (newHalf != null) {
            this.half = newHalf;
        }

        this.fouls.addAll(stateUpdate.getFouls());
    }

    private void createSimulationObjects() {
        ArrayList<IBaseNode> topLevelNodes = this.sceneGraph.getRootNode().getChildren();
        if (topLevelNodes != null && topLevelNodes.size() >= 36) {
            this.ball.setGraphRoot((IBaseNode)topLevelNodes.get(35));
            ArrayList<SoccerAgent> newAgents = new ArrayList();

            for(int i = 36; i < topLevelNodes.size(); ++i) {
                IBaseNode currentNode = (IBaseNode)topLevelNodes.get(i);
                if (currentNode.getChildren() != null && currentNode.getChildren().size() > 0) {
                    String teamName;
                    if (currentNode.getNode(IMeshNode.class, "materials", "matLeft") != null) {
                        teamName = this.leftTeam;
                    } else {
                        teamName = this.rightTeam;
                    }

                    newAgents.add(new SoccerAgent(currentNode, teamName));
                }
            }

            this.agents = newAgents;
        } else {
            System.err.println("Unexpected number of top level nodes in scene graph");
        }
    }

    private void refreshSimulationObjects() {
        this.ball.refresh(this.deltaT);
        Iterator var1 = this.agents.iterator();

        while(var1.hasNext()) {
            SoccerAgent agent = (SoccerAgent)var1.next();
            agent.refresh(this.deltaT);
        }

    }

    public SceneGraph getSceneGraph() {
        return this.sceneGraph;
    }

    public boolean hasSceneGraphStructureChanged() {
        return this.graphStructureChanged;
    }

    public Vector3D getFieldDimensions() {
        return this.fieldDimensions;
    }

    public Vector3D getGoalDimensions() {
        return this.goalDimensions;
    }

    public float getTime() {
        return this.time;
    }

    public String getLeftTeamName() {
        return this.leftTeam;
    }

    public String getRightTeamName() {
        return this.rightTeam;
    }

    public int getScoreLeft() {
        return this.scoreLeft;
    }

    public int getScoreRight() {
        return this.scoreRight;
    }

    public PlayMode getPlayMode() {
        return this.playMode;
    }

    public int getHalf() {
        return this.half;
    }

    public ISoccerBall getBall() {
        return this.ball;
    }

    public List<? extends ISoccerAgent> getSoccerAgents() {
        return this.agents;
    }

    public List<Foul> getFouls() {
        return this.fouls;
    }
}
