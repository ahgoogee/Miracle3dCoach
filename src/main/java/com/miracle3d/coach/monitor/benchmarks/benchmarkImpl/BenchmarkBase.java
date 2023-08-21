package com.miracle3d.coach.monitor.benchmarks.benchmarkImpl;


import com.miracle3d.coach.monitor.benchmarks.IBenchmark;
import com.miracle3d.coach.monitor.benchmarks.IUpdateListener;
import com.miracle3d.coach.monitor.doors.ChannelDataBuffer;
import com.miracle3d.coach.monitor.doors.ILockedDoor;
import com.miracle3d.coach.monitor.runtime.ICoachRuntime;
import com.miracle3d.coach.monitor.runtime.impl.CoachRuntime;

import java.util.List;

public abstract class BenchmarkBase implements IBenchmark, IUpdateListener {
    protected ChannelDataBuffer dataBuffer;
    protected List<ILockedDoor> lockedDoors;
    protected ICoachRuntime coachRuntime;
    protected BenchmarkBase(){
        coachRuntime = new CoachRuntime("localhost",3200);
        coachRuntime.addUpdateListener(this);
        lockedDoors = createLockedDoors();
        dataBuffer = createChannelDataBuffer(lockedDoors);
    }

    protected abstract List<ILockedDoor> createLockedDoors();

    protected abstract ChannelDataBuffer createChannelDataBuffer(List<ILockedDoor> lockedDoorList);

    protected abstract void unlock(List<ILockedDoor> doors);
    @Override
    public void update() {
        unlock(lockedDoors);
    }

    @Override
    public void startBenchmark() {
        dataBuffer.setRunning(true);
    }

    @Override
    public void startConnection() {
        coachRuntime.startConnection();
    }
}
