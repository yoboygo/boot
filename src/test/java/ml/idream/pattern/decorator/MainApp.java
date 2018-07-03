package ml.idream.pattern.decorator;

import ml.idream.pattern.decorator.entity.DarkRoast;
import ml.idream.pattern.decorator.entity.Mocha;
import ml.idream.pattern.decorator.entity.Whip;
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
