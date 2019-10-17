package com.xdx.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService <K,V>{

	@Autowired
	private RedisTemplate<K, V> redisTemplate;

	/**
	 * 将java对象序列化后存入redis 不设置超时时间
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putCache(final K key, final V value) {
		try {
			return putCache(key, value, null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将java对象序列化后存入redis
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean putCache(final K key, final V value, Long timeout) {
		try {
			if (timeout != null) {
				redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
			} else {
				redisTemplate.opsForValue().set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将序列化字符串转换为java对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getCache(final K key) {
		// 反序列化
		Object obj = null;
		try {
			obj = redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 指定缓存失效时间 * * @param key 键 * @param time 时间(秒) * @return
	 */
	public boolean expire(K key, long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据key 获取过期时间 * * @param key 键 不能为null * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(K key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 判断key是否存在 * * @param key 键 * @return true 存在 false不存在
	 */
	public boolean hasKey(K key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除缓存 * * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void del(K... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	// ============================String============================= /** * 普通缓存获取
	// * * @param key 键 * @return 值 */
	 public Object get(K key) {
		return key ==
	 null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 普通缓存放入 * * @param key 键 * @param value 值 * @return true成功 false失败
	 */
	public boolean set(K key, V value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存放入并设置时间 * * @param key 键 * @param value 值 * @param time 时间(秒) time要大于0
	 * 如果time小于等于0 将设置无限期 * @return true成功 false 失败
	 */
	public boolean set(K key, V value, long time) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 递增 * * @param key 键 * @param delta 要增加几(大于0) * @return
	 */
	public long incr(K key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减 * * @param key 键 * @param delta 要减少几(小于0) * @return
	 */
	public long decr(K key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	// ================================Map================================= /** *
//	 HashGet * * @param key 键 不能为null * @param item 项 不能为null * @return 值 */
	public Object hget(K key, String item) { return
			redisTemplate.opsForHash().get(key, item); }

	/**
	 * 获取hashKey对应的所有键值 * * @param key 键 * @return 对应的多个键值
	 */
	public Map<Object, Object> hmget(K key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * HashSet * * @param key 键 * @param map 对应多个键值 * @return true 成功 false 失败
	 */
	public<HK,HV> boolean hmset(K key, Map<? extends HK, ? extends HV> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * HashSet 并设置时间 * * @param key 键 * @param map 对应多个键值 * @param time 时间(秒)
	 * * @return true成功 false失败
	 */
	public<HK,HV>  boolean hmset(K key, Map<? extends HK, ? extends HV> map, long time) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建 * * @param key 键 * @param item 项 * @param value 值
	 * * @return true 成功 false失败
	 */
	public<HK,HV>  boolean hset(K key, HK item, HV value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建 * * @param key 键 * @param item 项 * @param value 值
	 * * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间 * @return true 成功 false失败
	 */
	public<HK,HV>  boolean hset(K key, HK item, HV value, long time) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除hash表中的值 * * @param key 键 不能为null * @param item 项 可以使多个 不能为null
	 */
	public void hdel(K key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}

	/**
	 * 判断hash表中是否有该项的值 * * @param key 键 不能为null * @param item 项 不能为null * @return
	 * true 存在 false不存在
	 */
	public boolean hHasKey(K key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回 * * @param key 键 * @param item 项 * @param by
	 * 要增加几(大于0) * @return
	 */
	public double hincr(K key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	/**
	 * hash递减 * * @param key 键 * @param item 项 * @param by 要减少记(小于0) * @return
	 */
	public double hdecr(K key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}

	// ============================set=============================

	/**
	 * 根据key获取Set中的所有值 * * @param key 键 * @return
	 */
	public Set<V> sGet(K key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在 * * @param key 键 * @param value 值 * @return true 存在
	 * false不存在
	 */
	public boolean sHasKey(K key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将数据放入set缓存 * * @param key 键 * @param values 值 可以是多个 * @return 成功个数
	 */
	public long sSet(K key, V... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将set数据放入缓存 * * @param key 键 * @param time 时间(秒) * @param values 值 可以是多个
	 * * @return 成功个数
	 */
	public long sSetAndTime(K key, long time, V... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0)
				expire(key, time);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取set缓存的长度 * * @param key 键 * @return
	 */
	public long sGetSetSize(K key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 移除值为value的 * * @param key 键 * @param values 值 可以是多个 * @return 移除的个数
	 */
	public long setRemove(K key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// ===============================list=================================

	/**
	 * 获取list缓存的内容 * * @param key 键 * @param start 开始 * @param end 结束 0 到 -1代表所有值
	 * * @return
	 */
	public List<V> lGet(K key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list缓存的长度 * * @param key 键 * @return
	 */
	public long lGetListSize(K key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值 * * @param key 键 * @param index 索引 index>=0时， 0 表头，1
	 * 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推 * @return
	 */
	public V lGetIndex(K key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将list放入缓存 * * @param key 键 * @param value 值 * @param time 时间(秒) * @return
	 */
	public boolean leftPush(K key, V value) {
		try {
			redisTemplate.opsForList().leftPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存 * * @param key 键 * @param value 值 * @param time 时间(秒) * @return
	 */
	public boolean leftPush(K key, V value, long time) {
		try {
			redisTemplate.opsForList().leftPush(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存 * * @param key 键 * @param value 值 * @param time 时间(秒) * @return
	 */
	public boolean leftPushAll(K key, List<V> value) {
		try {
			redisTemplate.opsForList().leftPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存 * * @param key 键 * @param value 值 * @param time 时间(秒) * @return
	 */
	public boolean leftPushAll(K key, List<V> value, long time) {
		try {
			redisTemplate.opsForList().leftPushAll(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据索引修改list中的某条数据 * * @param key 键 * @param index 索引 * @param value 值
	 * * @return
	 */
	public boolean lUpdateIndex(K key, long index, V value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 移除N个值为value * * @param key 键 * @param count 移除多少个 * @param value 值 * @return
	 * 移除的个数
	 */
	public long lRemove(K key, long count, Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 队列右弹出元素   
	 * 
	 * @param key
	 * @return
	 */
	public Object rPop(K key) {
		try {
			Object object = redisTemplate.opsForList().rightPop(key);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Set<K> getListBehind(K key) {
		return redisTemplate.keys((K) (key+"*"));
	}

}
