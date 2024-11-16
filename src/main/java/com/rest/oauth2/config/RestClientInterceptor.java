package com.rest.oauth2.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.util.Assert;
import java.io.IOException;
import java.time.Instant;

public class RestClientInterceptor implements ClientHttpRequestInterceptor {

  private final OAuth2AuthorizedClientManager manager;
  private final ClientRegistration clientRegistration;
  private OAuth2AccessToken oAuth2AccessToken;

  public RestClientInterceptor(
      OAuth2AuthorizedClientManager manager, ClientRegistration clientRegistration) {
    this.manager = manager;
    this.clientRegistration = clientRegistration;
  }

  private static boolean isTokenValid(OAuth2AccessToken token) {
    return token != null
        && token.getExpiresAt() != null
        && token.getExpiresAt().isAfter(Instant.now());
  }

  @Override
  public @NonNull ClientHttpResponse intercept(
      @NonNull HttpRequest request,
      @NonNull byte[] body,
      @NonNull ClientHttpRequestExecution execution)
      throws IOException {

    final OAuth2AccessToken accessToken = getOauth2AccessToken();
    request.getHeaders().setBearerAuth(accessToken.getTokenValue());
    return execution.execute(request, body);
  }

  private OAuth2AccessToken getAccessToken() {

    final OAuth2AuthorizeRequest oAuth2AuthorizeRequest =
        OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistration.getRegistrationId())
            .principal(clientRegistration.getClientId())
            .build();

    final OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest);
    Assert.notNull(
        client,
        () ->
            "Authorized client failed for Registration id: '"
                + clientRegistration.getRegistrationId()
                + "', returned client is null");
    oAuth2AccessToken = client.getAccessToken();
    return oAuth2AccessToken;
  }

  private OAuth2AccessToken getOauth2AccessToken() {
    if (!isTokenValid(oAuth2AccessToken)) {
      System.out.println("Generating new oauth2 access token");
      return getAccessToken();
    }
    System.out.println("Using the same oauth2 access token");
    return oAuth2AccessToken;
  }
}
