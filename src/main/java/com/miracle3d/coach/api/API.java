package com.miracle3d.coach.api;

import com.miracle3d.coach.monitor.benchmarks.IBenchmark;
import com.miracle3d.coach.monitor.benchmarks.benchmarkImpl.KickBenchmark;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 为python提供的静态接口
 */
public class API {
    private static AtomicReference<Float> result = new AtomicReference<>(null);
    private static IBenchmark benchmark;

    private static void resetBenchmark(){
        benchmark = null;
    }

    private static IBenchmark getBenchmark(){
        if(Objects.isNull(benchmark))
            benchmark = new KickBenchmark();
        return benchmark;
    }


    /**
     * 启动教练程序,连接服务器
     */
    public static void startCoach(){
        resetBenchmark();
        //注入回调函数
        getBenchmark().setCallback((fitness -> result.set(fitness)));
        getBenchmark().startConnection();
        System.out.println("[[Open Connection]]");
    }


    public static void stopCoach(){
        getBenchmark().stopConnection();
        resetBenchmark();
        System.out.println("[[Break Connection]]");
    }


    public static float getBenchmarkResult() throws InterruptedException {
        getBenchmark().startBenchmark();
        int timeout = 150;
        System.out.println("[[Benchmark Start]]");

        while (result.get()==null && timeout>0){
            Thread.sleep(100);
            timeout --;
        }
        System.out.println("[[Benchmark  Stop]]");

        if(result.get()==null)
            throw new RuntimeException("测试超时");

        System.out.println("timeout:"+timeout);
        float res = result.get();
        result.set(null);

        return res;
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
