package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PaymentService {

    static  AtomicInteger count = new AtomicInteger(0);

    public String paymentInfoOK(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + "\t" + "paymentInfoOK(),id:" + id + "\t" + " \"O(∩_∩) 成功返回哈哈哈";
    }

    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfoTimeOut(Integer id) {

        System.out.println("第" + count.getAndIncrement() + "请求");
        Integer timeOutNumber = 4;
        // int age  = 10/0;
        try {
            TimeUnit.SECONDS.sleep(timeOutNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "线程池:  " + Thread.currentThread().getName()  + " id:  " + id + "\t" + "┭┮﹏┭┮" + "  耗时(秒): " + timeOutNumber;
    }

    public String paymentInfoTimeOutHandler(Integer id){
        return "┭┮﹏┭┮调用支付接口超时，当前线程池名字"+Thread.currentThread().getName();
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback"
    ,commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true")
            ,@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10")
            ,@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000")
            ,@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    public String paymentCircuitBreaker(Integer id){
        if (id < 0){
            throw new RuntimeException("***id不能为负数***");
        }
        String simpleUUID = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"   调用成功，流水号："+simpleUUID;
    }

    public String paymentCircuitBreakerFallback(Integer id){
        return "id不能为负数， 请稍后再试，id"+id;
    }
}
