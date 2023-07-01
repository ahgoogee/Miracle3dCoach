package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.enums.PlayMode;
import monitor.runtime.ICoachRuntime;

/**
 * 开始测试门,用来进行测试前准备工作
 */
public class StartTestDoor extends LockedDoor {
    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        if(unlocked)
            return LockState.UNLOCKED;

        if( PlayMode.PLAY_ON == coachRuntime.getWorldModel().getPlayMode())
        {
            unlock();
            return LockState.UNLOCKED;
        }

        prepare(coachRuntime);

        return LockState.LOCKED;
    }

    private void prepare(ICoachRuntime coachRuntime){
        coachRuntime.getCommander().setPlaymode(PlayMode.PLAY_ON);
    }
}
