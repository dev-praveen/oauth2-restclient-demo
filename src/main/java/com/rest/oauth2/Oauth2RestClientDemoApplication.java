package com.rest.oauth2;

import com.rest.oauth2.client.SpotifyApiClient;
import com.rest.oauth2.dao.ArtistEntity;
import com.rest.oauth2.dao.ArtistRepository;
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
  CommandLineRunner runner(SpotifyApiClient spotifyApiClient, ArtistRepository artistRepository) {

    return args -> {
      final var artist = spotifyApiClient.getArtistDetailsRest("0TnOYISbd1XYRBk9myaseg").getBody();
      final var artist1 = spotifyApiClient.getArtistDetailsRest("0TnOYISbd1XYRBk9myaseg").getBody();
      final var artist2 = spotifyApiClient.getArtistDetailsRest("0TnOYISbd1XYRBk9myaseg").getBody();
      System.out.println(artist);
      System.out.println(artist1);
      System.out.println(artist2);

      final var artistEntity = new ArtistEntity();
      artistEntity.setName(artist.name());

      final var artistEntity1 = new ArtistEntity();
      artistEntity.setName(artist1.name());

      final var artistEntity2 = new ArtistEntity();
      artistEntity.setName(artist2.name());

      artistRepository.save(artistEntity);
      artistRepository.save(artistEntity1);
      artistRepository.save(artistEntity2);

      System.out.println(artistRepository.findAll());
    };
  }
}
