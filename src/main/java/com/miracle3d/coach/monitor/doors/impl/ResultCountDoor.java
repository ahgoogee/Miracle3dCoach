package com.miracle3d.coach.monitor.doors.impl;

import com.miracle3d.coach.monitor.runtime.ICoachRuntime;
import com.miracle3d.coach.monitor.doors.ChannelDataBuffer;
import com.miracle3d.coach.monitor.doors.LockState;

/**
 * 结果统计门
 */
public class ResultCountDoor extends LockedDoor{
    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        dataBuffer.addCurrentNum();
        dataBuffer.lock();

        if(dataBuffer.getCurrentNum() >= dataBuffer.getTotalNum())
        {
            return LockState.UNLOCKED;
        }

        return LockState.LOCKED;
    }
}
