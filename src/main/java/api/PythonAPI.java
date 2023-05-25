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
        //System.out.println("setRan");
        PythonAPI.ran = ran;
    }
    private synchronized static float getResult() {
        //System.out.println("getResult");
        return result;}
    private synchronized static void setResult(float result) {
        //System.out.println("setResult");
        PythonAPI.result = result;}

    public static void startCoach(){
        getBenchmark().setCallback(new PythonAPI());
        getBenchmark().startConnection();
    }
    public static void stopCoach(){
        getBenchmark().stopConnection();
    }
    public static float getBenchmarkResult(){
        getBenchmark().startBenchmark();
        while (!isRan()){
        }
        float fit = getResult();
        setResult(0.0f);
        return fit;
    }

    @Override
    public void call(float fitness) {
        System.out.println("call");
        setResult(fitness);
        setRan(true);

    }
}
