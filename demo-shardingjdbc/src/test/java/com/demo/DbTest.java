package com.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entity.AdConfigDO;
import com.demo.entity.ProductOrderDO;
import com.demo.entity.ProductOrderItemDO;
import com.demo.mapper.AdConfigMapper;
import com.demo.mapper.ProductOrderItemMapper;
import com.demo.mapper.ProductOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
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

//        for (int i = 0; i < 10; i++) {
//            ProductOrderItemDO productOrderItem = new ProductOrderItemDO();
//            productOrderItem.setProductOrderId(1519310343728111610L);
//            productOrderItem.setBuyNum(i);
//            productOrderItem.setUserId(3L);
//            productOrderItem.setProductName("关联表"+i);
//            productOrderItemMapper.insert(productOrderItem);
//        }

/*

Logic SQL: select * from product_order o left join product_order_item i on o.id=i.product_order_id

Actual SQL: ds0 ::: select * from product_order_0 o left join product_order_item_0 i on o.id=i.product_order_id
Actual SQL: ds0 ::: select * from product_order_1 o left join product_order_item_1 i on o.id=i.product_order_id
Actual SQL: ds1 ::: select * from product_order_0 o left join product_order_item_0 i on o.id=i.product_order_id
Actual SQL: ds1 ::: select * from product_order_1 o left join product_order_item_1 i on o.id=i.product_order_id


2022-04-30 08:43:47.533  INFO 2432 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@153cfd86, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@7e31062c), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@7e31062c, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=7, distinctRow=false, projections=[ShorthandProjection(owner=Optional.empty, actualColumns=[ColumnProjection(owner=null, name=id, alias=Optional.empty), ColumnProjection(owner=null, name=out_trade_no, alias=Optional.empty), ColumnProjection(owner=null, name=state, alias=Optional.empty), ColumnProjection(owner=null, name=create_time, alias=Optional.empty), ColumnProjection(owner=null, name=pay_amount, alias=Optional.empty), ColumnProjection(owner=null, name=nickname, alias=Optional.empty), ColumnProjection(owner=null, name=user_id, alias=Optional.empty), ColumnProjection(owner=null, name=id, alias=Optional.empty), ColumnProjection(owner=null, name=product_order_id, alias=Optional.empty), ColumnProjection(owner=null, name=product_id, alias=Optional.empty), ColumnProjection(owner=null, name=product_name, alias=Optional.empty), ColumnProjection(owner=null, name=buy_num, alias=Optional.empty), ColumnProjection(owner=null, name=user_id, alias=Optional.empty)])]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@595fed99, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@7d522180, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@ff03361, containsSubquery=false)
2022-04-30 08:43:47.534  INFO 2432 --- [           main] ShardingSphere-SQL                       :
2022-04-30 08:43:47.534  INFO 2432 --- [           main] ShardingSphere-SQL                       :
2022-04-30 08:43:47.534  INFO 2432 --- [           main] ShardingSphere-SQL                       :
2022-04-30 08:43:47.534  INFO 2432 --- [           main] ShardingSphere-SQL                       :
* */
        List<Object> list = productOrderMapper.listProductOrderDetail();
        System.out.println(list);
    }


    @Test
    public void testCustomSaveProductOrder(){
        ProductOrderDO productOrderDO = new ProductOrderDO();
        productOrderDO.setCreateTime(new Date());
        productOrderDO.setNickname("自定义分库分表_"+222);
        productOrderDO.setOutTradeNo(UUID.randomUUID().toString().substring(0,32));
        productOrderDO.setPayAmount(100.00);
        productOrderDO.setState("PAY");
        productOrderDO.setUserId(Long.valueOf(2+""));
        productOrderMapper.insert(productOrderDO);
        System.out.println(productOrderDO.getId());
    }

    @Test
    public void testBetween(){
        List<ProductOrderDO> list = productOrderMapper.selectList(new QueryWrapper<ProductOrderDO>().between("id", 1L, 2L));
        System.out.println(list);
    }
    @Test
    public void testComplex(){
        List<ProductOrderDO> list = productOrderMapper.selectList(
                new QueryWrapper<ProductOrderDO>().eq("id", 66L).eq("user_id", 99L));
        System.out.println(list);
    }

    @Test
    public void testHint() {
        // 清除掉历史的规则
        HintManager.clear();
        //Hint分片策略必须要使用 HintManager工具类
        HintManager hintManager = HintManager.getInstance();
        // 设置库的分片健,value用于库分片取模，
        hintManager.addDatabaseShardingValue("product_order",3L);

        // 设置表的分片健,value用于表分片取模，
        //hintManager.addTableShardingValue("product_order", 7L);
        hintManager.addTableShardingValue("product_order", 8L);

        // 如果在读写分离数据库中，Hint 可以强制读主库（主从复制存在一定延时，但在业务场景中，可能更需要保证数据的实时性）
        //hintManager.setMasterRouteOnly();

        //对应的value只做查询，不做sql解析
        productOrderMapper.selectList(new QueryWrapper<ProductOrderDO>().eq("id", 66L));
    }

}
