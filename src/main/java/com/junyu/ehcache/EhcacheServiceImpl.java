package com.junyu.ehcache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EhcacheServiceImpl implements EhcacheService{

	// HelloWorldCache
    // return current time with long data type
    @Cacheable(value="HelloWorldCache", key="#param")
    public String getTimestamp(String param) {
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }

    // demonstrate retrieve data from DB
    @Cacheable(value="HelloWorldCache", key="#key")
    public String getDataFromDB(String key) {
        System.out.println("retrieve data from DB");
        return key + ":" + String.valueOf(Math.round(Math.random()*1000000));
    }

    // demonstrate remove data from DB
    @CacheEvict(value="HelloWorldCache", key="#key")
    public void removeDataAtDB(String key) {
        System.out.println("remove data from DB");
    }
    
    // demonstrate load data from DB
    @CachePut(value="HelloWorldCache", key="#key")
    public String refreshData(String key) {
        System.out.println("load data from DB");
        return key + "::" + String.valueOf(Math.round(Math.random()*1000000));
    }

    // ------------------------------------------------------------------------
    // UserCache
    // demonstrate load user from DB
    @Cacheable(value="UserCache", key="'user:' + #userId")    
    public User findById(String userId) {  
        System.out.println("load user from DB");
        return new User(1, "junyu");           
    }  

    // use condition in cache
    @Cacheable(value="UserCache", condition="#userId.length()<12")    
    public boolean isReserved(String userId) {    
        System.out.println("UserCache: " + userId);    
        return false;    
    }

    // delete one key cache in UserCache
    @CacheEvict(value="UserCache",key="'user:' + #userId")    
    public void removeUser(String userId) {    
        System.out.println("UserCache remove: " + userId);    
    }    

    // delete all keys cache in UserCache
    @CacheEvict(value="UserCache", allEntries=true)    
    public void removeAllUser() {    
       System.out.println("UserCache delete all");    
    }
}
