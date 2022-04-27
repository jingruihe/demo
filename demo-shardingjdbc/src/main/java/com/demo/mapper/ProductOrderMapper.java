package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.ProductOrderDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ProductOrderMapper extends BaseMapper<ProductOrderDO> {

    @Select("select * from product_order o left join product_order_item i on o.id=i.product_order_id")
    List<Object> listProductOrderDetail();

}
