package monitor.doors.impl;

import monitor.doors.ILockedDoor;

public abstract class LockedDoor implements ILockedDoor {
    protected boolean unlocked = false;

    @Override
    public void unlock(){unlocked = true;}

    @Override
    public void lock()  {
        unlocked = false;
    }
}
