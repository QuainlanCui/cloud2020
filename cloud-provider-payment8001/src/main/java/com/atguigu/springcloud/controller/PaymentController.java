package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.PaymentMain8001;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 崔正午
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("***插入结果："+result+"***");
        if (result > 0){
            return new CommonResult(200,"成功,调用服务"+serverPort,result);
        }else {
            return new CommonResult(444,"失败",null);
        }

    }


    @GetMapping("/payment/get/{id}")
    public CommonResult get(@PathVariable Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("***查询结果："+payment);
        if (payment != null){
            return new CommonResult(200,"成功,调用服务"+serverPort,payment);
        }else {
            return new CommonResult(444,"失败",null);
        }

    }


}
