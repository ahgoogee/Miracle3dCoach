package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.ILockedDoor;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;

public abstract class LockedDoor implements ILockedDoor {
    protected boolean unlocked = false;

    @Override
    public void unlock(){unlocked = true;}

    @Override
    public void lock()  {
        unlocked = false;
    }
}
