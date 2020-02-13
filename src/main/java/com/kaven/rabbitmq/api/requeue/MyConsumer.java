package com.kaven.rabbitmq.api.requeue;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {

    public Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("------------ consumer message -----------");
        System.out.println("body：" + new String(body));

        // 休眠两秒钟 ， 使效果更明显
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if((Integer)(properties.getHeaders().get("num")) == 0){
            // multiple-批量处理为false ， 重回队列为true
            channel.basicNack(envelope.getDeliveryTag() , false , true);
        } else{
            // multiple-批量处理为false
            channel.basicAck(envelope.getDeliveryTag() , false);
        }
    }
}
