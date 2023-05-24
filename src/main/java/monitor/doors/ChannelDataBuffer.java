package monitor.doors;


import java.util.List;

/**
 * Channel data buffer
 * 通道数据缓存器
 * 管理门之间的共享数据
 */
public class ChannelDataBuffer {

    /**
     * 是否开启测试
     */
    private  Boolean isRunning = false;
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


    private ISignal signal;

    //启动回调函数

    public ChannelDataBuffer(Integer totalTestNum, Float singleTestTime, List<ILockedDoor> lockedDoorList,ISignal signal) {
        this.totalTestNum = totalTestNum;
        this.singleTestTime = singleTestTime;
        this.lockedDoorList = lockedDoorList;
        this.signal = signal;
    }

    public synchronized Boolean isRunning() {return isRunning;}
    public synchronized void setIsRunning(Boolean isRunning) {this.isRunning = isRunning;}
    public Integer getTotalTestNum() {return totalTestNum;}
    public Float getSingleTestTime() {return singleTestTime;}
    public Float getTotalFitness() {return totalFitness;}
    public void setTotalFitness(Float totalFitness) {this.totalFitness = totalFitness;}
    public void addCurrent()
    {
        this.currentTestNum += 1;
    }
    public void resetCurrent(){
        currentTestNum = 0;
    }
    public Integer getCurrentTestNum(){return this.currentTestNum;}
    public void lockDisposableLock(){lockedDoorList.forEach(ILockedDoor::lock);}

    public void Signal(){
        signal.signal();
    }

}
