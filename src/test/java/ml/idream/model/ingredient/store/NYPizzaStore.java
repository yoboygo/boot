package ml.idream.model.ingredient.store;

import ml.idream.model.ingredient.ingre.PizzaIngredient;
import ml.idream.model.ingredient.pizza.CheesePizza;
import ml.idream.model.ingredient.pizza.Pizza;

public class NYPizzaStore extends PizzaStore {
    private PizzaIngredient ingredient;

    public NYPizzaStore(PizzaIngredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    protected Pizza createPizza(String type) {
        if(type.equals("cheese")){
            return new CheesePizza(ingredient);
        }
        return null;
    }
}
