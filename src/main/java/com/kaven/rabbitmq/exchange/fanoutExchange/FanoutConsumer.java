package com.kaven.rabbitmq.exchange.fanoutExchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutConsumer {

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
    // 队列名
    private static String queueName = "queue";
    // 队列与交换机绑定的routingKey
    private static String routingKey = "test";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1 创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ip);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);

        // 2 创建连接
        Connection connection = connectionFactory.newConnection();

        // 3 创建Channel
        Channel channel = connection.createChannel();

        // 4 定义Queue ，将Queue绑定到direct exchange
        channel.queueDeclare(queueName,true , false , false , null);
        channel.queueBind(queueName , exchangeName , routingKey);

        // 5 创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        // 6 设置
        channel.basicConsume(queueName , true , consumer);

        // 7 接收消息
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println(msg);
        }
    }
}
