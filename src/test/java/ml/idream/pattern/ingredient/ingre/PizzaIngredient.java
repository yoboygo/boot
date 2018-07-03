package ml.idream.pattern.ingredient.ingre;

public interface PizzaIngredient {
    public Dough creatDough();
    public Sauce createSauce();
    public Cheese createCheese();
    public Veggie[] createVeggies();
}
