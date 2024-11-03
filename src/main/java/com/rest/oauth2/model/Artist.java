package com.rest.oauth2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Artist(
    @JsonProperty("external_urls") ExternalUrls externalUrls,
    Followers followers,
    List<String> genres,
    String href,
    String id,
    List<Image> images,
    String name,
    int popularity,
    String type,
    String uri) {

  public record ExternalUrls(String spotify) {}

  public record Followers(String href, int total) {}

  public record Image(int height, String url, int width) {}
}
