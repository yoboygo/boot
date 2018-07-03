package ml.idream.pattern.simplefactory;

public class MainApp {

    public static void main(String[] args) {
        ChicagoPizzaStore cps = new ChicagoPizzaStore();
        NYPizzaStore nys = new NYPizzaStore();
        cps.orderPizza("cheese");
        nys.orderPizza("cheese");
    }
}
