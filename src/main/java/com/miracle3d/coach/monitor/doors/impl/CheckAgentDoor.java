package com.miracle3d.coach.monitor.doors.impl;

import com.miracle3d.coach.monitor.doors.ChannelDataBuffer;
import com.miracle3d.coach.monitor.doors.LockState;
import com.miracle3d.coach.monitor.enums.PlaySide;
import com.miracle3d.coach.monitor.runtime.ICoachRuntime;
import com.miracle3d.coach.monitor.worldmodel.ISoccerAgent;

public class CheckAgentDoor extends LockedDoor{

    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        if(unlocked)
            return LockState.UNLOCKED;

        if(coachRuntime.getWorldModel().getSoccerAgents().isEmpty())
            return LockState.LOCKED;

        ISoccerAgent agent = coachRuntime.getWorldModel().getSoccerAgents().get(0);
        if(agent.getPosition().getZ()<0.15f)
        {
            coachRuntime.getCommander().movePlayer(PlaySide.LEFT,agent.getPlayerID(),-1f,0f,0f);
            return LockState.LOCKED;
        }

        return LockState.UNLOCKED;
    }
}
