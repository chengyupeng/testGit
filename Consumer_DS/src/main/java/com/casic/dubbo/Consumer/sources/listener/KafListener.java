package com.casic.dubbo.Consumer.sources.listener;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;

import com.alibaba.fastjson.JSON;
import com.casic.dubbo.Consumer.sources.util.RedisUtil;
import com.casic.dubbo.Consumer.sources.util.StringUtil;


public class KafListener {
	 protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	
		int i=0;
		 //监听多个主题
	  // @KafkaListener(topicPartitions  = {@TopicPartition(topic = "IOT_DS_DATA", partitions = {"0"},partitionOffsets={@PartitionOffset(initialOffset="")} )})
		 @KafkaListener( topicPartitions ={ 
	             @TopicPartition(topic = "IOT_DS_DATA",
	             partitionOffsets = @PartitionOffset(initialOffset = "${initOffset}", partition = "0"))
	})
		// @KafkaListener(topics = {"IOT_DS_DATA"})
		public void listen(List<ConsumerRecord> list) {
	    	//topic:value
			 String initOffset = RedisUtil.getString("initOffset");
			 long sum=0;
	      if(list!=null&&!list.isEmpty()){
	    	  for (ConsumerRecord record : list) {
		    	  i++;
		    	  String value=String.valueOf(record.value());
		    		  System.out.println("----"+value+"---topicPartTiton="+record.partition());
		    		  long offset = record.offset();
		    		  sum+=offset;
		    		  new Runnable() {
		  				@Override
		  				public void run() {
		  					Map map = JSON.parseObject(value, Map.class);
		  					long createTime = Long.parseLong(String.valueOf(map.get("createTime")));
		  					i=0;
		  				}
		  			}.run();
			}
	      }
	      if(StringUtil.isEmpty(initOffset)){
	    	  RedisUtil.setString("initOffset", String.valueOf(sum)); 
	      }else{
	    	  RedisUtil.setString("initOffset", String.valueOf(sum+Long.parseLong(initOffset)));
	      }
	    	
	    
	    }
	    
}
