package com.casic.dubbo.Consumer.sources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@RestController
@RequestMapping("/kafka")
public class DubboControll {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
/*	@Autowired
	private DubboInterface interface1;*/
	/*@Autowired
    private KafkaTemplate kafkaTemplate;*/

	
	 @RequestMapping(value = "/send", method = RequestMethod.GET)
	 public String sendKafka(HttpServletRequest request, HttpServletResponse response) {
	        try {
	            String message = request.getParameter("message");
	            logger.info("kafka的消息={}", message);
	             Gson gson = new GsonBuilder().create();
	           /* kafkaTemplate.send("IOT_DS_DATA", "key", message);*/
	            logger.info("发送kafka成功.");
	            return "success";
	        } catch (Exception e) {
	            logger.error("发送kafka失败", e);
	            return "error"+e.getMessage();
	        }
	   }
	@RequestMapping("/hello")
	@ResponseBody
	public void hello(String name){
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
}
