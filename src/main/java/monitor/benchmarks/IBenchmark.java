package monitor.benchmarks;

import api.ICallback;

public interface IBenchmark extends IUpdateListener{
    void startBenchmark();

    void startConnection();

    void stopConnection();

    void setCallback(ICallback callback);
}
