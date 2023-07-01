package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;


/**
 * 正式测试门,判断何时结束测试
 */
public class SingleTestDoor extends LockedDoor {
    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        if(checkState(dataBuffer,coachRuntime))
        {
            Float distance = getAgentScore(coachRuntime);
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
        //假设机器人行走5m
        return 5.0f;
    }
}
