package com.kaven.rabbitmq.api.qos;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    // 自己服务器的IP
    private static String ip = "47.112.7.219";
    // RabbitMQ启动的默认端口，也是应用程序进行连接RabbitMQ的端口
    private static int port = 5672;
    // RabbitMQ有一个 "/" 的虚拟主机
    private static String virtualHost = "/";

    // default exchange
    private static String exchange = "";
    // 队列名
    private static String queueName = "test";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ip);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);

        // 2 创建Connection
        Connection connection = connectionFactory.newConnection();

        // 3 创建Channel
        Channel channel = connection.createChannel();

        // 4 创建Queue
        channel.queueDeclare(queueName , true , false , false , null);

        // 5 限流方式
        // 0-说明对消息大小不限制 ， 1-一次性最多消费1条消息 ，false-设置consumer而不是channel
        channel.basicQos(0 , 1 ,false);

        // 6 消费消息， autoAck一定要设置为false
        channel.basicConsume(queueName , false , new MyConsumer(channel));
    }
}
