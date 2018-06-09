package ml.idream;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestRandom {

    @Test
    public void testRandomNumber(){

        for(int j = 0; j < 10000000 ; ++j){
            List<Integer> ret = new ArrayList<Integer>();
            int sum = 0 ;
            int counter = 0;
            for(int i = 1; i <= 6 ; ++i) {
                if(i == 5){

                    int item = RandomUtils.nextInt(10,20);
                    item = 200 - sum - item -  (20 - counter * 10);
                    if(item < 10){
                        item = 10;
                    }
                    sum += item;
                    ret.add(item);

                }else if(i == 6){

                    int item = 200 - sum;
                    sum += item;
                    ret.add(item);
                }else{
                    int item = RandomUtils.nextInt(33,40);
                    sum += item;
                    ret.add(item);
                }
                if(RandomUtils.nextInt(1,1000) % 2 == 0){
                    if(counter < 2){
                        ++counter;
                        sum += 10;
                        ret.add(10);
                    }

                }
            }

            boolean flag = true;
            for (int item : ret){
                if (item > 50 || item < 10){
                    flag = false;
                    System.err.println(j + ":sum = " + sum + " item : " + ret.toString());
                    break;
                }
            }
//            System.out.println(j + ":sum = " + sum + " item : " + ret.toString());
            /*
            if(flag){
                System.out.println(j + ":sum = " + sum + " item : " + ret.toString());
            }else{
                System.err.println(j + ":sum = " + sum + " item : " + ret.toString());
            }
            */

        }

    }
}
