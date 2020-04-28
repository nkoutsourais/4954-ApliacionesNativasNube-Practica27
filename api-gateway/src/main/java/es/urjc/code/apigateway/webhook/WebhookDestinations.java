package es.urjc.code.apigateway.webhook;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "webhook.destinations")
public class WebhookDestinations {

  @NotNull
  private String webhookServiceUrl;

  public String getWebhookServiceUrl() {
      return webhookServiceUrl;
  }

  public void setWebhookServiceUrl(String webhookServiceUrl) {
      this.webhookServiceUrl = webhookServiceUrl;
  }
}