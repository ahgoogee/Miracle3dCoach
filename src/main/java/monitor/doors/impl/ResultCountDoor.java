package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;

public class ResultCountDoor extends LockedDoor{
    @Override
    public LockState unlock(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        dataBuffer.addCurrentNum();
        dataBuffer.lock();

        if(dataBuffer.getCurrentNum() >= dataBuffer.getTotalNum())
        {
            return LockState.UNLOCKED;
        }

        return LockState.LOCKED;
    }
}
