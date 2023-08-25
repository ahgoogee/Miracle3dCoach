package com.miracle3d.coach.monitor.doors.impl;

import com.miracle3d.coach.monitor.doors.ChannelDataBuffer;
import com.miracle3d.coach.monitor.doors.LockState;
import com.miracle3d.coach.monitor.enums.PlaySide;
import com.miracle3d.coach.monitor.runtime.ICoachRuntime;
import com.miracle3d.coach.monitor.worldmodel.ISoccerAgent;

public class CheckAgentDoor extends LockedDoor{
    private int localDelay = 40;

    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        if(unlocked)
            return LockState.UNLOCKED;

        if(coachRuntime.getWorldModel().getSoccerAgents().isEmpty())
            return LockState.LOCKED;

        if(localDelay > 0){
            localDelay--;
            return LockState.LOCKED;
        }

        return LockState.UNLOCKED;
    }
}
