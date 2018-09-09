package com.casic.dubbo.Consumer.sources.listener;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.casic.dubbo.Consumer.sources.mqtt.Constants;
import com.casic.dubbo.Consumer.sources.mqtt.MqttTemplate;

/**
 * Created by qasim on 30/12/15.
 */
@Configuration
public class MqttListener implements MqttCallback{
	private static final Logger logger = LoggerFactory.getLogger(MqttListener.class);
    @Autowired
    MqttTemplate mqttTemplate;

    
    @PostConstruct
    public void initialize() throws MqttException {
    	mqttTemplate.subscribe(Constants.FD_MQTT_TOPIC);
        mqttTemplate.setCallback(this);
    }

    @Override
    public void connectionLost(Throwable throwable) {
    	  System.out.println("连接断开，可以做重连");    
    	  try {
			/*mqttTemplate.subscribe(Constants.FD_MQTT_TOPIC);
			mqttTemplate.setCallback(this);*/
    		  if(!mqttTemplate.isConnected()){
    			  MqttTemplate mqttTemplate = new MqttTemplate(Constants.FD_MQTT_SERVER_URL);
    		      mqttTemplate.connect(MqttTemplate.defaultOptions());
    		  }
    		mqttTemplate.subscribe(Constants.FD_MQTT_TOPIC);
  			mqttTemplate.setCallback(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        try {
        	logger.info("Consumer接收到的数据--------topic:"+s+"------------------------message:"+mqttMessage.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
       
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
