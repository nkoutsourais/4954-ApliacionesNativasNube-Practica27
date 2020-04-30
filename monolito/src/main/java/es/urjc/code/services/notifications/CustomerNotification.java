package es.urjc.code.services.notifications;

import es.urjc.code.domain.notifications.Message;

public interface CustomerNotification {

    void send(Message message);
}
