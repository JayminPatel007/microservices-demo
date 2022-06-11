package com.jaymin.microservice_demo.twitter_to_kafka_service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class TwitterStatusKafkaListener extends StatusAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterStatusKafkaListener.class);
    @Override
    public void onStatus(Status status) {
        LOG.info("Twitter status with text {}", status.getText());
    }
}
