package com.validus.javarest.controllers;

import com.validus.javarest.entities.Album;
import com.validus.javarest.services.IAlbumService;
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
public class AlbumRestAPIController {

  @Autowired
  private IAlbumService albumService;


  @GetMapping("album/{id}")
  public ResponseEntity<Album> getAlbumById(@PathVariable("id") Long id) {
    try {
      Album album = albumService.getAlbumById(id);
      return new ResponseEntity<Album>(album, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Album with id " + id + " not found."), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("albums")
  public ResponseEntity<List<Album>> getAllAlbums() {
    try {
      List<Album> artists = albumService.getAllAlbums();
      return new ResponseEntity<List<Album>>(artists, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("albums/{artistId}")
  public ResponseEntity<List<Album>> getAllAlbumsByArtist(@PathVariable("artistId") Long artistId) {
    try {
      List<Album> artists = albumService.getAllAlbumsByArtist(artistId);
      return new ResponseEntity<List<Album>>(artists, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Albums for artistId " + artistId + " not found."), HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("album/{artistId}")
  public ResponseEntity<Void> addAlbum(@PathVariable("artistId") Long artistId, @RequestBody Album album) {
    boolean flag = albumService.addAlbum(album, artistId);
    if (flag == false) {
      return new ResponseEntity(new CustomError("Album with name: " + album.getName() + " is already exist."), HttpStatus.CONFLICT);
    }
    return new ResponseEntity<Void>(HttpStatus.CREATED);
  }

  @PutMapping("album")
  public ResponseEntity<Album> updateAlbum(@RequestBody Album album) {
    try {
      albumService.updateAlbum(album);
      return new ResponseEntity<Album>(album, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Unable to update. Album with name " + album.getName() + " not found."), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("album/{id}")
  public ResponseEntity<Void> deleteAlbum(@PathVariable("id") Long id) {
    try {
      Album album = albumService.getAlbumById(id);
      albumService.deleteAlbum(album);
      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Unable to delete. Album with id " + id + " not found."), HttpStatus.NOT_FOUND);
    }
  }

}
