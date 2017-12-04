package com.steam.util.cache;

import com.steam.util.string.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 * ClassName: CountCache <br/>
 * Description: CountCache  基于类型和sessionId计数缓存，用于各种因为多线程导致计数不准确 <br/>
 * Copyright: Copyright (c) 2017 <br/>
 * Company:Nanjing Lending Cloud Finance Information Service <br/>
 * Date 2017年6月26日 下午8:09:23 <br/>
 *
 * @author yinchuang
 * @version 1.0
 */
public class CountCache {


    /**
     * @Fields poolSize : 全局使用该缓存最大线程数,目前写死，如果后期需要调整可以放在配置文件
     */
    public static int poolSize = 10;
    private static ExecutorService pool = null;
    /**
     * @Fields timeOut : 超时时间1天，下次同一个类型调用时会删除过期缓存
     */
    private static final long timeOut = 86400000L;

    /**
     * @Fields map : 静态化类型缓存<buildType,<sessionId,缓存对象>>
     */
    private Map<String, Map<String, CacheBean>> map = new HashMap<String, Map<String, CacheBean>>();

    /**
     * @Fields rwlock : 读写锁，保证读写分离 目前用这种锁，等部署分布式时需要换成缓存锁
     */
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();
    /**
     * @Fields readLock : 读锁
     */
    private Lock readLock = null;
    /**
     * @Fields writeLock : 写锁
     */
    private Lock writeLock = null;
    /**
     * @Fields cache : 静态私有对象
     */
    private static CountCache cache;

    /**
     * 私有构造器
     */
    private CountCache() {
        readLock = rwlock.readLock();
        writeLock = rwlock.writeLock();
        pool = Executors.newFixedThreadPool(poolSize);
    }

    ;

    /**
     * Title: getInstance <br/>
     * Description: 实例化方法 <br/>
     *
     * @return StaticGenerateCache    返回类型
     */
    public static CountCache getInstance() {
        if (cache == null) {
            synchronized (CountCache.class) {
                if (cache == null) {
                    cache = new CountCache();
                }
            }
        }
        return cache;
    }

    /**
     * Title: getExecutorService <br/>
     * Description: 获取线程池 <br/>
     *
     * @return ExecutorService    返回类型
     */
    public ExecutorService getExecutorService() {
        return pool;
    }

    /**
     * Title: addCache <br/>
     * Description: 调用缓存时先调用的方法 <br/>
     *
     * @param type
     * @param sessionId
     * @return void    返回类型
     */
    public void addCache(String type, String sessionId) {
        if (!StringUtil.isEmpty(type) && !StringUtil.isEmpty(sessionId)) {
            try {
                writeLock.lock();
                if (map.containsKey(type)) {//是否已经有这个类型
                    Map<String, CacheBean> cacheMap = map.get(type);
                    for (String key : cacheMap.keySet()) {
                        //如果有已超时缓存则删除该缓存
                        if (cacheMap.get(key) != null && isTimeOut(cacheMap.get(key).getCacheTime())) {
                            removeCache(type, key);
                        }
                    }
                }
                addNew(type, sessionId, map.containsKey(type));
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     * Title: removeCache <br/>
     * Description: 删除缓存 <br/>
     *
     * @param type      类型
     * @param sessionId session id
     * @return void    返回类型
     */
    public void removeCache(String type, String sessionId) {
        //如果没有其他session的缓存则删除整个缓存
        try {
            writeLock.lock();
            if (map.containsKey(type)) {
                if (map.get(type).keySet().size() == 1 && map.get(type).containsKey(sessionId)) {
                    map.remove(type);
                } else {
                    map.get(type).remove(sessionId);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Title: addNew <br/>
     * Description: 加入缓存 <br/>
     *
     * @param type      类型
     * @param sessionId session id
     * @param haveType  是否已经有该类型
     * @return void    返回类型
     */
    private void addNew(String type, String sessionId, boolean haveType) {
        CacheBean cacheBean = new CacheBean();
        cacheBean.setCacheTime(new Date());
        cacheBean.setCount(0L);
        if (haveType && map.containsKey(type)) {
            map.get(type).put(sessionId, cacheBean);
        } else {
            Map<String, CacheBean> countMap = new HashMap<String, CacheBean>();
            countMap.put(sessionId, cacheBean);
            map.put(type, countMap);
        }
    }

    /**
     * Title: countPlus <br/>
     * Description: TODO(这里用一句话描述这个方法的作用) <br/>
     *
     * @param type
     * @param sessionId
     * @return void    返回类型
     */
    public void countPlus(String type, String sessionId, int addCount) {
        if (!StringUtil.isEmpty(type) && !StringUtil.isEmpty(sessionId)) {
            try {
                writeLock.lock();
                if (map.containsKey(type)) {
                    CacheBean cache = map.get(type).get(sessionId);
                    if (cache != null) {
                        cache.setCount(cache.getCount() + addCount);
                    }
                } else {
                    addNew(type, sessionId, false);
                }
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     * Title: getCacheCount <br/>
     * Description: 获取缓存中计数个数 <br/>
     *
     * @param type      类型
     * @param sessionId session id
     * @return Long    返回类型
     */
    public Long getCacheCount(String type, String sessionId) {
        if (!StringUtil.isEmpty(type) && !StringUtil.isEmpty(sessionId)) {
            try {
                readLock.lock();
                if (map.containsKey(type) && map.get(type).containsKey(sessionId)) {
                    return map.get(type).get(sessionId).getCount();
                }
            } finally {
                readLock.unlock();
            }
        }
        return null;
    }

    /**
     * Title: isTimeOut <br/>
     * Description: 判断缓存是否过期 <br/>
     *
     * @param cacheTime 缓存时间
     * @return boolean    返回类型
     */
    private boolean isTimeOut(Date cacheTime) {
        if (cacheTime != null && (new Date().getTime() - cacheTime.getTime()) > timeOut) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * ClassName: cachBean <br/>
     * Description: cachBean  缓存内部类 <br/>
     * Copyright: Copyright (c) 2017 <br/>
     * Company:Nanjing Lending Cloud Finance Information Service <br/>
     * Date 2017年6月26日 下午8:00:48 <br/>
     *
     * @author yinchuang
     * @version 1.0
     */
    public class CacheBean {

        /**
         * @Fields countMap : 缓存个数，key:sessionId;value:count
         */
        private Long count;

        /**
         * @Fields time : 缓存时间，用于超时
         */
        private Date cacheTime;

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public Date getCacheTime() {
            return cacheTime;
        }

        public void setCacheTime(Date cacheTime) {
            this.cacheTime = cacheTime;
        }

    }

}
