package com.kaven.rabbitmq.exchange.topicExchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicConsumer {

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
    // 队列名
    private static String[] queueName = {"queue_#" , "queue_*"};
    // 队列与交换机绑定的routingKey
    private static String[] routingKey = {"test.#" , "test.*"};

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

        for (int i = 0; i < queueName.length; i++) {
            // 4 定义Queue ，将Queue绑定到direct exchange
            channel.queueDeclare(queueName[i],true , false , false , null);
            channel.queueBind(queueName[i] , exchangeName , routingKey[i]);
        }


        // 5 创建消费者
        QueueingConsumer consumer0 = new QueueingConsumer(channel);
        QueueingConsumer consumer1 = new QueueingConsumer(channel);

        // 6 设置
        channel.basicConsume(queueName[0] , true , consumer0);
        channel.basicConsume(queueName[1] , true , consumer1);

        // 7 接收消息
        Thread thread0 = new Thread(new MyRunnable(consumer0 , routingKey[0]));
        Thread thread1 = new Thread(new MyRunnable(consumer1 , routingKey[1]));

        thread0.start();
        thread1.start();

        thread0.join();
        thread1.join();
    }
}
