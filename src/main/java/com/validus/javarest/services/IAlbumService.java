package com.validus.javarest.services;

import com.validus.javarest.entities.Album;

import java.util.List;

public interface IAlbumService {

  List<Album> getAllAlbums();

  List<Album> getAllAlbumsByArtist(Long artistId);

  Album getAlbumById(Long albumId);

  boolean addAlbum(Album album, Long artistId);

  void updateAlbum(Album album);

  void deleteAlbum(Album album);
}
