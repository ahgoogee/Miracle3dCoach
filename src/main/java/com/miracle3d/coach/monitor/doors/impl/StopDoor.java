package com.miracle3d.coach.monitor.doors.impl;

import com.miracle3d.coach.monitor.runtime.ICoachRuntime;
import com.miracle3d.coach.monitor.doors.ChannelDataBuffer;
import com.miracle3d.coach.monitor.doors.LockState;

/**
 * 结束门,用来重置门状态,准备下一次测试
 */
public class StopDoor extends LockedDoor{
    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        dataBuffer.resetCurrentNum();
        dataBuffer.setRunning(false);
        dataBuffer.call(compute(dataBuffer));
        dataBuffer.setTotalFitness(0.0f);

        return LockState.UNLOCKED;
    }

    private float compute(ChannelDataBuffer dataBuffer){
        return dataBuffer.getTotalFitness()/dataBuffer.getTotalNum();
    }
}
