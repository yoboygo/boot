package ml.idream.pattern.decorator;
/*
* 饮料基类
* */
public abstract class Beverage {
    String description = "Unknown Beverage";
    public String getDescription(){
        return description;
    }
    public abstract double cost();

    protected void setDescription(String description){
        this.description = description;
    }
}
