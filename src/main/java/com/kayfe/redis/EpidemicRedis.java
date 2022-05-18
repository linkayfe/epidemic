package com.kayfe.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class EpidemicRedis {

    private static JedisPool pool = new JedisPool("127.0.0.1",5345);
//    private static JedisPool pool = new JedisPool("175.178.183.118",5345);
    public static Jedis getJedis(){
        return pool.getResource();
    }


}
