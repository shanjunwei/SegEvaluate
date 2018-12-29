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
 * 全局变量
 */
public class Constans {
    public static final int THREAD_NUM = 25;  // 线程数
    //连接本地的 Redis 服务
    public static final Jedis redis = new Jedis("localhost");   // redis client
    //  分词后存这里
    public static final Map<Integer, String> EXTRACT_WORD_MAP = new ConcurrentHashMap<>();
    // 栅栏
    public static final CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);  // 栅栏，控制并发线程全部结束之后才返回主线程

    public static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");   // 不能在多个线程中使用一个redis实例


    public static String CORPUS_INPUT_PATH = "D:\\HanLP\\novel\\天龙八部.txt";
}
