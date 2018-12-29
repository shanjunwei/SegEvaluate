package edu.csu.shan.queue;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ȫ�ֱ���
 */
public class Constans {
    public static final int THREAD_NUM = 25;  // �߳���
    //���ӱ��ص� Redis ����
    public static final Jedis redis = new Jedis("localhost");   // redis client
    //  �ִʺ������
    public static final Map<Integer, String> EXTRACT_WORD_MAP = new ConcurrentHashMap<>();
    // դ��
    public static final CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);  // դ�������Ʋ����߳�ȫ������֮��ŷ������߳�

    public static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");   // �����ڶ���߳���ʹ��һ��redisʵ��


    public static String CORPUS_INPUT_PATH = "D:\\HanLP\\novel\\�����˲�.txt";
}
