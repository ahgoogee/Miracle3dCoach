package com.miracle3d.coach.api;

import com.miracle3d.coach.monitor.benchmarks.IBenchmark;
import com.miracle3d.coach.monitor.benchmarks.benchmarkImpl.KickBenchmark;

import java.util.Objects;


/**
 * 为python提供的静态接口
 */
public class API {
    private static float result = 0.0f;
    private static IBenchmark benchmark;
    private static IBenchmark getBenchmark(){
        if(Objects.isNull(benchmark))
            benchmark = new KickBenchmark();
        return benchmark;
    }


    /**
     * 启动教练程序,连接服务器
     */
    public static void startCoach(){
        //注入回调函数
        getBenchmark().setCallback((fitness -> {
            synchronized (API.class){
                result = fitness;
                API.class.notifyAll();
            }
        }));
        getBenchmark().startConnection();
        System.out.println("[[Open Connection]]");
    }


    public static void stopCoach(){
        getBenchmark().stopConnection();
        System.out.println("[[Break Connection]]");
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
            System.out.println("[[Benchmark  Stop]]");
            return result;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        startCoach();
        for (int i = 0; i < 5; i++) {
            float res = getBenchmarkResult();
            System.out.printf("R<%f>\n",res);
        }
        Thread.sleep(1000);
        stopCoach();
    }
}
