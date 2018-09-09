package com.casic.dubbo.Consumer.sources.listener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import com.casic.dubbo.Consumer.sources.util.RedisUtil;
import com.casic.dubbo.Consumer.sources.util.StringUtil;


@Configuration
public class KafkaConfiguration  implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		String initOffset = RedisUtil.getString("initOffset");
			System.setProperty("initOffset", StringUtil.isEmpty(initOffset)?"0":initOffset);
	}

}
