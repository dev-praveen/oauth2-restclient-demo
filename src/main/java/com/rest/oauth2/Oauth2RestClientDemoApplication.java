package com.rest.oauth2;

import com.rest.oauth2.client.SpotifyApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Oauth2RestClientDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(Oauth2RestClientDemoApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(SpotifyApiClient spotifyApiClient) {

    return args -> {
      final var body = spotifyApiClient.getArtistDetailsRest("0TnOYISbd1XYRBk9myaseg").getBody();
      System.out.println(body);
    };
  }
}
