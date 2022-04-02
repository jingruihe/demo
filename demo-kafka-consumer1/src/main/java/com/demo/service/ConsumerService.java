package com.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

    @KafkaListener(topics = "${kafka.topic}")
    public void onMsg(ConsumerRecord<?, ?> record, Acknowledgment ack) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("Kafka消费者接受到消息 " + record.toString());
        ack.acknowledge();
    }

}
