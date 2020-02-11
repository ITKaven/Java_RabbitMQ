package com.kaven.rabbitmq.exchange.defaultExchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DefaultConsumer {

    // 自己服务器的IP
    private static String ip = "47.112.7.219";
    // RabbitMQ启动的默认端口，也是应用程序进行连接RabbitMQ的端口
    private static int port = 5672;
    // RabbitMQ有一个 "/" 的虚拟主机
    private static String virtualHost = "/";

    // 定义的队列名
    private static String queueName = "test";

    // default exchange
    private static String exchange = "";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1 创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ip);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);

        // 2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3 通过连接创建一个Channel
        Channel channel = connection.createChannel();

        // 4 声明（创建）一个队列
        // 队列名、持久化、独占、自动删除、参数
        channel.queueDeclare(queueName , true,false , false , null);

        // 5 创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        // 6 设置Channel
        // 队列名、自动回复ACK(收到消息后)、消费者
        channel.basicConsume(queueName , true , queueingConsumer);

        // 7 获取消息
        while(true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端：" + msg);
        }
    }
}
