package com.validus.javarest.Utils;

import com.validus.javarest.entities.Album;
import com.validus.javarest.entities.Artist;
import com.validus.javarest.entities.Song;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

  public static List<Artist> prepareListOfArtists() {
    List<Artist> artists = new ArrayList<>();
    artists.add(prepareDummyArtist(1l, "TestArtist1"));
    artists.add(prepareDummyArtist(2l, "TestArtist2"));
    artists.add(prepareDummyArtist(3l, "TestArtist3"));
    return artists;
  }

  public static Artist prepareDummyArtist(Long id, String name) {
    Artist artist = new Artist();
    artist.setId(id);
    artist.setName(name);
    return artist;
  }

  public static List<Album> prepareListOfAlbums() {
    List<Album> albums = new ArrayList<>();
    albums.add(prepareDummyAlbum(1l, "TestAlbum1"));
    albums.add(prepareDummyAlbum(2l, "TestAlbum2"));
    albums.add(prepareDummyAlbum(3l, "TestAlbum3"));
    return albums;
  }

  public static Album prepareDummyAlbum(Long id, String name) {
    Album album = new Album();
    album.setId(id);
    album.setName(name);
    return album;
  }

  public static List<Song> prepareListOfSongs() {
    List<Song> songs = new ArrayList<>();
    songs.add(prepareDummySong(1l, "TestSong1"));
    songs.add(prepareDummySong(2l, "TestSong2"));
    songs.add(prepareDummySong(3l, "TestSong3"));
    return songs;
  }

  public static Song prepareDummySong(Long id, String name) {
    Song song = new Song();
    song.setId(id);
    song.setName(name);
    return song;
  }
}
