package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;
public class SingleTestDoor extends LockedDoor {
    @Override
    public LockState unlock(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {

        if(coachRuntime.getWorldModel().getTime() >= dataBuffer.getSingleTime())
        {
            //假设机器人行走5m
            Float distance = 5.0f;
            dataBuffer.setTotalFitness(dataBuffer.getTotalFitness()+distance);
            return LockState.UNLOCKED;
        }

        return LockState.LOCKED;
    }
}
