package ml.idream.pattern.simplefactory;

public class NYStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza() {
        super.setName("New York Style Sauce and Cheese Pizza");
        super.setDough("Thin Crust Dough");
        super.setSauce("Marinara Sauce");
        getToppings().add("Grated Reggiano Cheese");
    }
}
