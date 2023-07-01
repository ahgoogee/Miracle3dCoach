package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;

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
