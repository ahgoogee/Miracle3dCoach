package api;

import monitor.benchmarks.IBenchmark;
import monitor.benchmarks.benchmarkImpl.RunBenchmark;

import java.util.Objects;

public class PythonAPI implements ICallback {
    private static boolean ran = false;
    private static float result = 0.0f;
    private static IBenchmark benchmark;
    private static IBenchmark getBenchmark(){
        if(Objects.isNull(benchmark))
            benchmark = new RunBenchmark();
        return benchmark;
    }
    private synchronized static boolean isRan() {return ran;}
    private synchronized static void setRan(boolean ran) {
        PythonAPI.ran = ran;
    }
    private synchronized static float getResult() {
        return result;}
    private synchronized static void setResult(float result) {
        PythonAPI.result = result;}
    private static void reset(){
        setRan(false);
        setResult(0.0f);
    }

    public static void startCoach(){
        getBenchmark().setCallback(new PythonAPI());
        getBenchmark().startConnection();
    }
    public static void stopCoach(){
        getBenchmark().stopConnection();
    }
    public static float getBenchmarkResult(){
        reset();

        getBenchmark().startBenchmark();

        while (!isRan());

        return getResult();
    }

    @Override
    public void call(float fitness) {
        setResult(fitness);
        setRan(true);
    }
}
