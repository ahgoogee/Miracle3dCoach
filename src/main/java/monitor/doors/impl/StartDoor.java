package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;

public class StartDoor extends LockedDoor{
    @Override
    public LockState unlock(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        if(dataBuffer.isRunning())
            return LockState.UNLOCKED;

        return LockState.LOCKED;
    }
}
