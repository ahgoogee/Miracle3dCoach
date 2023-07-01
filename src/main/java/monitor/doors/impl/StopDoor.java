package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;

/**
 * 结束门,用来重置门状态,准备下一次测试
 */
public class StopDoor extends LockedDoor{
    @Override
    public LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        dataBuffer.resetCurrentNum();
        dataBuffer.setRunning(false);
        dataBuffer.setTotalFitness(0.0f);
        dataBuffer.call(compute(dataBuffer));

        return LockState.UNLOCKED;
    }

    private float compute(ChannelDataBuffer dataBuffer){
        return dataBuffer.getTotalFitness()/dataBuffer.getTotalNum();
    }
}
