package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.enums.PlayMode;
import monitor.runtime.ICoachRuntime;

/**
 * 初始化门,用于重置比赛状态
 */
public class InitDoor extends LockedDoor {
    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        //只需要开一次
        if(unlocked)
            return LockState.UNLOCKED;

        //检查是否满足开锁条件
        if(PlayMode.BEFORE_KICK_OFF == coachRuntime.getWorldModel().getPlayMode())
        {
            unlock();
            return LockState.UNLOCKED;
        }

        initState(coachRuntime);

        return LockState.LOCKED;
    }

    private void initState(ICoachRuntime coachRuntime){
        coachRuntime.getCommander().setPlaymode(PlayMode.BEFORE_KICK_OFF);
        coachRuntime.getCommander().setTime(0.0f);
    }
}
