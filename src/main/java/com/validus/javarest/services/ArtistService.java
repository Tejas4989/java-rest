package com.validus.javarest.services;

import com.validus.javarest.entities.Artist;
import com.validus.javarest.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistService implements IArtistService {

  @Autowired
  private ArtistRepository artistRepository;

  @Override
  public Artist getArtistById(long artistId) {
    return artistRepository.findById(artistId).get();
  }

  @Override
  public List<Artist> getAllArtists() {
    List<Artist> artists = new ArrayList<>();
    artistRepository.findAll().forEach(artist -> artists.add(artist));
    return artists;
  }

  @Override
  public boolean addArtist(Artist artist) {
    if(artistRepository.findArtistByName(artist.getName()).size() > 0){
      return false;
    }
    artistRepository.save(artist);
    return true;
  }

  @Override
  public void updateArtist(Artist artist) {
    artistRepository.save(artist);
  }

  @Override
  public void deleteArtist(Artist artist) {
    artistRepository.delete(artist);
  }
}
