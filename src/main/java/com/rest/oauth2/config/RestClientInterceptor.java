package com.rest.oauth2.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.util.Assert;
import java.io.IOException;

public class RestClientInterceptor implements ClientHttpRequestInterceptor {

  private final OAuth2AuthorizedClientManager manager;
  private final ClientRegistration clientRegistration;

  public RestClientInterceptor(
      OAuth2AuthorizedClientManager manager, ClientRegistration clientRegistration) {
    this.manager = manager;
    this.clientRegistration = clientRegistration;
  }

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    request.getHeaders().setBearerAuth(getBearerToken());
    return execution.execute(request, body);
  }

  private String getBearerToken() {
    OAuth2AuthorizeRequest oAuth2AuthorizeRequest =
        OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistration.getRegistrationId())
            .principal(clientRegistration.getClientId())
            .build();

    OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest);
    Assert.notNull(
        client,
        () ->
            "Authorized client failed for Registration id: '"
                + clientRegistration.getRegistrationId()
                + "', returned client is null");
    return client.getAccessToken().getTokenValue();
  }
}
