package es.urjc.code.services.notifications;

import org.springframework.stereotype.Service;

@Service
public class CustomerNotificationService implements CustomerNotification {

    @Override
    public void send(Message message) {
        System.out.println(message.getMessage());
    }
}