package com.jfcbxp.redis.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    @Cacheable(value = "math:fib",key = "#index")
    public int getFib(int index,String name){
        return this.fib(index);

    }

    //PUT /POST /PATCH /DELETE
    @CacheEvict(value = "math:fib",key = "#index")
    public void clearCache(int index){
        System.out.println("clearing cache");
    }

    @Scheduled(fixedRate = 10_000)
    @CacheEvict(value = "math:fib",allEntries = true)
    public void clearAllCache(){
        System.out.println("clearing all cache");
    }

    private int fib(int index){
        if(index < 2)
            return index;
        return fib(index - 1 ) + fib(index - 2);
    }
}
