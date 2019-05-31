package com.validus.javarest.services;

import com.validus.javarest.entities.Song;

import java.util.List;

public interface ISongService {

  List<Song> getAllSongs();

  Song getSongById(Long songId);

  boolean addSong(Song song, Long albumId);

  void updateSong(Song song);

  void deleteSong(Song song);
}
