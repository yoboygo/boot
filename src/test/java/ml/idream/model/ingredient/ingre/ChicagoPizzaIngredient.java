package ml.idream.model.ingredient.ingre;

public class ChicagoPizzaIngredient implements PizzaIngredient {

    @Override
    public Dough creatDough() {
        return new ChicagoDough();
    }

    @Override
    public Sauce createSauce() {
        return new ChicagoSauce();
    }

    @Override
    public Cheese createCheese() {
        return new ChicagoCheese();
    }

    @Override
    public Veggie[] createVeggies() {
        Veggie[] veggies = {new Garlic()};
        return veggies;
    }

}
