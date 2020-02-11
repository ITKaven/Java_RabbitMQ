package com.kaven.rabbitmq.exchange.fanoutExchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutProducer {

    // 自己服务器的IP
    private static String ip = "47.112.7.219";
    // RabbitMQ启动的默认端口，也是应用程序进行连接RabbitMQ的端口
    private static int port = 5672;
    // RabbitMQ有一个 "/" 的虚拟主机
    private static String virtualHost = "/";

    // fanout exchange ，RabbitMQ提供的fanout exchange
    private static String exchangeName = "amq.fanout";
    // exchange type
    private static String exchangeType= "fanout";

    // 交换机路由的routingKey
    private static String routingKey = "";

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
        String msg = "RabbitMQ：Fanout Exchange 发送数据";
        channel.basicPublish(exchangeName ,routingKey ,null, msg.getBytes());

        // 5 关闭连接
        channel.close();
        connection.close();
    }
}
