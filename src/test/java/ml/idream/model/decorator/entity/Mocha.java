package ml.idream.model.decorator.entity;

import ml.idream.model.decorator.Beverage;
import ml.idream.model.decorator.CondimentDecorator;
/*
* 调料：摩卡
* */
public class Mocha extends CondimentDecorator {
    private String desc = "Mocha";
    private Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", " + desc;
    }

    @Override
    public double cost() {
        return 0.2 + beverage.cost();
    }
}
