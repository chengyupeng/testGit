package com.casic.dubbo.Consumer.sources.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerPool {

    /**
     * 日志处理
     */
    private static final Log log = LogFactory.getLog(KafkaConsumerPool.class);

    /**
     *  线程池
     */
    private ExecutorService executor;

    /**
     * 初始化10个线程
     */
    @PostConstruct
    void init(){
        executor = Executors.newFixedThreadPool(10);
    }

    /**
     * 提交新的消费者
     *
     * @param shutdownableThread
     */
    public void SubmitConsumerPool(Consumer shutdownableThread) {
        executor.execute(shutdownableThread);
    }

    /**
     * 程序关闭,关闭线程池
     *
     */
    @PreDestroy
    void fin(){
        shutdown();
    }

    public void shutdown() {
        if (executor != null) executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                log.info("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            log.info("Interrupted during shutdown, exiting uncleanly");
        }
    }
}