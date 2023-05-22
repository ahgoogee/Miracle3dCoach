package base.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Subject<T> implements IPublishSubscribe<T> {
    private final List<IObserver<T>> observers = new ArrayList();

    public Subject() {
    }

    public void attach(IObserver<T> observer) {
        if (!this.observers.contains(observer)) {
            this.observers.add(observer);
        }

    }

    public boolean detach(IObserver<T> observer) {
        return this.observers.remove(observer);
    }

    public void detachAll() {
        this.observers.clear();
    }

    public void onStateChange(T content) {
        Iterator var2 = this.observers.iterator();

        while(var2.hasNext()) {
            IObserver<T> observer = (IObserver)var2.next();
            observer.update(content);
        }

    }
}
