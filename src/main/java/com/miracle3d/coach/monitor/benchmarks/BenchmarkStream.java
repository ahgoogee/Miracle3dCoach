package com.miracle3d.coach.monitor.benchmarks;

import com.miracle3d.coach.base.geometry.Pose2D;
import com.miracle3d.coach.monitor.enums.PlayMode;
import com.miracle3d.coach.monitor.runtime.ICoachRuntime;
import lombok.*;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter @Setter
public class BenchmarkStream extends ProcessorCollection {

    private Boolean running = false;

    private final Float singleTestTime;

    private final Integer totalTestNum;

    private final Pose2D initBallPose;

    private Integer currentTestNum = 0;

    private Float totalFitness = 0.0f;

    private Consumer<Float> callback;

    private ICoachRuntime coachRuntime;

    private Boolean ignore;

    private int localStartDelay = 40;

    private int localWaitDelay = 100;

    public BenchmarkStream(ICoachRuntime coachRuntime, Map<String,Object> initParams){
        super();
        scanProcessor(this.getClass());
        this.coachRuntime = coachRuntime;

        singleTestTime = (Float) initParams.getOrDefault("SingleTestTime",15f);
        totalTestNum = (Integer) initParams.getOrDefault("TotalTestNum",10);
        Float initBallX = (Float) initParams.getOrDefault("InitBallX",0f);
        Float initBallY = (Float) initParams.getOrDefault("InitBallY",0f);
        initBallPose = new Pose2D(initBallX,initBallY);
    }

    public BenchmarkStream Ignore(){
        setIgnore(true);
        return this;
    }

    public BenchmarkStream Continue(){
        setIgnore(false);
        return this;
    }


    @Processor
    public BenchmarkStream InitProcess(){
        //判断是否需要跳过
        if(getIgnore())return Ignore();

        //只需要被执行一次
        if(getProcessorState(getCurrentFunctionName()))
            return Continue();


        if(PlayMode.BEFORE_KICK_OFF == coachRuntime.getWorldModel().getPlayMode())
        {
            completeProcessor(getCurrentFunctionName());
            return Continue();
        }
        //设置游戏进程为BEFORE_KICK_OFF
        coachRuntime.getCommander().setPlaymode(PlayMode.BEFORE_KICK_OFF);
        coachRuntime.getCommander().setTime(0.0f);

        return Ignore();
    }

    @Processor
    public BenchmarkStream StartProcess(){
        //判断是否需要跳过
        if(getIgnore())return Ignore();

        //只需要被执行一次
        if(getProcessorState(getCurrentFunctionName()))
            return Continue();

        if(getRunning()){
            completeProcessor(getCurrentFunctionName());
            return Continue();
        }

        return Ignore();
    }


    @Processor
    public BenchmarkStream CheckAgentProcess(){
        //判断是否需要跳过
        if(getIgnore())return Ignore();

        //只需要被执行一次
        if(getProcessorState(getCurrentFunctionName()))
            return Continue();

        coachRuntime.getCommander().beamBall((float) initBallPose.x, (float) initBallPose.y);

        if(coachRuntime.getWorldModel().getSoccerAgents().isEmpty())
            return Ignore();

        if(localStartDelay > 0) {
            localStartDelay --;
            return Ignore();
        }
        else {
            localStartDelay = 40;
        }

        if (coachRuntime.getWorldModel().getSoccerAgents().get(0).getPosition().getZ()<0.25)
        {
            return Ignore();
        }

        completeProcessor(getCurrentFunctionName());
        return Continue();
    }

    @Processor
    public BenchmarkStream StartBenchmarkProcess(){
        //判断是否需要跳过
        if(getIgnore())return Ignore();

        //只需要被执行一次
        if(getProcessorState(getCurrentFunctionName()))
            return Continue();

        if( PlayMode.PLAY_ON == coachRuntime.getWorldModel().getPlayMode())
        {
            completeProcessor(getCurrentFunctionName());
            return Continue();
        }

        coachRuntime.getCommander().setPlaymode(PlayMode.PLAY_ON);

        return Ignore();
    }


    private Vector3D ballLastPose = Vector3D.ZERO;
    private Boolean ballLastState = false;
    private Boolean ballCurrentState = false;
    @Processor
    public BenchmarkStream SingleBenchmarkProcess(){
        //判断是否需要跳过
        if(getIgnore())return Ignore();

        //只需要被执行一次
        if(getProcessorState(getCurrentFunctionName()))
            return Continue();

        Predicate<Vector3D> isMoving = pos->{
            ballLastState = ballCurrentState;
            ballLastPose = pos;

            double deltaX = Vector3D.distance(pos,ballLastPose);
            ballCurrentState = !(deltaX < 0.05);
            return ballCurrentState;
        };

        if(coachRuntime.getWorldModel().getTime() >= getSingleTestTime()
        || (!isMoving.test(coachRuntime.getWorldModel().getBall().getPosition()) && ballLastState))
        {
            Float distance = (float) coachRuntime.getWorldModel().getBall().getPosition().getX() - (float) initBallPose.x;
            setTotalFitness(getTotalFitness()+distance);

            completeProcessor(getCurrentFunctionName());
            return Continue();
        }

        return Ignore();
    }

    @Processor
    public BenchmarkStream ResultCollectProcess(){
        //判断是否需要跳过
        if(getIgnore())return Ignore();

        resetAllProcessor();

        setCurrentTestNum(getCurrentTestNum()+1);

        if(getCurrentTestNum() >= getTotalTestNum())
        {
            completeProcessor(getCurrentFunctionName());
            return Continue();
        }
        return Ignore();
    }

    @Processor
    public BenchmarkStream StopProcess(){
        //判断是否需要跳过
        if(getIgnore())return Ignore();

        Float result = getTotalFitness()/getTotalTestNum();

        setCurrentTestNum(0);
        setRunning(false);
        setTotalFitness(0.0f);
        localStartDelay = 40;
        callback.accept(result);

        return Continue();
    }
}
