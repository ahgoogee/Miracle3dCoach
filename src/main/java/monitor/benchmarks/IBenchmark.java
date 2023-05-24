package monitor.benchmarks;

public interface IBenchmark extends IUpdateListener{
    void startTest();

    void startCoach();
    //float getFitness();
}
