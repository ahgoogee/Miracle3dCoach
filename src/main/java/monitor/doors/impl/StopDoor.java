package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;

public class StopDoor extends LockedDoor{
    @Override
    public LockState unlock(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {
        dataBuffer.resetCurrentNum();
        dataBuffer.setRunning(false);

        float fitness = dataBuffer.getTotalFitness()/dataBuffer.getTotalNum();


        dataBuffer.setTotalFitness(0.0f);

        dataBuffer.call(fitness);


        return LockState.UNLOCKED;
    }
}
