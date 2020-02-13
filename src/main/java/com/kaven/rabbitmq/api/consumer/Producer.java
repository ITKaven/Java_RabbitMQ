package com.kaven.rabbitmq.api.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
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
        // 1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ip);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);

        // 2 创建Connection
        Connection connection = connectionFactory.newConnection();

        // 3 创建Channel
        Channel channel = connection.createChannel();


        // 4 发送消息
        for (int i = 0; i < 5; i++) {
            String msg = "RabbitMQ: consumer message" + i;
            channel.basicPublish(exchange , routingKey , null , msg.getBytes());
        }

        // 5 关闭连接
        channel.close();
        connection.close();
    }
}
