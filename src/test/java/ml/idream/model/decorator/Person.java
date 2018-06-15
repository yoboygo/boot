package ml.idream.model.decorator;

public abstract class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public abstract void show();
}
