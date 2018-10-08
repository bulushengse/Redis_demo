package main;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
	private static volatile JedisPool jedisPool = null;
	
	private JedisPoolUtil(){}
	
	//获取连接池
	public static JedisPool getJedisPoolInstance()
	{
		if(null == jedisPool)
		{
			synchronized (JedisPoolUtil.class)
			{
				if(null == jedisPool)
				{
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					 // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
					poolConfig.setBlockWhenExhausted(true);
		            // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
					poolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		            // 是否启用pool的jmx管理功能, 默认true
					poolConfig.setJmxEnabled(true);
		            // 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
					poolConfig.setMaxIdle(8);
		            // 最大连接数, 默认8个
					poolConfig.setMaxTotal(200);
		            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
					poolConfig.setMaxWaitMillis(1000 * 100);
		            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
					poolConfig.setTestOnBorrow(true);

					jedisPool = new JedisPool(poolConfig,"192.168.1.84",6379);
				}
			}
		}
		return jedisPool;
	}

	//回收连接
	public static void release(JedisPool jedisPool,Jedis jedis)
	{
		if(null != jedis)
		{
			jedisPool.returnResourceObject(jedis);
		}
		
	}
	
	
	
}
