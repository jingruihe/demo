package com.demo;

import com.demo.entity.AdConfigDO;
import com.demo.entity.ProductOrderDO;
import com.demo.entity.ProductOrderItemDO;
import com.demo.mapper.AdConfigMapper;
import com.demo.mapper.ProductOrderItemMapper;
import com.demo.mapper.ProductOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class) //底层⽤junit SpringJUnit4ClassRunner
@SpringBootTest(classes = ShardingJdbcApplication.class)
@Slf4j
public class DbTest {
    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private AdConfigMapper adConfigMapper;

    @Autowired
    private ProductOrderItemMapper productOrderItemMapper;

    @Test
    public void testSaveProductOrder(){
        //for(int i=0; i<10;i++){
        ProductOrderDO productOrderDO = new ProductOrderDO();
        productOrderDO.setCreateTime(new Date());
        productOrderDO.setNickname("shardingJDBC_"+1);
        productOrderDO.setOutTradeNo(UUID.randomUUID().toString().substring(0,32));
        productOrderDO.setPayAmount(100.00);
        productOrderDO.setState("PAY");

        productOrderDO.setUserId(Long.valueOf(2+""));
        productOrderMapper.insert(productOrderDO);

        System.out.println(productOrderDO.getId());
        //}
    }

    @Test
    public void testSaveAdConfig(){

        AdConfigDO adConfigDO = new AdConfigDO();
        adConfigDO.setConfigKey("banner");
        adConfigDO.setConfigValue("xdclass.net");
        adConfigDO.setType("ad");

        adConfigMapper.insert(adConfigDO);
    }

    @Test
    public void testBingding(){

//        ProductOrderDO productOrderDO = new ProductOrderDO();
//        productOrderDO.setCreateTime(new Date());
//        productOrderDO.setNickname("shardingJDBC_"+1);
//        productOrderDO.setOutTradeNo(UUID.randomUUID().toString().substring(0,32));
//        productOrderDO.setPayAmount(100.00);
//        productOrderDO.setState("PAY");
//        productOrderDO.setUserId(2L);
//        productOrderMapper.insert(productOrderDO);


        ProductOrderItemDO productOrderItem = new ProductOrderItemDO();
        productOrderItem.setProductOrderId(1519310343728111618L);
        productOrderItem.setBuyNum(10);
        productOrderItem.setUserId(3L);
        productOrderItem.setProductName("电脑");
        productOrderItemMapper.insert(productOrderItem);

        List<Object> list = productOrderMapper.listProductOrderDetail();
        System.out.println(list);
    }

}
