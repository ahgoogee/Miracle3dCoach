package monitor.msgparser;

import monitor.enums.Foul;

import java.util.List;

public interface ISimulationState {
    Float getFieldLength();

    Float getFieldWidth();

    Float getFieldHeight();

    Float getGoalWidth();

    Float getGoalDepth();

    Float getGoalHeight();

    Float getBorderSize();

    Float getFreeKickDistance();

    Float getWaitBeforeKickOff();

    Float getAgentRadius();

    Float getBallRadius();

    Float getBallMass();

    Float getRuleGoalPauseTime();

    Float getRuleKickInPauseTime();

    Float getRuleHalfTime();

    String[] getPlayModes();

    Float getTime();

    String getLeftTeam();

    String getRightTeam();

    Integer getHalf();

    Integer getLeftScore();

    Integer getRightScore();

    Integer getPlayMode();

    List<Foul> getFouls();

    Integer getPassModeMinOppBallDist();

    Integer getPassModeDuration();

}
