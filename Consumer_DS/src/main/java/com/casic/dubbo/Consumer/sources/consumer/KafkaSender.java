package com.casic.dubbo.Consumer.sources.consumer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
/*	@Resource
KafkaConsumerConfigThread consumerConfig;
  @Resource
  private  KafkaConsumerPool consumerPool;*/
	@PostConstruct
    void d(){

    /*    ConsumerGroup consumerThread = new ConsumerGroup("gropu-1","IOT_DS_DATA",consumerConfig);
        ConsumerGroup consumerThread2 = new ConsumerGroup("gropu-2","IOT_DS_DATA", consumerConfig);

        *//**
         * 各起两个消费者 ,Kafka consumer是非线程安全的 Consumer 需要一个new 的
         *//*
        consumerPool.SubmitConsumerPool(new Consumer(consumerThread));
        consumerPool.SubmitConsumerPool(new Consumer(consumerThread));

        consumerPool.SubmitConsumerPool(new Consumer(consumerThread2));
        consumerPool.SubmitConsumerPool(new Consumer(consumerThread2));*/
    }
}
