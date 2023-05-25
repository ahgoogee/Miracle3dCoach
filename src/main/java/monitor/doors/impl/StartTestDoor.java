package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.enums.PlayMode;
import monitor.runtime.ICoachRuntime;

public class StartTestDoor extends LockedDoor {
    @Override
    public LockState unlock(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        if(unlocked)
            return LockState.UNLOCKED;

        if(coachRuntime.getWorldModel().getPlayMode() == PlayMode.PLAY_ON)
        {
            unlocked = true;
            return LockState.UNLOCKED;
        }

        coachRuntime.getCommander().setPlaymode(PlayMode.PLAY_ON);

        return LockState.LOCKED;
    }
}
