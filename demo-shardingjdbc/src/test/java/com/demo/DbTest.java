package com.demo;

import com.demo.entity.ProductOrderDO;
import com.demo.mapper.ProductOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class) //底层⽤junit SpringJUnit4ClassRunner
@SpringBootTest(classes = ShardingJdbcApplication.class)
@Slf4j
public class DbTest {
    @Autowired
    private ProductOrderMapper productOrderMapper;
    @Test
    public void testSaveProductOrder(){
        //for(int i=0; i<10;i++){
        ProductOrderDO productOrderDO = new ProductOrderDO();
        productOrderDO.setCreateTime(new Date());
        productOrderDO.setNickname("小滴课堂i="+1);
        productOrderDO.setOutTradeNo(UUID.randomUUID().toString().substring(0,32));
        productOrderDO.setPayAmount(100.00);
        productOrderDO.setState("PAY");

        productOrderDO.setUserId(Long.valueOf(1+""));
        productOrderMapper.insert(productOrderDO);

        //}
    }
}
