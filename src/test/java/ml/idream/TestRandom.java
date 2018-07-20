package ml.idream;

import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                    }else if(item > 50){
                        item = 50;
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

            for (int item : ret){
                if (item > 50 || item < 10){
                    System.err.println(j + ":sum = " + sum + " item : " + ret.toString());
                    break;
                }
            }

            System.out.println(j + ":sum = " + sum + " item : " + ret.toString());

        }

    }

    /*
    *
    * */
    @Test
    public void testInterface() throws IOException {
        String url = "http://10.10.8.22:8081/web-train-interface/SysFranchisee/querySysFranchiseeAll.do";
        HttpClient hc = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        HttpResponse response = hc.execute(post);
        String ret = EntityUtils.toString(response.getEntity());
        System.out.println(ret);
    }

    @Test
    public void testTime(){
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        date.getTime();
        System.out.println(cal.getTimeInMillis());
        System.out.println(cal.getTime().getTime());
    }
}
