package com.casic.dubbo.Consumer.sources.model;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class Receiver {

    private Gson gson = new GsonBuilder().create();

    @KafkaListener(topics = "test1")
    public void processMessage(String content) {
        Message m = gson.fromJson(content, Message.class);
    }
}