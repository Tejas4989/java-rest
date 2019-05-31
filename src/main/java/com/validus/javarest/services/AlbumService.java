package com.validus.javarest.services;

import com.validus.javarest.entities.Album;
import com.validus.javarest.entities.Artist;
import com.validus.javarest.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService implements IAlbumService{

  @Autowired
  private AlbumRepository albumRepository;

  @Autowired
  private IArtistService artistService;

  @Override
  public Album getAlbumById(Long albumId) {
    return albumRepository.findById(albumId).get();
  }

  @Override
  public List<Album> getAllAlbums() {
    List<Album> albums = new ArrayList<>();
    albumRepository.findAll().forEach(album -> albums.add(album));
    return albums;
  }

  @Override
  public boolean addAlbum(Album album, Long artistId) {
    if(albumRepository.findByName(album.getName()).size() > 0){
      return false;
    }
    Artist artist = artistService.getArtistById(artistId);
    artist.getAlbums().add(album);
    artistService.updateArtist(artist);
    return true;
  }

  @Override
  public void updateAlbum(Album album) {
    albumRepository.save(album);
  }

  @Override
  public void deleteAlbum(Album album) {
    albumRepository.delete(album);
  }

  @Override
  public List<Album> getAllAlbumsByArtist(Long artistId) {
    Artist artist = artistService.getArtistById(artistId);
    return artist.getAlbums();
  }
}