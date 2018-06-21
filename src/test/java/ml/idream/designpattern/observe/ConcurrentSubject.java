package ml.idream.designpattern.observe;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentSubject implements Subject {
    private List<Observer> observers = new ArrayList<Observer>();
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer o : observers){
            o.update();
        }
    }
}
