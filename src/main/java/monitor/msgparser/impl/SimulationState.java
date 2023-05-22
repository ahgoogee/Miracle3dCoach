package monitor.msgparser.impl;

import monitor.msgparser.ISimulationState;
import monitor.enums.Foul;

import java.util.ArrayList;
import java.util.List;

public class SimulationState implements ISimulationState {
    private Float fieldLength;
    private Float fieldWidth;
    private Float fieldHeight;
    private Float goalWidth;
    private Float goalDepth;
    private Float goalHeight;
    private Float borderSize;
    private Float freeKickDistance;
    private Float waitBeforeKickOff;
    private Float agentRadius;
    private Float ballRadius;
    private Float ballMass;
    private Float ruleGoalPauseTime;
    private Float ruleKickInPauseTime;
    private Float ruleHalfTime;
    private String[] playModes;
    private Float time;
    private String leftTeam;
    private String rightTeam;
    private Integer half;
    private Integer leftScore;
    private Integer rightScore;
    private Integer playMode;
    private List<Foul> fouls = new ArrayList();

    public SimulationState() {
    }

    public Float getFieldLength() {
        return this.fieldLength;
    }

    public Float getFieldWidth() {
        return this.fieldWidth;
    }

    public Float getFieldHeight() {
        return this.fieldHeight;
    }

    public Float getGoalWidth() {
        return this.goalWidth;
    }

    public Float getGoalDepth() {
        return this.goalDepth;
    }

    public Float getGoalHeight() {
        return this.goalHeight;
    }

    public Float getBorderSize() {
        return this.borderSize;
    }

    public Float getFreeKickDistance() {
        return this.freeKickDistance;
    }

    public Float getWaitBeforeKickOff() {
        return this.waitBeforeKickOff;
    }

    public Float getAgentRadius() {
        return this.agentRadius;
    }

    public Float getBallRadius() {
        return this.ballRadius;
    }

    public Float getBallMass() {
        return this.ballMass;
    }

    public Float getRuleGoalPauseTime() {
        return this.ruleGoalPauseTime;
    }

    public Float getRuleKickInPauseTime() {
        return this.ruleKickInPauseTime;
    }

    public Float getRuleHalfTime() {
        return this.ruleHalfTime;
    }

    public String[] getPlayModes() {
        return this.playModes;
    }

    public Float getTime() {
        return this.time;
    }

    public String getLeftTeam() {
        return this.leftTeam;
    }

    public String getRightTeam() {
        return this.rightTeam;
    }

    public Integer getHalf() {
        return this.half;
    }

    public Integer getLeftScore() {
        return this.leftScore;
    }

    public Integer getRightScore() {
        return this.rightScore;
    }

    public Integer getPlayMode() {
        return this.playMode;
    }

    public List<Foul> getFouls() {
        return this.fouls;
    }

    public void setFieldLength(Float fieldLength) {
        this.fieldLength = fieldLength;
    }

    public void setFieldWidth(Float fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public void setFieldHeight(Float fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public void setGoalWidth(Float goalWidth) {
        this.goalWidth = goalWidth;
    }

    public void setGoalDepth(Float goalDepth) {
        this.goalDepth = goalDepth;
    }

    public void setGoalHeight(Float goalHeight) {
        this.goalHeight = goalHeight;
    }

    public void setBorderSize(Float borderSize) {
        this.borderSize = borderSize;
    }

    public void setFreeKickDistance(Float freeKickDistance) {
        this.freeKickDistance = freeKickDistance;
    }

    public void setWaitBeforeKickOff(Float waitBeforeKickOff) {
        this.waitBeforeKickOff = waitBeforeKickOff;
    }

    public void setAgentRadius(Float agentRadius) {
        this.agentRadius = agentRadius;
    }

    public void setBallRadius(Float ballRadius) {
        this.ballRadius = ballRadius;
    }

    public void setBallMass(Float ballMass) {
        this.ballMass = ballMass;
    }

    public void setRuleGoalPauseTime(Float ruleGoalPauseTime) {
        this.ruleGoalPauseTime = ruleGoalPauseTime;
    }

    public void setRuleKickInPauseTime(Float ruleKickInPauseTime) {
        this.ruleKickInPauseTime = ruleKickInPauseTime;
    }

    public void setRuleHalfTime(Float ruleHalfTime) {
        this.ruleHalfTime = ruleHalfTime;
    }

    public void setPlayModes(String[] playModes) {
        this.playModes = playModes;
    }

    public void setTime(Float time) {
        this.time = time;
    }

    public void setLeftTeam(String leftTeamName) {
        this.leftTeam = leftTeamName;
    }

    public void setRightTeam(String rightTeamName) {
        this.rightTeam = rightTeamName;
    }

    public void setHalf(Integer half) {
        this.half = half;
    }

    public void setLeftScore(Integer leftScore) {
        this.leftScore = leftScore;
    }

    public void setRightScore(Integer rightScore) {
        this.rightScore = rightScore;
    }

    public void setPlayMode(Integer playMode) {
        this.playMode = playMode;
    }

    public void addFoul(Foul foul) {
        this.fouls.add(foul);
    }
}
