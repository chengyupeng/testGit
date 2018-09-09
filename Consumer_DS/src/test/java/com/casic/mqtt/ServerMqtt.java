package com.casic.mqtt;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import com.alibaba.fastjson.JSONObject;

public class ServerMqtt {
	public static final String HOST = "tcp://172.17.70.70:18883";
	public static MqttClient client;
	private MqttTopic topic11;
	private String userName = "admin";
	private String passWord = "public";
	private MqttMessage message;
	private static  ServerMqtt serverMqtt ;

	public static ServerMqtt getServerMQTTInstance() {
		if (null != serverMqtt) {
			return serverMqtt;
		}
		serverMqtt = new ServerMqtt();
		return serverMqtt;
	}

	public void connect( MqttClient client) {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(true);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		options.setConnectionTimeout(10);
		options.setKeepAliveInterval(20);
	  	try {
			client.setCallback(new PushCallback());
			client.connect(options);
			MqttTopic topic = client.getTopic("154");
			options.setWill(topic, "close".getBytes(), 2, true);
				int[] Qos = { 1 };
				String[] topic1 = { "/IOT_DATA_DS" };
				client.subscribe(topic1, Qos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static MqttClient getMqttClient(String clientId) throws MqttException {
		if (client != null && clientId.equals(client.getClientId())) {
			return client;
		}
		return new MqttClient(HOST, clientId);
	}

	public void MqttInit( String clientId) throws MqttException {
		MqttClient client1 = ServerMqtt.getMqttClient(clientId);
		if(this.client !=client1){
			this.client = client1;
			connect(client);
		}
		
	}
	public void publish(String topicStr, String str) throws MqttPersistenceException, MqttException {
		message = new MqttMessage();
		message.setQos(2);
		message.setRetained(true);
		message.setPayload(str.getBytes());
		topic11 = client.getTopic(topicStr);
		MqttDeliveryToken token = topic11.publish(message);
		token.waitForCompletion();
		System.out.println("message is published completely! " + token.isComplete());
	}
	
	public static void main(String[] args) throws Exception {
		getServerMQTTInstance().MqttInit("aaa");
		MqttClient mqttClient = getMqttClient("aaa");
		String topicStr="/IOT_DATA_DS";
		//mqttClient.subscribe(topicStr,2);
		 String message="{\"orgId\":\"8421746\",\"v\":\"1000\",\"createTime\":\""+System.currentTimeMillis()+"\",\"t\":\""+System.currentTimeMillis()+"\",\"iot\":\"10000049610850\",\"equipment\":\"10000050940000\",\"k\":\"state\"}";   
		getServerMQTTInstance().publish(topicStr, message);
		
	}

}