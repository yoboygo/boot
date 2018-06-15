package ml.idream.model.decorator;
/*
* 饮料
* */
public abstract class Beverage {
    String description = "Unknown Beverage";
    public String getDescription(){
        return description;
    }
    public abstract double cost();
}
