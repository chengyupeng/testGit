package com.casic.dubbo.Consumer.sources.listener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;



public class MyProducer4 {
	
	public static void main(String[] args) throws ParseException, InterruptedException {
		 //topic
        String topic = "IOT_DS_DATA_TEST";
        //閰嶇疆
        Properties properties = new Properties();
        //172.17.70.31:9092,172.17.70.30:9092 
        //172.17.70.14:6667,172.17.70.15:6667,172.17.70.17:6667
        //hdp4.hdp:2181,hdp3.hdp:2181,hdp5.hdp:2181,hdp7.hdp:2181,hdp6.hdp:2181
        //hdp2.hdp:6667,hdp3.hdp:6667,hdp4.hdp:6667
        properties.put("bootstrap.servers", "172.17.70.14:6667,172.17.70.15:6667,172.17.70.17:6667");
        // properties.put("bootstrap.servers", "10.153.5.15:6667,10.153.5.16:6667,10.153.5.17:6667");
        //搴忓垪鍖栫被鍨�
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //鍒涘缓鐢熶骇鑰�
        KafkaProducer<String, String> pro = new KafkaProducer<>(properties);
        
        int i=0;
        

		String context0 ="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"table\":\"20000050940000\",\"k\":\"state\"}";
       /* String context1 ="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"equipment\":\"10000050950000\",\"k\":\"state\"}";
        String context2 ="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"equipment\":\"10000050950006\",\"k\":\"state\"}";
        String context3 ="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"equipment\":\"10000050950012\",\"k\":\"state\"}";
        String context4 ="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"equipment\":\"10000050950018\",\"k\":\"state\"}";
        String context5 ="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"equipment\":\"10000050950024\",\"k\":\"state\"}";
        String context6 ="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"equipment\":\"10000050950030\",\"k\":\"state\"}";
        String context7 ="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"equipment\":\"10000050950036\",\"k\":\"state\"}";
        String context8 ="{\"orgId\":\"8421746\",\"v\":\"3000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"equipment\":\"10000050950042\",\"k\":\"state\"}";
        String context9 ="{\"orgId\":\"8421746\",\"v\":\"2000\",\"createTime\":\"?\",\"t\":\"?\",\"iot\":\"10000049610850\",\"equipment\":\"10000050950048\",\"k\":\"state\"}";*/
        List<String> list =new <String> ArrayList();
        list.add(context0);
       /* list.add(context1);
        list.add(context2);
        list.add(context3);
        list.add(context4);
        list.add(context5);
        list.add(context6);
        list.add(context7);
        list.add(context8);
        list.add(context9);*/
       /* while (true) {
        	i++;
        	if(i>200){
        		System.out.println("已经创建了"+i+"个线程");
        		break;
        	}*/
            Thread e=new Thread(new TaskProducer(topic, properties, list));
            e.start();
        /*}*/

	}
	
}
class TaskProducer implements Runnable{
	//KafkaProducer producer;
	Properties properties;
	List <String> context;
	String topic;
	public TaskProducer(String topic,Properties properties,List context) {
		this.topic=topic;
		this.context=context;
		this.properties=properties;
	}
	
	@Override
	public void run() {
		int i=0;
		while (true) {
			i++;
			if(i>2000){
				//System.out.println(Thread.currentThread().getName()+"已经发送了"+i);
				i=0;
			}
			String a=	context.get(0).replaceAll("\\?", System.currentTimeMillis()+"");
			ProducerRecord<String, String> pr = new ProducerRecord<String, String>(topic, a);
			//System.out.println("消息发送了----"+a);
			KafkaProducer producer=null;
			 try {
				   producer=new KafkaProducer(properties);
					producer.send(pr);
					Thread.sleep(50);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if(producer!=null){
						producer.close();
					}
				}
			
		}
		
         
	}
	
};