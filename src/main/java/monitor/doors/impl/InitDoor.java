package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.enums.PlayMode;
import monitor.runtime.ICoachRuntime;

public class InitDoor extends LockedDoor {
    @Override
    public LockState unlock(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        if(unlocked)
            return LockState.UNLOCKED;

        PlayMode playMode = coachRuntime.getWorldModel().getPlayMode();
        if(playMode == PlayMode.BEFORE_KICK_OFF)
        {
            unlock();
            return LockState.UNLOCKED;
        }

        coachRuntime.getCommander().setPlaymode(PlayMode.BEFORE_KICK_OFF);
        coachRuntime.getCommander().setTime(0.0f);

        return LockState.LOCKED;
    }
}
