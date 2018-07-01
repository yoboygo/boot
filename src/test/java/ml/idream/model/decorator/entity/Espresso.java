package ml.idream.model.decorator.entity;

import ml.idream.model.decorator.Beverage;

/*
* 浓缩咖啡
* */
public class Espresso extends Beverage {
    public Espresso() {
       setDescription("Espresson");
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
