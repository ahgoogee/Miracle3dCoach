package monitor.doors;

import monitor.runtime.ICoachRuntime;

public interface ILockedDoor {

    /**
     * 尝试开锁
     *
     * @param dataBuffer 公共数据缓存
     * @param coachRuntime 服务器状态交互
     * @return 解锁成功与否
     */
    LockState unlocking(ChannelDataBuffer dataBuffer, ICoachRuntime coachRuntime);

    /**
     * 上锁
     */
    void lock();

    /**
     * 开锁
     */
    void unlock();

}
