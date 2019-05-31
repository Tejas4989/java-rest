package com.validus.javarest.services;

import com.validus.javarest.entities.Album;
import com.validus.javarest.entities.Song;
import com.validus.javarest.repositories.SongRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.validus.javarest.Utils.TestUtils.prepareDummyAlbum;
import static com.validus.javarest.Utils.TestUtils.prepareDummySong;
import static com.validus.javarest.Utils.TestUtils.prepareListOfSongs;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class SongServiceTest {

  @Mock
  private AlbumService albumService;

  @InjectMocks
  private SongService songService;

  @Mock
  private SongRepository songRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void whenSearchById_thenSongShouldBeFound() {
    Song song = prepareDummySong(101L, "Test");
    doReturn(Optional.of(song)).when(songRepository).findById(101l);
    Song found = songService.getSongById(101L);
    assertEquals(found.getName(), song.getName());
  }


  @Test
  public void whenGetAllSongs_thenSongsShouldBeFound() {
    List<Song> songs = prepareListOfSongs();
    doReturn(songs).when(songRepository).findAll();
    List<Song> foundList = songService.getAllSongs();
    assertEquals(foundList.size(), songs.size());
  }

  @Test
  public void whenAddSong_thenSongsShouldBeAdded() {
    Song song = prepareDummySong(101L, "Test");
    Album album = prepareDummyAlbum(1l, "TestAlbum");
    doReturn(album).when(albumService).getAlbumById(1l);
    songService.addSong(song, 1l);
    assertEquals(1, album.getSongs().size());
  }

  @Test
  public void whenAddingDuplicateSong_thenSongsShouldNotBeAdded() {
    Song song = prepareDummySong(101L, "Test");
    doReturn(prepareListOfSongs()).when(songRepository).findByName("Test");
    songService.addSong(song, 1l);
    verify(songRepository, times(0)).save(song);
  }

  @Test
  public void whenUpdatingSong_thenSongsShouldBeUpdated() {
    Song song = prepareDummySong(101L, "Test");
    songService.updateSong(song);
    verify(songRepository, times(1)).save(song);
  }

  @Test
  public void whenDeletingSong_thenSongsShouldBeDeleted() {
    Song song = prepareDummySong(101L, "Test");
    doReturn(Optional.of(song)).when(songRepository).findById(101l);
    songService.deleteSong(song);
    verify(songRepository, times(1)).delete(song);
  }
}