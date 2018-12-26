package edu.csu.shan.queue;

import edu.csu.shan.pojo.LineMsg;
import redis.clients.jedis.Jedis;
import seg.Segment;

import java.util.concurrent.atomic.AtomicInteger;

import static edu.csu.shan.queue.Constans.*;

public class Consumer {
    private static MyBlockingQueue blockingQueue = new MyBlockingQueue();
    private static AtomicInteger COUNT = new AtomicInteger(0);

    //  消费后的数据存hashMap
    public static void consumer() {
        for (int i = 0; i < THREAD_NUM; i++) {    // 开了20个线程
            executorService.submit(() -> {
                try (Jedis jedis = pool.getResource()) {
                    jedis.auth("root");
                    //    while (blockingQueue.fairQueue.size() > 0) {
                    while (COUNT.get() < 151160) {
                        LineMsg lineMsg = blockingQueue.consume();
                     /*     synchronized (Segment.class) {
                              Segment.setupRedisClient(jedis);
                              String extract_result = Segment.extractWords(lineMsg.text);
                              Constans.EXTRACT_WORD_MAP.put(lineMsg.lineCount, extract_result);
                              System.out.println(Thread.currentThread() + " 正在消费:" + lineMsg + "  " + extract_result);

                              //   }
                          }*/
                        System.out.println(Thread.currentThread().getName() + " 正在消费:" + lineMsg + "  " + jedis.hget("我们", "mi"));
                        COUNT.incrementAndGet();
                    }
                    jedis.close();
                    countDownLatch.countDown();
                }
            });
        }
    }


    public static void main(String[] args) {
        //Producer.produce("data/test-text.txt");
        Producer.produceNovel();
        long t1 = System.currentTimeMillis();
        Consumer.consumer();
        try {
            countDownLatch.await();
            System.out.println("开的" + THREAD_NUM + "个子线程全部完成");
            //System.out.println(Constans.EXTRACT_WORD_MAP.size());
            executorService.shutdown();
            pool.destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("总计耗时:  " + (System.currentTimeMillis() - t1) + " ms");
    }
}
