package edu.csu.shan.queue;

import edu.csu.shan.pojo.LineMsg;
import edu.csu.shan.util.FileUtil;
import redis.clients.jedis.Jedis;
import seg.Segment;

import java.util.*;
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
                    System.out.println("jedis" + Thread.currentThread().getName());
                    jedis.auth("root");
                    while (COUNT.incrementAndGet() <= 2533709) {
                        LineMsg lineMsg = blockingQueue.consume();
                        String extract_result = Segment.extractWords(lineMsg.text, jedis);
                        Constans.EXTRACT_WORD_MAP.put(lineMsg.lineCount, extract_result);
                        System.out.println(Thread.currentThread().getName() + " ����:" + lineMsg.lineCount+":"+ lineMsg.text + "->" + extract_result);
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
        System.out.println("��������Ϣ������:" + MyBlockingQueue.fairQueue.size());

        // System.exit(0);

        long t1 = System.currentTimeMillis();
        Consumer.consumer();
        try {
            countDownLatch.await();
            System.out.println("����" + THREAD_NUM + "�����߳�ȫ�����");
            System.out.println("���Map�д�ŵ�����:" + Constans.EXTRACT_WORD_MAP.size());
            executorService.shutdown();
            pool.destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //  �����ս��д���ļ���
        //  ��map  �����д����
        List<Map.Entry<Integer,String>> list = new ArrayList<>(Constans.EXTRACT_WORD_MAP.entrySet());
        //��������
        Collections.sort(list, Comparator.comparing(Map.Entry::getKey));
        FileUtil.writeMapResultToFile("data/test001.txt",list);


        System.out.println("�ܼƺ�ʱ:  " + (System.currentTimeMillis() - t1) + " ms");
    }
}
