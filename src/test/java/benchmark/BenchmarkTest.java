package benchmark;

import com.miracle3d.coach.monitor.benchmarks.benchmarkImpl.RunBenchmark;
import com.miracle3d.coach.api.ICallback;

public class BenchmarkTest implements ICallback {
    private static Boolean next;

    public static synchronized Boolean getNext(){return next;}
    public static synchronized void setNext(Boolean _next){next = _next;}
    public static void main(String[] args) {
        RunBenchmark benchmark = new RunBenchmark();
        benchmark.setCallback(new BenchmarkTest());
        benchmark.startConnection();


        setNext(false);
        while (true){

            benchmark.startBenchmark();

            while (!getNext()){}

            setNext(false);
        }

    }

    @Override
    public void call(float fitness) {
        setNext(true);
        System.out.println("AVG Fit:"+fitness);
    }

}
