package com.msgmq.app;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.jms.Message;

public class TopicService {

    private Connection connection;
    private String subject = "TOOL.DEFAULT";

    public void receive(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        try{
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageConsumer consumer = session.createConsumer(destination);
            Message message = consumer.receive();
            // TODO
            message.clearProperties();
            consumer.close();
            session.close();
            connection.stop();
            connection.close();

        } catch (JMSException e){
            e.printStackTrace();
        }

    }


}
