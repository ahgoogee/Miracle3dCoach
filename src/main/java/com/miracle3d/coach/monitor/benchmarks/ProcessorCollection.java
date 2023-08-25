package com.miracle3d.coach.monitor.benchmarks;

import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessorCollection {
    private final Map<String, Boolean> Processors = new HashMap<>();

    protected <S extends ProcessorCollection> void resetProcessor(ProcessorFunction<S> processor) {
        resetProcessor(ProcessorCollection.getFunctionName(processor));
    }

    protected <S extends ProcessorCollection> void completeProcessor(ProcessorFunction<S> processor) {
        completeProcessor(ProcessorCollection.getFunctionName(processor));
    }

    protected <S extends ProcessorCollection> Boolean getProcessorState(ProcessorFunction<S> processor) {
        return getProcessorState(ProcessorCollection.getFunctionName(processor));
    }

    protected void resetProcessor(String name) {
        if (Processors.containsKey(name))
            Processors.put(name, false);
    }

    protected void resetAllProcessor(){
        Processors.forEach((key,value)->{
            Processors.put(key, false);
        });
    }

    protected void completeProcessor(String name) {
        if (Processors.containsKey(name))
            Processors.put(name, true);
    }

    protected Boolean getProcessorState(String name) {
        return Processors.get(name);
    }

    protected void scanProcessor(Class<?> range) {
        Arrays.stream(range.getMethods())
                .filter(method -> Arrays.stream(method.getDeclaredAnnotations())
                        .anyMatch(annotation -> annotation.annotationType().isAssignableFrom(Processor.class))
                )
                .map(Method::getName)
                .collect(Collectors.toList())
                .forEach(name -> Processors.put(name, false));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    protected @interface Processor {
    }

    @FunctionalInterface
    protected interface ProcessorFunction<S extends ProcessorCollection> extends Serializable {
        S process();
    }

    @SneakyThrows
    protected static <S extends ProcessorCollection> String getFunctionName(ProcessorFunction<S> func) {
        // 通过获取对象方法，判断是否存在该方法
        Method method = func.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(Boolean.TRUE);
        // 利用jdk的SerializedLambda 解析方法引用
        SerializedLambda serializedLambda = (SerializedLambda) method.invoke(func);
        return serializedLambda.getImplMethodName();
    }

    protected static String getCurrentFunctionName() {
        // 获取当前方法的调用堆栈
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // 第0个元素是getStackTrace方法本身，第1个元素是当前方法调用，第2个元素是调用当前方法的方法
        // 所以我们取第2个元素来获取调用当前方法的方法的信息
        StackTraceElement caller = stackTrace[2];

        // 获取调用方法的方法名
        return caller.getMethodName();
    }
}
