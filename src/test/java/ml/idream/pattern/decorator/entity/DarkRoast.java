package ml.idream.pattern.decorator.entity;

import ml.idream.pattern.decorator.Beverage;

/*
* 饮料类型：深培
* */
public class DarkRoast extends Beverage {
    public DarkRoast() {
        setDescription("Dark Roast Coffee");
    }

    @Override
    public double cost() {
        return 0.98;
    }
}
