package benchmark;

import monitor.benchmarks.benchmarkImpl.RunBenchmark;
import monitor.doors.ISignal;

public class BenchmarkTest implements ISignal {
    private static Boolean next;

    public static synchronized Boolean getNext(){return next;}
    public static synchronized void setNext(Boolean _next){next = _next;}
    public static void main(String[] args) {
        RunBenchmark benchmark = new RunBenchmark(new BenchmarkTest());
        benchmark.startCoach();


        setNext(false);
        while (true){

            benchmark.startTest();

            while (!getNext()){}

            setNext(false);
        }

    }

    @Override
    public void signal() {
        setNext(true);
        System.out.println("signal");
    }

}
