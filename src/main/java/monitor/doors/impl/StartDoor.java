package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;

/**
 * 控制教练何时开始测试,用来同步外部调用
 */
public class StartDoor extends LockedDoor{
    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        if(unlocked)
            return LockState.UNLOCKED;

        if(dataBuffer.isRunning())
        {
            unlock();
            return LockState.UNLOCKED;
        }


        return LockState.LOCKED;
    }
}
