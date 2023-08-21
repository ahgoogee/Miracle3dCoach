package com.miracle3d.coach.api;

import com.miracle3d.coach.monitor.benchmarks.IBenchmark;
import com.miracle3d.coach.monitor.benchmarks.benchmarkImpl.RunBenchmark;

import java.util.Objects;

public class API {
    private static float result = 0.0f;
    private static IBenchmark benchmark;
    private static IBenchmark getBenchmark(){
        if(Objects.isNull(benchmark))
            benchmark = new RunBenchmark();
        return benchmark;
    }
    public static void startCoach(){
        getBenchmark().setCallback((fitness -> {
            synchronized (API.class){
                result = fitness;
                API.class.notify();
            }
        }));
        getBenchmark().startConnection();
    }
    public static void stopCoach(){
        getBenchmark().stopConnection();
    }
    public static float getBenchmarkResult(){
        synchronized (API.class){
            getBenchmark().startBenchmark();
            System.out.println("[[Benchmark Start]]");
            try {
                API.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[[Benchmark Stop]]");
            return result;
        }
    }

    public static void hi(){
        System.out.println("hi");
    }

    public static void main(String[] args) {
        startCoach();
        for (int i = 0; i < 15; i++) {
            float result = getBenchmarkResult();
            System.out.println(result);
        }
    }
}
