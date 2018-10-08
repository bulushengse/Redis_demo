package com.zbc.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.zbc.bean.User;
import com.zbc.redis.RedisCacheManager2;
import com.zbc.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;


public class UserController {
	
	
	//模拟业务控制层。。。
	//spring-context-support这个jar包中含有Spring对于缓存功能的抽象封装接口。
	
	/**
	 * 获取服务层实例
	 * @param name
	 * @return
	 */
	public Object getBean(String name) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/ApplicationContext.xml");  
	    Object service = context.getBean((String) name);
	    return service;
	}
	

	/**
	 * 1.spring提供的缓存模版StringRedisTemplate的方法测试
	 * 
	 *	可封装缓存工具类RedisCacheManager.java 
	 */
	@Test
	public void testSpringRedis() {
		
		StringRedisTemplate	redisTemplate = (StringRedisTemplate) getBean("redisTemplate");
		
		redisTemplate.opsForValue().set("myStr", "skyLine");    
		System.out.println(redisTemplate.opsForValue().get("myStr"));     
		redisTemplate.delete("myStr");
		System.out.println(redisTemplate.opsForValue().get("myStr")); 
		
		// List读写 
		redisTemplate.delete("myList"); 
		redisTemplate.opsForList().rightPush("myList", "111");        
		redisTemplate.opsForList().rightPush("myList", "222");      
		redisTemplate.opsForList().leftPush("myList", "222");
		User user = new User();
		redisTemplate.opsForList().leftPush("myList", user.toString());
		List<String> listCache = redisTemplate.opsForList().range("myList", 0, -1);      
		for (String s : listCache) {  
			System.out.println(s);    
		}        

		// Set读写       
		redisTemplate.delete("mySet");        
		redisTemplate.opsForSet().add("mySet", "A");        
		redisTemplate.opsForSet().add("mySet", "B");        
		redisTemplate.opsForSet().add("mySet", "B");        
		Set<String> setCache = redisTemplate.opsForSet().members("mySet");        
		for (String s : setCache) {
			System.out.println(s);        
		}        
		
		// HashMap读写        
		redisTemplate.delete("myMap");        
		redisTemplate.opsForHash().put("myMap", "BJ", "北京");        
		redisTemplate.opsForHash().put("myMap", "SH", "上海");        
		redisTemplate.opsForHash().put("myMap", "HN", "河南");        
		Map<Object, Object> hashCache = redisTemplate.opsForHash().entries("myMap");        
		for (Map.Entry entry : hashCache.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());        
		}        

	}
	
	/**
	 * spring提供的缓存管理器RedisCacheManager的测试
	 * 
	 * 需在service层添加注解。。。
	 * 
	 * 对service层返回的list集合没有提供解决方法无法缓存！！！
	 */
	@Test
	public void findUser() throws Exception {
		UserService userService = (UserService) getBean("userService");
		User user = new User();
		user.setUSER_ID("1");
		System.out.println("--------------------第一次查询开始--------------------------");
		User data1 = userService.findById(user);
		//User data1 = userService.findById2(user);
		System.out.println("无缓存查询用户数据---"+data1);
		System.out.println("--------------------第一次查询结束--------------------------");
		System.out.println("--------------------第二次查询开始--------------------------");
		User data2 = userService.findById(user);
		//User data1 = userService.findById2(user);
		System.out.println("缓存查询用户数据---"+data2);
		System.out.println("--------------------第二次查询结束--------------------------");
	}
	

	@Test
	public void delCache() throws Exception {
		UserService userService = (UserService) getBean("userService");
		User user = new User();
		user.setUSER_ID("1");
		System.out.println("--------------------第一次查询开始--------------------------");
		User data1 = userService.findById(user);
		System.out.println("无缓存查询用户数据---"+data1);
		System.out.println("--------------------第一次查询结束--------------------------");
		
		userService.delCache(user);
		System.out.println("------已删除缓存数据");
		
		System.out.println("--------------------第二次查询开始--------------------------");
		User data2 = userService.findById(user);
		System.out.println("无缓存查询用户数据---"+data2);
		System.out.println("--------------------第二次查询结束--------------------------");
	}
	
	
	/**
	 * 2.自定义缓存管理器RedisCacheManager2的测试（推荐）
	 * 
	 * 扩展性好
	 */
	@Test
	public void testCacheManager() throws Exception {
		UserService userService = (UserService) getBean("userService");
		List<User> list = userService.listAll(null);
		//转换JSON数据
//		List<String> users = new ArrayList<String>();
//		Iterator<User> it = list.iterator();
//		User ur;
//		while(it.hasNext()) {
//			ur = it.next();
//			
//			JSONObject json = new JSONObject(ur); 
//			
//			users.add(json.toString());
//		}
		
		RedisCacheManager2 redisCacheManager2 = (RedisCacheManager2) getBean("redisrCacheManager2");
		
		redisCacheManager2.setList("users", list);
		
		List<User> list2 = redisCacheManager2.getList("users");
		
		for (User u : list2) {  
			
			System.out.println(u.toString());    
		} 
	}
	
}
