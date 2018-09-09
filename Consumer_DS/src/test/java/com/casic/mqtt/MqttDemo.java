package com.casic.mqtt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.alibaba.dubbo.qos.server.handler.QosProcessHandler;

public class MqttDemo {
	

	    public static void main(String[] args) throws KeyManagementException, CertificateException, FileNotFoundException, IOException, KeyStoreException {

	        String topic        = "/IOT_DATA_DS";
	        String content="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\""+System.currentTimeMillis()+"\",\"t\":\""+System.currentTimeMillis()+"\",\"iot\":\"10000049610850\",\"equipment\":\"10000050940000\",\"k\":\"state\"}";   
	        int qos             = 2;
	        String broker       = "tcp://172.17.70.70:18883";
	        String clientId     = UUID.randomUUID().toString();
	        MemoryPersistence persistence = new MemoryPersistence();
	       
	        try {
	            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
	          
	            MqttConnectOptions connOpts = new MqttConnectOptions();
	            connOpts.setCleanSession(true);
	            System.out.println("Connecting to broker: "+broker);
	            sampleClient.connect(connOpts);
	            SubMsg.sub(sampleClient, topic);
	            System.out.println("Connected");
	            System.out.println("Publishing message: "+content);
	            MqttMessage message = new MqttMessage(content.getBytes());
	            message.setQos(qos);
	            message.setRetained(true);//表示未订阅认可哟
	            sampleClient.publish(topic, message);
	            System.out.println("Message published");
	           // sampleClient.disconnect();
	            //System.out.println("Disconnected");
	            //System.exit(0);
	        } catch(MqttException me) {
	            System.out.println("reason "+me.getReasonCode());
	            System.out.println("msg "+me.getMessage());
	            System.out.println("loc "+me.getLocalizedMessage());
	            System.out.println("cause "+me.getCause());
	            System.out.println("excep "+me);
	            me.printStackTrace();
	        } 
	        
	      
	    }
	    

}

 class SubMsg {  
	  
//  private static String topic = "$share/testgroup/wyptest1";  
//  private static String topic = "$queue/wyptest1";  
//  private static String topic = "wyptest1";  
    private static int qos = 2;  
    private static String broker = "tcp://172.17.70.70:1883";  
     
 
    private static MqttClient connect(String clientId) throws MqttException{  
        MemoryPersistence persistence = new MemoryPersistence();  
        MqttConnectOptions connOpts = new MqttConnectOptions();  
//      String[] uris = {"tcp://10.100.124.206:1883","tcp://10.100.124.206:1883"};  
        connOpts.setCleanSession(false);  
        connOpts.setConnectionTimeout(10);  
        connOpts.setKeepAliveInterval(20);  
        MqttClient mqttClient = new MqttClient(broker, clientId, persistence);  
        mqttClient.connect(connOpts);  
        return mqttClient;  
    }  
      
    public static void sub(MqttClient mqttClient,String topic) throws MqttException{  
        int[] Qos  = {qos};  
        String[] topics = {topic};  
        mqttClient.subscribe(topics, Qos);  
    }  
      
      
    public static void runsub(String clientId, String topic) throws MqttException{  
       MqttClient mqttClient = connect(clientId);  
       if(mqttClient != null){  
           sub(mqttClient,topic);  
       }  
   }  
 
}
