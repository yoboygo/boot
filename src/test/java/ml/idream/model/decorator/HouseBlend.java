package ml.idream.model.decorator;

public class HouseBlend extends Beverage {
    public HouseBlend() {
        description = "House Bleand Coffee";
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
