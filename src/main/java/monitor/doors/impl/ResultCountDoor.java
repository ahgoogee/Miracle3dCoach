package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.ILockedDoor;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;

public class ResultCountDoor extends LockedDoor{
    @Override
    public LockState unlock(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {

        dataBuffer.addCurrent();
        dataBuffer.lockDisposableLock();

        if(dataBuffer.getCurrentTestNum() >= dataBuffer.getTotalTestNum())
        {
            return LockState.UNLOCKED;
        }

        return LockState.LOCKED;
    }
}
