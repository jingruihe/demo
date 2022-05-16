package com.demo.strategy;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 范围分片
 */
public class CustomRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        Set<String> result = new LinkedHashSet<>();
        // between 起始值
        Long lower = shardingValue.getValueRange().lowerEndpoint();
        // between 结束值
        Long upper = shardingValue.getValueRange().upperEndpoint();
        // 循环范围计算分库/表逻辑
        for (long i = lower; i <= upper; i++) {
            for (String databaseName : availableTargetNames) {
                if (databaseName.endsWith(i % availableTargetNames.size() + "")) {
                    result.add(databaseName);
                }
            }
        }
        return result;
    }
}
