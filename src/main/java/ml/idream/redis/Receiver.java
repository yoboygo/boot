package ml.idream.redis;

import java.util.concurrent.CountDownLatch;

public class Receiver {

    CountDownLatch latch;

    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) {
        latch.countDown();
        System.out.println("收到消息 " + latch.getCount() + "---> " + message);
    }
}
