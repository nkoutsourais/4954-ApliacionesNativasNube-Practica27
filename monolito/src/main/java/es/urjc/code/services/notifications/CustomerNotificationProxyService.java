package es.urjc.code.services.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.urjc.code.domain.notifications.Message;

@Component
public class CustomerNotificationProxyService implements CustomerNotification {

    @Autowired
    private CustomerNotificationProxy customerNotificationProxy;

    @Override
    public void send(Message message) {
        customerNotificationProxy.send(message);
    }
}