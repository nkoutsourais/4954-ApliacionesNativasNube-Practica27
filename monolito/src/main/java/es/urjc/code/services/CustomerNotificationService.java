package es.urjc.code.services;

import org.springframework.stereotype.Service;

import es.urjc.code.domain.notifications.Message;

@Service
public class CustomerNotificationService {

    public void send(Message message) {
        System.out.println(message.getMessage());
    }
}