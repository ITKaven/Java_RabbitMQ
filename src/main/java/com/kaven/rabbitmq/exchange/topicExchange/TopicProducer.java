package com.kaven.rabbitmq.exchange.topicExchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicProducer {

    // 自己服务器的IP
    private static String ip = "47.112.7.219";
    // RabbitMQ启动的默认端口，也是应用程序进行连接RabbitMQ的端口
    private static int port = 5672;
    // RabbitMQ有一个 "/" 的虚拟主机
    private static String virtualHost = "/";

    // topic exchange ，RabbitMQ提供的topic exchange
    private static String exchangeName = "amq.topic";
    // exchange type
    private static String exchangeType= "topic";

    // 交换机路由的routingKey
    private static String[] routingKey = {"test.kaven.wyy" , "test" , "test.topic"};

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ip);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);

        // 2 创建连接
        Connection connection = connectionFactory.newConnection();

        // 3 创建Channel
        Channel channel = connection.createChannel();

        // 4 发送消息
        for (int i = 0; i < routingKey.length; i++) {
            String msg = "RabbitMQ：Topic Exchange 发送数据 , routingKey："+routingKey[i];
            channel.basicPublish(exchangeName ,routingKey[i] ,null, msg.getBytes());
        }

        // 5 关闭连接
        channel.close();
        connection.close();
    }
}
