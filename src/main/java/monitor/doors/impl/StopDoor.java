package monitor.doors.impl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.LockState;
import monitor.runtime.ICoachRuntime;

public class StopDoor extends LockedDoor{
    @Override
    public LockState unlock(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime) {

        dataBuffer.lockDisposableLock();
        dataBuffer.resetCurrent();
        dataBuffer.setIsRunning(false);


        System.out.println(dataBuffer.getTotalFitness()/dataBuffer.getTotalTestNum());

        dataBuffer.setTotalFitness(0.0f);
        dataBuffer.Signal();


        return LockState.UNLOCKED;
    }
}
