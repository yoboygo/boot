package ml.idream.model.ingredient.store;

import ml.idream.model.ingredient.ingre.ChicagoPizzaIngredient;
import ml.idream.model.ingredient.ingre.PizzaIngredient;
import ml.idream.model.ingredient.pizza.CheesePizza;
import ml.idream.model.ingredient.pizza.Pizza;

public class ChicagoPizzaStore extends PizzaStore {
    private PizzaIngredient ingredient = new ChicagoPizzaIngredient();

    public ChicagoPizzaStore(){}

    public ChicagoPizzaStore(PizzaIngredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    protected Pizza createPizza(String type) {
        if (type.equals("cheese")){
            return new CheesePizza(ingredient);
        }
        return null;
    }
}
