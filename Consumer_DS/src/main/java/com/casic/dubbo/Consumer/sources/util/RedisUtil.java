package com.casic.dubbo.Consumer.sources.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Redis 工具类 
 */
public class RedisUtil {  
  
    protected static ReentrantLock lockPool = new ReentrantLock();  
    protected static ReentrantLock lockJedis = new ReentrantLock();  
  
  
    //Redis服务器IP  
 //   private static String ADDR_ARRAY = "10.153.4.8:6379"; 
    private static String ADDR_ARRAY = "172.17.70.18:6379";
  //  private static String ADDR_ARRAY = "127.0.0.1:6379"; 
  
    //Redis的端口号  
    private static int PORT = 6379;  
  
    //访问密码  
    //可用连接实例的最大数目，默认值为8；  
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
    private static int MAX_ACTIVE = 8;  
  
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。  
    private static int MAX_IDLE = 8;  
  
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；  
    private static int MAX_WAIT = 3000;  
  
    //超时时间  
    private static int TIMEOUT = 10000;  
  
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
    private static boolean TEST_ON_BORROW = false;  
    private static boolean isCluster = false;  //是否是集群
  
    private static JedisPool jedisPool = null;  
    private static ShardedJedisPool shardedJedisPool  = null;  
  
    /** 
     * redis过期时间,以秒为单位 
     */  
    public final static int EXRP_HOUR = 60 * 60;            //一小时  
    public final static int EXRP_DAY = 60 * 60 * 24;        //一天  
    public final static int EXRP_MONTH = 60 * 60 * 24 * 30; //一个月  
    {
    	initialPool();
    }
    /** 
     * 初始化Redis连接池 
     */  
    private static final List<JedisShardInfo> getseeds(){
		List<JedisShardInfo> addresses = new ArrayList<JedisShardInfo>();
		boolean flag = false;
		String hosts = RedisUtil.ADDR_ARRAY;
		if(null != hosts && hosts.length() > 0){
			String[] arr = hosts.split(",");
			if(arr.length > 0){
				String host;
				for(int i = 0; i < arr.length; i++){
					host = arr[i];
					String[] tmp = host.split(":");
					if(tmp.length == 2){
						addresses.add(new JedisShardInfo(tmp[0] , Integer.parseInt(tmp[1])));
					}
				}
			}
		}else{
			throw new RuntimeException("create jedis cluster connection failure.");
		}
		return addresses;
	}
	
	private static void initialPool() {  
        try { 
        	List<JedisShardInfo> shards = getseeds();
        	if(isCluster){
        		
        		JedisPoolConfig poolConfig = new JedisPoolConfig();
        		poolConfig.setMaxTotal(MAX_ACTIVE);  
        		poolConfig.setMaxIdle(MAX_IDLE);  
        		poolConfig.setMaxWaitMillis(MAX_WAIT);  
        		poolConfig.setTestOnBorrow(TEST_ON_BORROW);  
        		Set<JedisShardInfo> nodes = new HashSet<>();    
                if(shards!=null&&!shards.isEmpty()){
                	nodes.addAll(shards);
                }
                shardedJedisPool  = new ShardedJedisPool(poolConfig,shards);
        	}else{
        		JedisShardInfo jedisShardInfo=shards.get(0);
            JedisPoolConfig config = new JedisPoolConfig();  
            config.setMaxTotal(MAX_ACTIVE);  
            config.setMaxIdle(MAX_IDLE);  
            config.setMaxWaitMillis(MAX_WAIT);  
            config.setTestOnBorrow(TEST_ON_BORROW);  
            jedisPool = new JedisPool(config, jedisShardInfo.getHost(), jedisShardInfo.getPort(), TIMEOUT, null); 
        	}
        } catch (Exception e) {  
           e.printStackTrace();
        }  
    } 
  
  
    /** 
     * 在多线程环境同步初始化 
     */  
    private static synchronized void poolInit() { 
    	if(isCluster){
    		if(shardedJedisPool==null){
    			initialPool();  
    		}
    	}else{
    		 if (jedisPool == null) {    
    	            initialPool();  
    	        }  
    	}
       
    }  
  
      
    /** 
     * 同步获取Jedis实例 
     * @return Jedis 
     */  
    public synchronized static ShardedJedis getShardedJedis() {  
    	
        if (shardedJedisPool == null) {    
            poolInit();  
        }  
        ShardedJedis jedis = null;  
        try {    
            if (shardedJedisPool != null) {    
                jedis = shardedJedisPool.getResource();   
            }  
        } catch (Exception e) {    
        	e.printStackTrace();
        }finally{  
        	returnSharedJedisResource(jedis);  
        }  
        return jedis;  
    }    
    /** 
     * 同步获取Jedis实例 
     * @return Jedis 
     */  
    public synchronized static Jedis getJedis() {    
    	if (jedisPool == null) {    
    		poolInit();  
    	}  
    	Jedis jedis = null;  
    	try {    
    		if (jedisPool != null) {    
    			jedis = jedisPool.getResource();   
    		}  
    	} catch (Exception e) {    
    		e.printStackTrace();
    	}finally{  
    		returnResource(jedis);  
    	}  
    	return jedis;  
    }    
    /** 
     * 同步获取Jedis实例 
     * @return Jedis 
     */  
    public synchronized static Set<String> keys(String pattern) { 
    	Set<String> keys=new HashSet<>();
    	if(isCluster){
    		Collection<Jedis> jedisList = getShardedJedis().getAllShards();//获取所有的缓存实例
    			Iterator<Jedis> it = jedisList.iterator();
    			while (it.hasNext()) {
					Jedis jedis = (Jedis) it.next();
					keys.addAll(jedis.keys(pattern));
				}
    		
    	}else{
    		keys = getJedis().keys(pattern);
    	}
    	return keys;
    }    
  
