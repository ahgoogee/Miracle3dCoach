package com.miracle3d.coach.base.observer;

@FunctionalInterface
public interface IObserver<T> {
    void update(T var1);
}
