package ml.idream.model.decorator;

import ml.idream.model.decorator.entity.DarkRoast;
import ml.idream.model.decorator.entity.Mocha;
import ml.idream.model.decorator.entity.Whip;
/*
* MainApp
* */
public class MainApp {

    public static void main(String[] args) {
        Beverage myCoffe = new DarkRoast();
        myCoffe = new Mocha(myCoffe);
        myCoffe = new Whip(myCoffe);
        System.out.println(myCoffe.getDescription() + ";" + myCoffe.cost());
    }

}
