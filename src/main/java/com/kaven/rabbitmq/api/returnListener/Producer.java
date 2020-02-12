package com.kaven.rabbitmq.api.returnListener;

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
    private static String routingKeyError = "test_1";

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

        //4 添加ReturnListener
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("---------- handle return ------------");
                System.out.println("replyCode：" + replyCode);
                System.out.println("replyText：" + replyText);
                System.out.println("exchange：" + exchange);
                System.out.println("routingKey：" + routingKey);
                System.out.println("properties：" + properties);
                System.out.println("body：" + new String(body));
            }
        });

        // 5 发送消息
        String msg = "RabbitMQ: return message";
        String msgError = "RabbitMQ: error return message";
        // mandatory 为 true
        channel.basicPublish(exchange , routingKey , true,null , msg.getBytes());
        channel.basicPublish(exchange , routingKeyError , true,null , msgError.getBytes());
    }
}
