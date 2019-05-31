package com.validus.javarest.services;

import com.validus.javarest.entities.Album;
import com.validus.javarest.entities.Song;
import com.validus.javarest.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongService implements ISongService {

  @Autowired
  private SongRepository songRepository;

  @Autowired
  private IAlbumService albumService;

  @Override
  public Song getSongById(Long songId) {
    return songRepository.findById(songId).get();
  }

  @Override
  public List<Song> getAllSongs() {
    List<Song> songs = new ArrayList<>();
    songRepository.findAll().forEach(song -> songs.add(song));
    return songs;
  }

  @Override
  public boolean addSong(Song song, Long albumId) {
    List<Song> songs = songRepository.findByName(song.getName());
    if(songs.size() > 0) {
      return false;
    }
    Album album = albumService.getAlbumById(albumId);
    album.getSongs().add(song);
    albumService.updateAlbum(album);
    return true;
  }

  @Override
  public void updateSong(Song song) {
    songRepository.save(song);
  }

  @Override
  public void deleteSong(Song song) {
    songRepository.delete(song);
  }
}

