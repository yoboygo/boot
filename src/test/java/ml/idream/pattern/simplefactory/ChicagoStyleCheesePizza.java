package ml.idream.pattern.simplefactory;

public class ChicagoStyleCheesePizza extends Pizza {
    public ChicagoStyleCheesePizza() {
        super.setName("Chicago Style Sauce and Cheese Pizza");
        super.setDough("Extra Thick Crust Dough");
        super.setSauce("Plum Tomato Sauce");
        getToppings().add("Sharedded Mozzarella Cheese");
    }

    @Override
    public void cut() {
        System.out.println("Cutting the pizza into square slices");
    }
}
