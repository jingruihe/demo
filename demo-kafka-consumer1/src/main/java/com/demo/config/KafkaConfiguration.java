package com.demo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class KafkaConfiguration {
	private final static String KAFKA_BOOTSTRAP_SERVERS = "192.168.1.121:9090,192.168.1.121:9091,192.168.1.121:9092";

	private final static String KAFKA_GROUP_ID = "demoGroupTest";

	// 默认配置，自动提交，批量
	@Bean("kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		// 默认自动批量提交
//		factory.getContainerProperties().setAckMode((ContainerProperties.AckMode.BATCH));
		factory.getContainerProperties().setAckMode((ContainerProperties.AckMode.MANUAL_IMMEDIATE));
		return factory;
	}

//	// 自动提交，批量提交，批量监听
//	@Bean("batchListenerContainerFactory")
//	public ConcurrentKafkaListenerContainerFactory<Integer, String> batchListenerContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(consumerFactory());
//		factory.getContainerProperties().setAckMode((ContainerProperties.AckMode.BATCH));
//		//设置为批量监听
//		factory.setBatchListener(true);
//		return factory;
//	}
//
//	// 大任务,手动提交，单条提交
//	@Bean("ackBigTaskContainerFactory")
//	public ConcurrentKafkaListenerContainerFactory<Integer, String> ackBigTaskContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		// 大任务消费者配制
//		factory.setConsumerFactory(consumerBigTaskFactory());
//		// 单条提交
//		factory.getContainerProperties().setAckMode((ContainerProperties.AckMode.MANUAL_IMMEDIATE));
//		return factory;
//	}




	// 根据consumerProps填写的参数创建消费者工厂
	@Bean
	public ConsumerFactory<Integer, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerProps());
	}

	// 根据consumerProps填写的参数创建消费者工厂
	@Bean
	public ConsumerFactory<Integer, String> consumerBigTaskFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerBigTaskProps());
	}

	// 消费者配置参数
	private Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		//连接地址
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
		//键的反序列化方式
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		//值的反序列化方式
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		//GroupID
		props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID);

		//是否自动提交
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		//自动提交的频率
//		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "30000");
		//Session超时设置
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "100000");

		// poll 一次拉取数量
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 20);
		// 两次poll 处理的超时时间,超时提交offset失败,造成重复消费,新任务永远不会执行
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10000);
		return props;
	}

	// 大任务消费者
	private Map<String, Object> consumerBigTaskProps() {
		Map<String, Object> props = new HashMap<>();
		//连接地址
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
		//键的反序列化方式
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		//值的反序列化方式
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		//GroupID
		props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID);

		//是否自动提交
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		//自动提交的频率
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
		//Session超时设置
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "100000");

		// poll 一次拉取数量
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
		// 两次poll 处理的超时时间,超时提交offset失败,造成重复消费,新任务永远不会执行
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 5 * 60 * 60 * 1000);
		return props;
	}

}
