package com.spring.cloud.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @description: mq通道、队列定义
 * @author: Lyle
 * @create: 2020-10-11 15:31
 **/
public interface MqMessageSource {

    //发送卷轴
   /* String SCROLL_OUTPUT = "scroll_output";
    @Output(SCROLL_OUTPUT)
    MessageChannel scrollOutput();*/
}
