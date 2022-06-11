package com.jaymin.microservice_demo.twitter_to_kafka_service.runner;

import twitter4j.TwitterException;

public interface StreamRunner {
    void start() throws TwitterException;
}
