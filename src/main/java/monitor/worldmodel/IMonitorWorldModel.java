package monitor.worldmodel;

import base.observer.IObserver;
import monitor.enums.Foul;
import monitor.enums.PlayMode;
import monitor.msgparser.IMonitorMessageParser;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import utils.scene.impl.SceneGraph;

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
