package com.miracle3d.coach.monitor.benchmarks.benchmarkImpl;

import com.miracle3d.coach.monitor.benchmarks.BenchmarkStream;
import com.miracle3d.coach.monitor.benchmarks.IBenchmark;
import com.miracle3d.coach.monitor.runtime.ICoachRuntime;
import com.miracle3d.coach.monitor.runtime.impl.CoachRuntime;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class KickBenchmark implements IBenchmark{
    protected BenchmarkStream stream;

    protected ICoachRuntime coachRuntime;

    private final static Map<String,Object> initParams = new HashMap<String,Object> (){{
        put("Host","localhost");
        put("Port",3200);
        put("SingleTestTime",15f);
        put("TotalTestNum",10);
        put("InitBallX",-14f);
        put("InitBallY",0f);
    }};


    public KickBenchmark(){
        String host = (String) initParams.getOrDefault("Host","localhost");
        Integer port = (Integer) initParams.getOrDefault("Port",3200);

        coachRuntime = new CoachRuntime(host,port);
        coachRuntime.addUpdateListener(this);
        stream = new BenchmarkStream(coachRuntime,initParams);
    }

    @Override
    public void startBenchmark() {
        stream.setRunning(true);
    }

    @Override
    public void startConnection() {
        coachRuntime.startConnection();
    }

    @Override
    public void stopConnection() {
        coachRuntime.stopConnection();
    }

    @Override
    public void setCallback(Consumer<Float> callback) {
        stream.setCallback(callback);
    }

    @Override
    public void update() {
        stream.Continue()
                .InitProcess()
                .StartProcess()
                .CheckAgentProcess()
                .StartBenchmarkProcess()
                .SingleBenchmarkProcess()
                .ResultCollectProcess()
                .StopProcess();
    }
}
