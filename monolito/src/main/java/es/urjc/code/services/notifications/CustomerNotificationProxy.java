package es.urjc.code.services.notifications;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Response;

@FeignClient(name = "notifications", url = "${notification.server}")
public interface CustomerNotificationProxy {
    
    @RequestMapping(method = RequestMethod.POST, value = "api/notifications", consumes = "application/json")
    Response send(Message message);
}