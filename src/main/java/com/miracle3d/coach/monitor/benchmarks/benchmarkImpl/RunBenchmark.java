package com.miracle3d.coach.monitor.benchmarks.benchmarkImpl;

import com.miracle3d.coach.monitor.doors.impl.*;
import com.miracle3d.coach.monitor.doors.ChannelDataBuffer;
import com.miracle3d.coach.monitor.doors.ILockedDoor;
import com.miracle3d.coach.monitor.doors.LockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RunBenchmark extends BenchmarkBase{
    public RunBenchmark() {
        super();
    }

    @Override
    protected List<ILockedDoor> createLockedDoors() {
        List<ILockedDoor> doorList = new ArrayList<>();

        doorList.add(new InitDoor());
        doorList.add(new StartDoor());
        doorList.add(new CheckAgentDoor());
        doorList.add(new StartTestDoor());
        doorList.add(new SingleTestDoor());
        doorList.add(new ResultCountDoor());
        doorList.add(new StopDoor());

        return doorList;
    }

    @Override
    protected ChannelDataBuffer createChannelDataBuffer(List<ILockedDoor> lockedDoorList) {
        return new ChannelDataBuffer(10,15.0f, lockedDoorList);
    }

    @Override
    protected void unlock(List<ILockedDoor> doors) {
        for(ILockedDoor door : doors)
        {
            LockState state = door.unlocking(this.dataBuffer,this.coachRuntime);
            if(state==LockState.LOCKED)
                return;
        }
    }


    @Override
    public void stopConnection() {
        coachRuntime.stopConnection();
    }

    @Override
    public void setCallback(Consumer<Float> callback) {
        dataBuffer.setCallback(callback);
    }
}
