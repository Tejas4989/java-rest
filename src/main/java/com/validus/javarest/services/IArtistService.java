package com.validus.javarest.services;

import com.validus.javarest.entities.Artist;

import java.util.List;

public interface IArtistService {

  List<Artist> getAllArtists();

  Artist getArtistById(long artistId);

  boolean addArtist(Artist artist);

  void updateArtist(Artist artist);

  void deleteArtist(Artist artist);
}
