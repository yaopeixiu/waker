package com.spring.cloud.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @description:
 * @author: Lyle
 * @create: 2019-03-08 15:32
 **/

@EnableBinding(MqSource.class)
public class MqMessageConsumer {
    private static Logger logger = LoggerFactory.getLogger(MqMessageConsumer.class);

}
