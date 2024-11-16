package com.rest.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean
  RestClient restClient(
      RestClient.Builder builder,
      OAuth2AuthorizedClientManager authorizedClientManager,
      ClientRegistrationRepository clientRegistrationRepository) {

    final ClientRegistration clientRegistration =
        clientRegistrationRepository.findByRegistrationId("spotify");
    final ClientHttpRequestInterceptor interceptor =
        new RestClientInterceptor(authorizedClientManager, clientRegistration);
    return builder.requestInterceptor(interceptor).baseUrl("https://api.spotify.com/v1").build();
  }

  @Bean
  OAuth2AuthorizedClientManager authorizedClientManager(
      ClientRegistrationRepository clientRegistrationRepository,
      OAuth2AuthorizedClientService authorizedClientService) {

    final OAuth2AuthorizedClientProvider authorizedClientProvider =
        OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();

    final AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
        new AuthorizedClientServiceOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientService);
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

    return authorizedClientManager;
  }
}
