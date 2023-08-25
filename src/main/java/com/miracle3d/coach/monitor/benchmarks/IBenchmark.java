package com.miracle3d.coach.monitor.benchmarks;

import java.util.function.Consumer;

public interface IBenchmark extends IUpdateListener{
    void startBenchmark();

    void startConnection();

    void stopConnection();

    void setCallback(Consumer<Float> callback);
}
