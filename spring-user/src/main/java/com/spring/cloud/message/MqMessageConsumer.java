package com.spring.cloud.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @description:
 * @author: Lyle
 * @create: 2020-10-11 15:31
 **/
@Slf4j
@EnableBinding(MqMessageSource.class)
public class MqMessageConsumer {

}
