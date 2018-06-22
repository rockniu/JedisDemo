import java.util.Date;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;


public class App {

	public static final String HOST_NAME="120.79.213.203";
	public static final int PORT=6379;
	public static final int PORT2=6380;
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println(new Date());
		Jedis  jedis = new Jedis (HOST_NAME,PORT); 
		jedis.auth("zaqxsw@555.com");
		jedis.connect();
		System.out.println("Connected.");
		
		String testKey="str2";
		
		jedis.set(testKey, "Rock's PC");
		//jedis.hset(key, field, value);
		
	
		System.out.println("--------------All Keys-------------");
		Set<String> keys = jedis.keys("*");
		for (String key : keys){
			System.out.println("Key name: " + key + ", key type: " + jedis.type(key));
		}
		System.out.println("----------------------------------");
		if (jedis.exists(testKey)){
			jedis.expire(testKey, 50);
			jedis.persist(testKey);
			jedis.del(testKey);
		}		 
		
		//Add string keys
		for (int i=0;i<10000;i++){
			jedis.set("Test.Key."+i, ""+i);
			Thread.sleep(1);
		}
		
		//Add list keys
		String listKey="MyList";
		for (int i=0;i<100;i++){
			jedis.lpush(listKey, ""+i);
		}
		jedis.expire(listKey, 100);
		//Add set keys
		String setKey="MySet";
		for (int i=0;i<100;i++){
			jedis.sadd(setKey, "A"+i);
		}
		jedis.srem(setKey, "A10");
		
		
		
	
		Set<String> mySet=jedis.smembers(setKey);
		System.out.println("My set item size:" + jedis.scard(setKey));
		//Add hash keys
		String hashKey="MyHash";
		for (int i=0;i<100;i++){
			jedis.hset(hashKey, "Property"+i, "Value"+i);
		}
		
		//bitget
		String bitKey="MyBit";
		jedis.setbit(bitKey, 10, true);
		jedis.setbit(bitKey, 9, true);
		
		//Sorted set(Zset), zadd
		String zKey="MySortedSet";
		jedis.zadd(zKey, 0, "XXX");
		jedis.zadd(zKey, 1.1, "XXX");
	
		
		Map<String, String> map = jedis.hgetAll(hashKey);
		System.out.println("My hash field count:" +map.size());
		System.out.println("key count:" + jedis.dbSize());
		 //jedis.flushAll();		 
		 System.out.println("key count:" + jedis.dbSize());
		 
		
		 
//		 while (true){
//			 Thread.sleep(1000);
//		 }
		 System.out.println("Exit.");
		 jedis.disconnect();
		 System.out.println("Disconnected.");
	}

}
