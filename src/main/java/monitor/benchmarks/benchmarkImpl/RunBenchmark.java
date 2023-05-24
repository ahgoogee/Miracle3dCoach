package monitor.benchmarks.benchmarkImpl;

import monitor.doors.ChannelDataBuffer;
import monitor.doors.ILockedDoor;
import monitor.doors.ISignal;
import monitor.doors.LockState;
import monitor.doors.impl.*;

import java.util.ArrayList;
import java.util.List;

public class RunBenchmark extends BenchmarkBase{
    public RunBenchmark(ISignal signal) {
        super(signal);
        this.signal = signal;
    }
    private ISignal signal = null;

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
    protected ChannelDataBuffer createChannelDataBuffer(ISignal signal) {
        return new ChannelDataBuffer(10,1.0f, lockedDoors,signal);
    }

    @Override
    protected void unlock(List<ILockedDoor> doors) {
        for(ILockedDoor door : doors)
        {
            LockState state = door.unlock(this.dataBuffer,this.coachRuntime);
            if(state==LockState.LOCKED)
                return;
        }
    }


}
