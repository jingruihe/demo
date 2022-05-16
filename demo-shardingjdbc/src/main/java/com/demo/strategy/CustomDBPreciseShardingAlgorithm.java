package com.demo.strategy;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 精准分片
 */
public class CustomDBPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        for (String availableTargetName : availableTargetNames) {
            String value = shardingValue.getValue() % availableTargetNames.size() + "";
            //value是0，则进入0库/表，1则进入1库/表
            if (availableTargetName.endsWith(value)) {
                return availableTargetName;
            }
        }
        throw new IllegalArgumentException();
    }
}
