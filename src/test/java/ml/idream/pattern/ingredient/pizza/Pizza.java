package ml.idream.pattern.ingredient.pizza;

import ml.idream.pattern.ingredient.ingre.Cheese;
import ml.idream.pattern.ingredient.ingre.Dough;
import ml.idream.pattern.ingredient.ingre.Sauce;
import ml.idream.pattern.ingredient.ingre.Veggie;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {
    private String name;
    private Dough dough;
    private Sauce sauce;
    private Cheese cheese;
    private Veggie[] veggies;

    private List<String> toppings = new ArrayList<String>();

    public abstract void prepare();

    public void bake(){
        System.out.println("Bake for 25 minutes at 350");
    }
    public void cut(){
        System.out.println("Cutting the pizza into diagonal slices");
    }
    public void box(){
        System.out.println("Place Pizza in official PizzaStore box");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dough getDough() {
        return dough;
    }

    public void setDough(Dough dough) {
        this.dough = dough;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public Cheese getCheese() {
        return cheese;
    }

    public void setCheese(Cheese cheese) {
        this.cheese = cheese;
    }

    public Veggie[] getVeggies() {
        return veggies;
    }

    public void setVeggies(Veggie[] veggies) {
        this.veggies = veggies;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }
}
