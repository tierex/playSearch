package services.cache.impl;

import redis.clients.jedis.JedisPoolConfig;
import scala.concurrent.ExecutionContext;
import services.cache.Cache;

public class CacheFactory {

    private static CacheFactory instance = null;
    private final static Object lock = new Object();

    //Todo read cache configuration from play configuration class
    public static void configure(play.Configuration conf, ExecutionContext context) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new CacheFactory(context);
                }
            }
        }
    }

    public static CacheFactory getInstance() {
        return instance;
    }

    private Cache cache;

    private CacheFactory(ExecutionContext context) {
        //Todo move to the play configuration
        JedisPoolConfig pool = new JedisPoolConfig();
        String host = "localhost";
        int port = 6379;
        this.cache = RedisCache.getInstance(context, pool, host, port);
    }

    public Cache getCache() {
        return cache;
    }
}
