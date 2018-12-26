package edu.csu.shan.queue;

import edu.csu.shan.pojo.LineMsg;
import redis.clients.jedis.Jedis;
import seg.Segment;

import java.util.concurrent.atomic.AtomicInteger;

import static edu.csu.shan.queue.Constans.*;

public class Consumer {
    private static MyBlockingQueue blockingQueue = new MyBlockingQueue();
    private static AtomicInteger COUNT = new AtomicInteger(0);

    //  ���Ѻ�����ݴ�hashMap
    public static void consumer() {
        for (int i = 0; i < THREAD_NUM; i++) {    // ����20���߳�
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
                              System.out.println(Thread.currentThread() + " ��������:" + lineMsg + "  " + extract_result);

                              //   }
                          }*/
                        System.out.println(Thread.currentThread().getName() + " ��������:" + lineMsg + "  " + jedis.hget("����", "mi"));
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
            System.out.println("����" + THREAD_NUM + "�����߳�ȫ�����");
            //System.out.println(Constans.EXTRACT_WORD_MAP.size());
            executorService.shutdown();
            pool.destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("�ܼƺ�ʱ:  " + (System.currentTimeMillis() - t1) + " ms");
    }
}
