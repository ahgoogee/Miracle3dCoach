package base.observer;

@FunctionalInterface
public interface ISubscribe<T> {
    void attach(IObserver<T> var1);
}
