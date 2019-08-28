package com.example;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HelloReceive {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = "test-queue")
    @RabbitHandler
    public void process(Channel channel, Message message) {
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false); // 该方法先执行
            logger.info("接收处理队列当中的消息： " + new String(message.getBody(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
