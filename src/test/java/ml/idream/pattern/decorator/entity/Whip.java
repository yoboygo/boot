package ml.idream.pattern.decorator.entity;

import ml.idream.pattern.decorator.Beverage;
import ml.idream.pattern.decorator.CondimentDecorator;
/*
* 调料：奶泡
* */
public class Whip extends CondimentDecorator {
    private String desc = "Whip";
    private Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", " + desc;
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.85;
    }
}
