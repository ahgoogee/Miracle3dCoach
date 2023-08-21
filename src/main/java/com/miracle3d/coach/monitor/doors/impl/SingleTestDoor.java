package com.miracle3d.coach.monitor.doors.impl;

import com.miracle3d.coach.monitor.runtime.ICoachRuntime;
import com.miracle3d.coach.monitor.doors.ChannelDataBuffer;
import com.miracle3d.coach.monitor.doors.LockState;


/**
 * 正式测试门,判断何时结束测试
 */
public class SingleTestDoor extends LockedDoor {
    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        if(checkState(dataBuffer,coachRuntime))
        {
            Float distance = getAgentScore(coachRuntime);
            //System.out.println("--distance:"+distance);
            dataBuffer.setTotalFitness(dataBuffer.getTotalFitness()+distance);
            return LockState.UNLOCKED;
        }

        return LockState.LOCKED;
    }

    private boolean checkState(ChannelDataBuffer dataBuffer,ICoachRuntime coachRuntime)
    {
        return coachRuntime.getWorldModel().getTime() >= dataBuffer.getSingleTime();
    }

    private float getAgentScore(ICoachRuntime coachRuntime){
        return (float) coachRuntime.getWorldModel().getBall().getPosition().getX();
    }
}
