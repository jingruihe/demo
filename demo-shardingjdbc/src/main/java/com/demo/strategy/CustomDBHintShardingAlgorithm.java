package com.demo.strategy;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Hint分片策略
 */
public class CustomDBHintShardingAlgorithm implements HintShardingAlgorithm<Long> {
    /**
     * @param dataSourceNames 数据源集合
     *  - 在分库时值为所有分片库的集合 databaseNames
     *  - 分表时为对应分片库中所有分片表的集合 tablesNames
     *
     * @param hintShardingValue  分片属性，包括
     *  - logicTableName 为逻辑表，
     *  - columnName 分片健（字段），hit策略此处为空 ""
     *    value 【之前】都是 从 SQL 中解析出的分片健的值,用于取模判断
     *    HintShardingAlgorithm不再从SQL 解析中获取值，而是直接通过
     *    hintManager.addTableShardingValue("product_order", 1)参数进行指定
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> dataSourceNames,
                                         HintShardingValue<Long> hintShardingValue) {

        Collection<String> result = new ArrayList<>();
        for (String tableName : dataSourceNames) {
            for (Long shardingValue : hintShardingValue.getValues()) {
                if (tableName.endsWith(String.valueOf(shardingValue % dataSourceNames.size()))) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }
}