    /** 
     * 释放jedis资源 
     * 
     * @param jedis 
     */  
    public static void returnResource(final Jedis jedis) {  
        if (jedis != null && jedisPool != null) {  
            jedisPool.returnResource(jedis);  
        }  
    }  
    /** 
     * 释放jedis资源 
     * 
     * @param jedis 
     */  
    public static void returnSharedJedisResource(final ShardedJedis jedis) {  
    	if (jedis != null && shardedJedisPool != null) {  
    		shardedJedisPool.returnResource(jedis);  
    	}  
    }  
  
    /** 
     * 设置 String 
     * 
     * @param key 
     * @param value 
     */  
    public synchronized static void setString(String key, String value) {  
        try { 
        	if(isCluster){
        		  value = StringUtil.isEmpty(value) ? "" : value;  
                  getShardedJedis().set(key, value);
        	}else{
        		  value = StringUtil.isEmpty(value) ? "" : value;  
                  getJedis().set(key, value);
        	}
        } catch (Exception e) {  
          e.printStackTrace();
        }  
    }  
  
    /** 
     * 设置 过期时间 
     * 
     * @param key 
     * @param seconds 以秒为单位 
     * @param value 
     */  
    public synchronized static void setString(String key, int seconds, String value) {  
        try {  
        	if(isCluster){
        		 value = StringUtil.isEmpty(value) ? "" : value;  
                 getShardedJedis().setex(key, seconds, value);  
        	}else{
        		 value = StringUtil.isEmpty(value) ? "" : value;  
                 getJedis().setex(key, seconds, value);  
        	}
           
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  

    /** 
     * 获取String值 
     * 
     * @param key 
     * @return value 
     */  
    public synchronized static String getString(String key) { 
    	if(isCluster){
    		if(getShardedJedis()==null||!getShardedJedis().exists(key)){
    			return null;
    		}
    		return getShardedJedis().get(key);
    	}else{
    		if (getJedis() == null || !getJedis().exists(key)) {  
        		return null;  
        	}  
        	return getJedis().get(key); 	
    	}
    	 
    }

	public synchronized static void flushAll() {
		
		 if(isCluster){
			Collection<Jedis> jedisList = getShardedJedis().getAllShards();//获取所有的缓存实例
 			Iterator<Jedis> it = jedisList.iterator();
	 			while (it.hasNext()) {
					Jedis jedis = (Jedis) it.next();
					jedis.flushAll();
				}		 
 			}else{
			  getJedis().flushAll(); 
		 }
		
	}  
	public static void main(String[] args) {
		RedisUtil.getJedis().set("aa", "aa");
	}

} 