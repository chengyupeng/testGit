package com.casic.dubbo.Consumer.sources.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by qasim on 29/12/15.
 */
public class MqttTemplate extends MqttClient {

    public MqttTemplate(String serverURI) throws MqttException {
        super(serverURI, java.util.UUID.randomUUID().toString());
    }

    public static MqttConnectOptions defaultOptions(){
    	
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
//        mqttConnectOptions.setUserName("pub");
//        mqttConnectOptions.setPassword("123456".toCharArray());
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setConnectionTimeout(30);//设置连接超时时间为一分钟
        mqttConnectOptions.setKeepAliveInterval(10);
        return mqttConnectOptions;
    }
    
    
}
