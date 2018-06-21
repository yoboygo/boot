package ml.idream.designpattern.observe;

public class ConcurrentObserver implements Observer {
    private String name;
    public ConcurrentObserver(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println("My name is : " + this.name);
    }
}
