package com.redis.manager;

import com.redis.manager.bean.Host;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集群连接管理
 * @author jiafengshen 2017/12/19.
 */
public class JedisManager {

    /**
     * 缓存
     */
    private static final Map<Host,JedisPool> jedisPools  = new ConcurrentHashMap<Host, JedisPool>();

    /**
     * 初始化
     * @param host
     * @return
     */
    public static Jedis getJedis(Host host) {
        if(jedisPools.containsKey(host)){
            return jedisPools.get(host).getResource();
        }
        synchronized (host.toString().intern()){
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(5);
            //config.setMaxWait(1000l);
            config.setTestOnBorrow(false);
            JedisPool jedisPool = new JedisPool(config, host.getIp(), host.getPort());
            jedisPools.put(host,jedisPool);
            return jedisPools.get(host).getResource();
        }
    }
}
