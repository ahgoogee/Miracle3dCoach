package benchmark;

import com.miracle3d.coach.monitor.benchmarks.benchmarkImpl.RunBenchmark;

public class BenchmarkTest {
    private static Boolean next;

    public static synchronized Boolean getNext(){return next;}
    public static synchronized void setNext(Boolean _next){next = _next;}
    public static void main(String[] args) {
        RunBenchmark benchmark = new RunBenchmark();
        benchmark.setCallback(BenchmarkTest::call);
        benchmark.startConnection();


        setNext(false);
        while (true){

            benchmark.startBenchmark();

            while (!getNext()){}

            setNext(false);
        }

    }

    public static void call(Float fitness) {
        setNext(true);
        System.out.println("AVG Fit:"+fitness);
    }

}
