package monitor.doors;

import monitor.runtime.ICoachRuntime;

public interface ILockedDoor {
    LockState unlock(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime);
    void lock();

    void unlock();
}
