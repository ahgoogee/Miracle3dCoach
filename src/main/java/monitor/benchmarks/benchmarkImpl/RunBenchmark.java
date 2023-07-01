package monitor.benchmarks.benchmarkImpl;

import api.ICallback;
import monitor.doors.ChannelDataBuffer;
import monitor.doors.ILockedDoor;
import monitor.doors.LockState;
import monitor.doors.impl.*;

import java.util.ArrayList;
import java.util.List;

public class RunBenchmark extends BenchmarkBase{
    public RunBenchmark() {
        super();
    }
    private ICallback callback = null;

    @Override
    protected List<ILockedDoor> createLockedDoors() {
        List<ILockedDoor> doorList = new ArrayList<>();

        doorList.add(new InitDoor());
        doorList.add(new StartDoor());
        doorList.add(new StartTestDoor());
        doorList.add(new SingleTestDoor());
        doorList.add(new ResultCountDoor());
        doorList.add(new StopDoor());

        return doorList;
    }

    @Override
    protected ChannelDataBuffer createChannelDataBuffer(List<ILockedDoor> lockedDoorList) {
        return new ChannelDataBuffer(10,1.0f, lockedDoorList);
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
    public void setCallback(ICallback callback) {
        dataBuffer.setCallback(callback);
    }
}
