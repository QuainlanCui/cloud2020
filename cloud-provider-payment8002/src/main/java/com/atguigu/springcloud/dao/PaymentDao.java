package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao {
    int create(Payment payment);

    /**
     * 根据id获取订单信息
     * @param id
     * @return
     */
    Payment getPaymentById(@Param("id") Long id);
}