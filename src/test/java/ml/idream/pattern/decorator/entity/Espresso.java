package ml.idream.pattern.decorator.entity;

import ml.idream.pattern.decorator.Beverage;

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
