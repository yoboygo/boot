package ml.idream.blog;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class BlogService {


    @Async
    public Future<Map<String,Object>> printMsg(String msg) throws InterruptedException {
        Map<String,Object> datas = new HashMap<>();
        Future<Map<String,Object>> ret = new AsyncResult<Map<String,Object>>(datas);
        for(int i = 0; i < 10; ++i){
            System.out.println(Thread.currentThread().getName() + ":" + i + ":" + msg);
            datas.put("" + i ,i + ":" + msg);
            Thread.sleep(1000);
        }
        return ret;
    }

}
