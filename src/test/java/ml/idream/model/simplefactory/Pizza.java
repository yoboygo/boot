package ml.idream.model.simplefactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {
    private String name;
    private String dough;
    private String sauce;
    private List<String> toppings = new ArrayList<String>();

    public void prepare(){
        System.out.println("preparing " + name);
        System.out.println("Tossing dough ...");
        System.out.println("Adding sauce ...");
        System.out.println("Adding topoings ...");
        for(String top : toppings){
            System.out.println(" " + top);
        }
    }

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

    public String getDough() {
        return dough;
    }

    public void setDough(String dough) {
        this.dough = dough;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }
}
