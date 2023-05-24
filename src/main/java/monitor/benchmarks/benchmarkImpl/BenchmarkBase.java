package monitor.benchmarks.benchmarkImpl;


import monitor.benchmarks.IBenchmark;
import monitor.benchmarks.IUpdateListener;
import monitor.doors.ChannelDataBuffer;
import monitor.doors.ILockedDoor;
import monitor.doors.ISignal;
import monitor.runtime.ICoachRuntime;
import monitor.runtime.impl.CoachRuntime;


import java.util.List;

public abstract class BenchmarkBase implements IBenchmark, IUpdateListener {
    protected ChannelDataBuffer dataBuffer;
    protected List<ILockedDoor> lockedDoors;
    protected ICoachRuntime coachRuntime;
    protected BenchmarkBase(ISignal signal){
        coachRuntime = CoachRuntime.getInstance("localhost",3200);
        coachRuntime.addUpdateListener(this);
        lockedDoors = createLockedDoors();
        dataBuffer = createChannelDataBuffer(signal);
    }

    protected abstract List<ILockedDoor> createLockedDoors();

    protected abstract ChannelDataBuffer createChannelDataBuffer(ISignal signal);

    protected abstract void unlock(List<ILockedDoor> doors);
    @Override
    public void update() {
        unlock(lockedDoors);
    }

    @Override
    public void startTest() {
        dataBuffer.setIsRunning(true);
    }

    @Override
    public void startCoach() {
        coachRuntime.startCoach();
    }
}
