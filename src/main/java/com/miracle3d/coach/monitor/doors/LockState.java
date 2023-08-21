package com.miracle3d.coach.monitor.doors;

public enum LockState {
    //已上锁
    LOCKED(false,"上锁的"),
    //已解锁
    UNLOCKED(true,"未上锁的");

    LockState(Boolean code,String state){
        this.code = code;
        this.state = state;
    }
    private Boolean code;
    private String state;

    public Boolean getCode() {
        return code;
    }
}
