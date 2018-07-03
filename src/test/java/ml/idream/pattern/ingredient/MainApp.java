package ml.idream.pattern.ingredient;

import ml.idream.pattern.ingredient.store.ChicagoPizzaStore;

public class MainApp {

    public static void main(String[] args) {
        ChicagoPizzaStore cps = new ChicagoPizzaStore();
//        NYPizzaStore nys = new NYPizzaStore();
        cps.orderPizza("cheese");
//        nys.orderPizza("cheese");
    }
}
