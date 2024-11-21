package com.rest.oauth2.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "artist")
public class ArtistEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTIST_ID_GENERATOR")
  @SequenceGenerator(
      name = "ARTIST_ID_GENERATOR",
      sequenceName = "ARTIST_ID_GENERATOR_SEQUENCE",
      allocationSize = 1)
  private long id;

  private String name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ArtistEntity{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
