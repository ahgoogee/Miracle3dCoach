package com.miracle3d.coach.monitor.worldmodel;

import com.miracle3d.coach.base.observer.IObserver;
import com.miracle3d.coach.monitor.enums.Foul;
import com.miracle3d.coach.monitor.enums.PlayMode;
import com.miracle3d.coach.monitor.msgparser.IMonitorMessageParser;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import com.miracle3d.coach.utils.scene.impl.SceneGraph;

import java.util.List;

public interface IMonitorWorldModel extends IObserver<IMonitorMessageParser> {
    SceneGraph getSceneGraph();

    boolean hasSceneGraphStructureChanged();

    Vector3D getFieldDimensions();

    Vector3D getGoalDimensions();

    float getTime();

    String getLeftTeamName();

    String getRightTeamName();

    int getScoreLeft();

    int getScoreRight();

    PlayMode getPlayMode();

    int getHalf();

    ISoccerBall getBall();

    List<? extends ISoccerAgent> getSoccerAgents();

    List<Foul> getFouls();
}
