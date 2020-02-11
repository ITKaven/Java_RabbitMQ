package com.kaven.rabbitmq.exchange.defaultExchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DefaultProducer {

    // 自己服务器的IP
    private static String ip = "47.112.7.219";
    // RabbitMQ启动的默认端口，也是应用程序进行连接RabbitMQ的端口
    private static int port = 5672;
    // RabbitMQ有一个 "/" 的虚拟主机
    private static String virtualHost = "/";

    // default exchange
    private static String exchange = "";
    // default exchange 的路由规则： routingKey（test） 将匹配同名的 queue(test)
    private static String routingKey = "test";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ip);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);

        // 2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3 通过连接创建一个Channel
        Channel channel = connection.createChannel();

        // 4 通过Channel 发送数据
        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ：" + i;
            // 1 default的exchange ,2 routingKey
            channel.basicPublish(exchange,routingKey, null , msg.getBytes());
        }

        // 5 关闭相关的连接
        channel.close();
        connection.close();
    }
}
