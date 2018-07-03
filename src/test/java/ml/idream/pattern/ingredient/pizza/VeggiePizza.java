package ml.idream.pattern.ingredient.pizza;

import ml.idream.pattern.ingredient.ingre.PizzaIngredient;

public class VeggiePizza extends Pizza {
    private PizzaIngredient ingredient;

    public VeggiePizza(PizzaIngredient ingredient) {
        this.ingredient = ingredient;
        super.setName("New York Style Sauce and Cheese Pizza");
        getToppings().add("Grated Reggiano Cheese");
    }

    @Override
    public void prepare() {
        setCheese(ingredient.createCheese());
        setDough(ingredient.creatDough());
        setSauce(ingredient.createSauce());
        setVeggies(ingredient.createVeggies());
    }
}
