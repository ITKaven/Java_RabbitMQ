package com.kaven.rabbitmq.exchange.topicExchange;

import com.rabbitmq.client.QueueingConsumer;

public class MyRunnable implements Runnable {

    private QueueingConsumer consumer;
    private String routingKey;

    public MyRunnable(QueueingConsumer consumer , String routingKey) {
        this.consumer = consumer;
        this.routingKey = routingKey;
    }

    @Override
    public void run() {
        while(true){
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String msg = new String(delivery.getBody());
                System.out.println(routingKey+"[收到]-"+msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
