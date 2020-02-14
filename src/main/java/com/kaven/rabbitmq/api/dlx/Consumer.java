package com.kaven.rabbitmq.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    // 死信队列的设置
    private static String dlxExchange = "dlx.exchange";
    private static String dlxQueueName = "dlx.queue";
    private static String dlxRoutingKey = "#";

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

        // 4 设置死信队列
        Map<String , Object> arguments = new HashMap<String , Object>();
        arguments.put("x-dead-letter-exchange" , dlxExchange);
        channel.exchangeDeclare(dlxExchange , "topic" , true , false , null);
        channel.queueDeclare(dlxQueueName , true , false , false , null);
        channel.queueBind(dlxQueueName , dlxExchange , dlxRoutingKey);

        // 5 创建Queue
        channel.queueDeclare(queueName , true , false , false , arguments);

        // 6 消费消息
//        channel.basicConsume(queueName , true , new MyConsumer(channel));
    }
}
