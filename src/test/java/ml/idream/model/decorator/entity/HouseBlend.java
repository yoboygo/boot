package ml.idream.model.decorator.entity;

import ml.idream.model.decorator.Beverage;

/*
* 深培
* */
public class HouseBlend extends Beverage {
    public HouseBlend() {
        setDescription("House Bleand Coffee");
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
