package com.jaymin.microservice_demo.twitter_to_kafka_service.listener;

import com.jaymin.microservice_demo.app_config_data.config.KafkaConfigData;
import com.jaymin.microservice_demo.kafka.avro.model.TwitterAvroModel;
import com.jaymin.microservice_demo.kafka_producer.service.KafkaProducer;
import com.jaymin.microservice_demo.twitter_to_kafka_service.transformer.TwitterStatusToAvroTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class TwitterStatusKafkaListener extends StatusAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterStatusKafkaListener.class);

   private final KafkaConfigData kafkaConfigData;
   private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;
   private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;

    public TwitterStatusKafkaListener(KafkaConfigData kafkaConfigData,
                                      KafkaProducer<Long, TwitterAvroModel> kafkaProducer,
                                      TwitterStatusToAvroTransformer twitterStatusToAvroTransformer) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaProducer = kafkaProducer;
        this.twitterStatusToAvroTransformer = twitterStatusToAvroTransformer;
    }

    @Override
    public void onStatus(Status status) {
        LOG.info("Received status text {} sending to Kafka topic {}", status.getText(), kafkaConfigData.getTopicName() );
        TwitterAvroModel twitterAvroModel = twitterStatusToAvroTransformer.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }
}
