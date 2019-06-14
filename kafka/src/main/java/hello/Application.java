package hello;

import java.util.concurrent.CountDownLatch;
import reactor.core.Disposable;

public class Application {

  private static final String BOOTSTRAP_SERVERS = "172.19.0.1:9092";
  private static final String TOPIC = "demo-topic";

  public static void main(String args[]) {
    int count = 20;
    CountDownLatch latch = new CountDownLatch(count);
    SampleConsumer consumer = new SampleConsumer(BOOTSTRAP_SERVERS);
    Disposable disposable = consumer.consumeMessages(TOPIC, latch);
    // disposable.dispose();
  }
}
