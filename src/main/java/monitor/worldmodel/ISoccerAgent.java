package monitor.worldmodel;

import base.geometry.Pose3D;

public interface ISoccerAgent extends ISimulationObject {
    String getTeamName();

    int getPlayerID();

    Pose3D getBodyPartPose(SoccerAgentBodyPart var1);

    String getRobotModel();
}