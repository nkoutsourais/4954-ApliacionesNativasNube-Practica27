package es.urjc.code.services.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.urjc.code.domain.notifications.Message;

@Service
public class CustomerNotificationFeatureToggle implements CustomerNotification {

    @Value("${feature.toggle.notification.proxy}")
    private boolean proxy = false;

    @Autowired
    CustomerNotificationProxy customerNotificationProxy;
    @Autowired
    CustomerNotificationService customerNotificationService;

    @Override
    public void send(Message message) {
        if(proxy)
            customerNotificationProxy.send(message);
        else
            customerNotificationService.send(message);
    }
}