package com.miracle3d.coach.monitor.benchmarks;

import com.miracle3d.coach.api.ICallback;

public interface IBenchmark extends IUpdateListener{
    void startBenchmark();

    void startConnection();

    void stopConnection();

    void setCallback(ICallback callback);
}
