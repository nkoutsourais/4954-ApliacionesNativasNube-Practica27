package es.urjc.code.services.notifications;

import org.springframework.stereotype.Component;

import es.urjc.code.domain.notifications.Message;

@Component
public class CustomerNotificationService implements CustomerNotification {

    @Override
    public void send(Message message) {
        System.out.println(message.getMessage());
    }
}