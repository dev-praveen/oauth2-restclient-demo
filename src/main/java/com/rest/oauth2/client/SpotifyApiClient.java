package com.rest.oauth2.client;

import com.rest.oauth2.model.Artist;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpotifyApiClient {

  private final RestClient restClient;

  // private final RestTemplate restTemplate;

  public SpotifyApiClient(RestClient restClient) {
    this.restClient = restClient;
  }

  /*public SpotifyApiClient(RestTemplate restTemplate, RestClient restClient) {
    this.restTemplate = restTemplate;
    this.restClient = restClient;
  }*/

  public ResponseEntity<Artist> getArtistDetailsRest(String artistId) {

    return restClient.get().uri("/artists/{artistId}", artistId).retrieve().toEntity(Artist.class);
  }

  /*public ResponseEntity<Artist> getArtistDetails(String artistId) {

    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("artistId", artistId);
    return restTemplate.getForEntity("/artists/{artistId}", Artist.class, uriVariables);
  }*/
}
