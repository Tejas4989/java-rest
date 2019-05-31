package com.validus.javarest.controllers;

import com.validus.javarest.entities.Song;
import com.validus.javarest.services.ISongService;
import com.validus.javarest.util.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class SongRestAPIController {

  @Autowired
  private ISongService songService;


  @GetMapping("song/{id}")
  public ResponseEntity<Song> getSongById(@PathVariable("id") Long id) {
    try {
      Song song = songService.getSongById(id);
      return new ResponseEntity<Song>(song, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Song with id " + id + " not found."), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("songs")
  public ResponseEntity<List<Song>> getAllSongs() {
    try {
      List<Song> artists = songService.getAllSongs();
      return new ResponseEntity<List<Song>>(artists, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("song/{albumId}")
  public ResponseEntity<Void> addSong( @PathVariable("albumId") Long albumId, @RequestBody Song song) {
    boolean flag = songService.addSong(song, albumId);
    if (flag == false) {
      return new ResponseEntity(new CustomError("Song with name: " + song.getName() + " is already exist."), HttpStatus.CONFLICT);
    }
    return new ResponseEntity<Void>(HttpStatus.CREATED);
  }

  @PutMapping("song")
  public ResponseEntity<Song> updateSong(@RequestBody Song song) {
    try {
      songService.updateSong(song);
      return new ResponseEntity<Song>(song, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Unable to update. Song with id " + song.getName() + " not found."), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("song/{id}")
  public ResponseEntity<Void> deleteSong(@PathVariable("id") Long id) {
    try {
      Song song = songService.getSongById(id);
      songService.deleteSong(song);
      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Unable to delete. Song with id " + id + " not found."), HttpStatus.NOT_FOUND);
    }
  }

}
