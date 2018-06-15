package ml.idream.model.decorator;

public class Finery extends Person {

    private Person person;
    public Finery(String name) {
        super(name);
    }

    @Override
    public void show() {
        person.show();
    }
}
