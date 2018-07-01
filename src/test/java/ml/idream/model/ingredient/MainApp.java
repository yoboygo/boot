package ml.idream.model.ingredient;

import ml.idream.model.ingredient.ingre.ChicagoPizzaIngredient;
import ml.idream.model.ingredient.ingre.PizzaIngredient;
import ml.idream.model.ingredient.store.ChicagoPizzaStore;
import ml.idream.model.ingredient.store.NYPizzaStore;

public class MainApp {

    public static void main(String[] args) {
        ChicagoPizzaStore cps = new ChicagoPizzaStore();
//        NYPizzaStore nys = new NYPizzaStore();
        cps.orderPizza("cheese");
//        nys.orderPizza("cheese");
    }
}
