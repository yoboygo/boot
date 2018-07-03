package ml.idream.pattern.decorator.entity;

import ml.idream.pattern.decorator.Beverage;

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
