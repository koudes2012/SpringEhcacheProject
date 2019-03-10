package com.junyu.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.junyu.ehcache.EhcacheService;

public class EhcacheServiceTest extends SpringTestCase{

    @Autowired
    private EhcacheService ehcacheService;

	// HelloWorldCache
    @Test
    public void testTimestamp() throws InterruptedException{
    	// valid period is 5 seconds
        System.out.println("The first time call: " + ehcacheService.getTimestamp("param"));
        Thread.sleep(2000);
        // totally 2 seconds passed
        System.out.println("Another 2 seconds later call: " + ehcacheService.getTimestamp("param"));
        Thread.sleep(4000);
        // totally 6 seconds passed
        System.out.println("Another 4 seconds later call: " + ehcacheService.getTimestamp("param"));
    }

    @Test
    public void testCache(){
        String key = "junyu";
        ehcacheService.getDataFromDB(key); // output: retrieve data from DB
        // retrieve data from cache
        ehcacheService.getDataFromDB(key);
        ehcacheService.removeDataAtDB(key); // output: remove data from DB
        // cache data was be removed, so retrieve again
        ehcacheService.getDataFromDB(key);  // output: retrieve data from DB
    }

    @Test
    public void testPut(){
        String key = "junyu";
        ehcacheService.refreshData(key);  // output: load data from DB
        String data = ehcacheService.getDataFromDB(key);
        System.out.println("data:" + data); // output: data:junyu::225711

        ehcacheService.refreshData(key);  // output: load data from DB
        String data2 = ehcacheService.getDataFromDB(key);
        System.out.println("data2:" + data2);   // output: data2:junyu::994883
    }

    // ------------------------------------------------------------------------
    // UserCache
    @Test
    public void testFindById(){
        ehcacheService.findById("2"); // output: load user from DB
        ehcacheService.findById("2");
    }

    @Test
    public void testIsReserved(){
        ehcacheService.isReserved("123"); // output: UserCache: 123
        ehcacheService.isReserved("123");
    }

    @Test
    public void testRemoveUser(){
        ehcacheService.findById("1"); // output: load user from DB
        ehcacheService.removeUser("1"); // output: UserCache remove: 1
        ehcacheService.findById("1"); // output: load user from DB
    }

    @Test
    public void testRemoveAllUser(){
        ehcacheService.findById("1"); // output: load user from DB
        ehcacheService.findById("2"); // output: load user from DB
        ehcacheService.removeAllUser(); // output: UserCache delete all
        ehcacheService.findById("1"); // output: load user from DB
        ehcacheService.findById("2"); // output: load user from DB
    }

}
