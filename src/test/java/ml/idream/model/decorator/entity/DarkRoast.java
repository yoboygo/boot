package ml.idream.model.decorator.entity;

import ml.idream.model.decorator.Beverage;

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
