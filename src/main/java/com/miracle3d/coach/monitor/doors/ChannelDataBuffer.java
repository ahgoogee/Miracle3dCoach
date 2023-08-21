package com.miracle3d.coach.monitor.doors;


import com.miracle3d.coach.api.ICallback;

import java.util.List;
import java.util.Objects;

/**
 * Channel data buffer
 * 通道数据缓存器
 * 管理门之间的共享数据
 */
public class ChannelDataBuffer {

    /**
     * 是否开启测试
     */
    private  Boolean running = false;
    /**
     * 总测试数
     */

    private final Integer totalTestNum;
    /**
     * 当前测试次数
     */
    private Integer currentTestNum = 0;
    /**
     * 单次测试时间
     */
    private final Float singleTestTime;
    /**
     * 总适应度
     */
    private Float totalFitness = 0.0f;

    /**
     * 所有door,用于门之间访问
     */
    private List<ILockedDoor> lockedDoorList;


    /**
     * 结果返回回调函数
     */
    private ICallback callback;

    public ChannelDataBuffer(Integer totalTestNum, Float singleTestTime, List<ILockedDoor> lockedDoorList) {
        this.totalTestNum = totalTestNum;
        this.singleTestTime = singleTestTime;
        this.lockedDoorList = lockedDoorList;
    }

    public synchronized Boolean isRunning() {return running;}
    public synchronized void setRunning(Boolean running) {this.running = running;}
    public Integer getTotalNum() {return totalTestNum;}
    public Float getSingleTime() {return singleTestTime;}
    public Float getTotalFitness() {return totalFitness;}
    public void setTotalFitness(Float totalFitness) {this.totalFitness = totalFitness;}
    public void addCurrentNum()
    {
        this.currentTestNum += 1;
    }
    public void resetCurrentNum(){
        this.currentTestNum = 0;
    }
    public Integer getCurrentNum(){return this.currentTestNum;}
    public void lock(){lockedDoorList.forEach(ILockedDoor::lock);}

    public void setCallback(ICallback callback){
        this.callback = callback;
    }
    public void call(float fitness){
        if(!Objects.isNull(callback))
            callback.call(fitness);
    }

}
