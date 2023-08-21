package com.miracle3d.coach.monitor.doors.impl;

import com.miracle3d.coach.monitor.doors.ILockedDoor;

public abstract class LockedDoor implements ILockedDoor {
    protected boolean unlocked = false;

    @Override
    public void unlock(){unlocked = true;}

    @Override
    public void lock()  {
        unlocked = false;
    }
}
