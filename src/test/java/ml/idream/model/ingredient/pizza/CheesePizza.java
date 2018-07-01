package ml.idream.model.ingredient.pizza;

import ml.idream.model.ingredient.ingre.*;

public class CheesePizza extends Pizza {

    private PizzaIngredient ingredient;

    public CheesePizza(PizzaIngredient ingredient) {
        this.ingredient = ingredient;
        super.setName("Chicago Style Sauce and Cheese Pizza");
        getToppings().add("Sharedded Mozzarella Cheese");
    }

    @Override
    public void prepare() {
        Cheese cheese = ingredient.createCheese();
        Dough dough = ingredient.creatDough();
        Sauce sauce = ingredient.createSauce();
        Veggie[] veggies = ingredient.createVeggies();

        setCheese(cheese);
        setDough(dough);
        setSauce(sauce);
        setVeggies(veggies);

        System.out.println("prepare pizza");
        System.out.println(cheese.getName());
        System.out.println(dough.getName());
        System.out.println(sauce.getName());
        System.out.println("--- Veggie ---");
        for (Veggie v : veggies){
            System.out.println(v.getName());
        }
    }

    @Override
    public void cut() {
        System.out.println("Cutting the pizza into square slices");
    }
}
