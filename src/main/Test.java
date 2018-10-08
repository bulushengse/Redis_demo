package main;

import java.util.List;

import redis.clients.jedis.Jedis;

public class Test {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.1.84", 6379);

		System.out.println("------" + jedis.ping());

		// jedis.append("aaa", "bbb");
		jedis.set("111", "222");
		System.out.println(jedis.exists("1111"));
		System.out.println(jedis.get("111"));
		// 以秒为单位返回 key 的剩余过期时间。
		System.out.println(jedis.ttl("111"));
		// jedis.mset("str1","v1","str2","v2","str3","v3");
		// System.out.println(jedis.mget("str1","str2","str3"));
		// 先向key java framework中存放三条数据
		// jedis.lpush("mylist","v1","v2","v3","v4","v5");
		// 再取出所有数据jedis.lrange是按范围取出，
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
		List<String> list = jedis.lrange("mylist", 0, -1);
		for (String element : list) {
			System.out.println(element);
		}

	}



}
