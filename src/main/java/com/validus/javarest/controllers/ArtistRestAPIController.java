package com.validus.javarest.controllers;

import com.validus.javarest.entities.Artist;
import com.validus.javarest.services.IArtistService;
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
public class ArtistRestAPIController {

  @Autowired
  private IArtistService artistService;


  @GetMapping("artist/{id}")
  public ResponseEntity<Artist> getArtistById(@PathVariable("id") Long id) {
    try {
      Artist artist = artistService.getArtistById(id);
      return new ResponseEntity<Artist>(artist, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Artist with id " + id + " not found."), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("artists")
  public ResponseEntity<List<Artist>> getAllArtists() {
    try {
      List<Artist> artists = artistService.getAllArtists();
      return new ResponseEntity<List<Artist>>(artists, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("artist")
  public ResponseEntity<Void> addArtist(@RequestBody Artist artist) {
    boolean flag = artistService.addArtist(artist);
    if (flag == false) {
      return new ResponseEntity(new CustomError("Artist with name: " + artist.getName() + " is already exist."), HttpStatus.CONFLICT);
    }
    return new ResponseEntity<Void>(HttpStatus.CREATED);
  }

  @PutMapping("artist")
  public ResponseEntity<Artist> updateArtist(@RequestBody Artist artist) {
    try {
      artistService.updateArtist(artist);
      return new ResponseEntity<Artist>(artist, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Unable to update. Artist with name " + artist.getName() + " not found."), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("artist/{id}")
  public ResponseEntity<Void> deleteArtist(@PathVariable("id") Long id) {
    try {
      Artist artist = artistService.getArtistById(id);
      artistService.deleteArtist(artist);
      return new ResponseEntity<Void>(HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(new CustomError("Unable to delete. Artist with id " + id + " not found."), HttpStatus.NOT_FOUND);
    }
  }

}
