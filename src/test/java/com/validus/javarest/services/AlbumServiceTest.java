package com.validus.javarest.services;

import com.validus.javarest.entities.Album;
import com.validus.javarest.entities.Artist;
import com.validus.javarest.repositories.AlbumRepository;
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
import static com.validus.javarest.Utils.TestUtils.prepareDummyArtist;
import static com.validus.javarest.Utils.TestUtils.prepareListOfAlbums;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class AlbumServiceTest {

  @InjectMocks
  private AlbumService albumService;
  @Mock
  private ArtistService artistService;

  @Mock
  private AlbumRepository albumRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void whenSearchById_thenAlbumShouldBeFound() {
    Album album = prepareDummyAlbum(101L, "Test");
    doReturn(Optional.of(album)).when(albumRepository).findById(101l);
    Album found = albumService.getAlbumById(101L);
    assertEquals(found.getName(), album.getName());
  }


  @Test
  public void whenGetAllAlbums_thenAlbumsShouldBeFound() {
    List<Album> albums = prepareListOfAlbums();
    doReturn(albums).when(albumRepository).findAll();
    List<Album> foundList = albumService.getAllAlbums();
    assertEquals(foundList.size(), albums.size());
  }

  @Test
  public void whenAddAlbum_thenAlbumsShouldBeAdded() {
    Album album = prepareDummyAlbum(101L, "Test");
    Artist artist = prepareDummyArtist(1l, "TestArtist");
    doReturn(artist).when(artistService).getArtistById(1l);
    albumService.addAlbum(album, 1l);
    assertEquals(1, artist.getAlbums().size());
  }

  @Test
  public void whenAddingDuplicateAlbum_thenAlbumsShouldNotBeAdded() {
    Album album = prepareDummyAlbum(101L, "Test");
    doReturn(prepareListOfAlbums()).when(albumRepository).findByName("Test");
    albumService.addAlbum(album, 1l);
    verify(albumRepository, times(0)).save(album);
  }

  @Test
  public void whenUpdatingAlbum_thenAlbumsShouldBeUpdated() {
    Album album = prepareDummyAlbum(101L, "Test");
    albumService.updateAlbum(album);
    verify(albumRepository, times(1)).save(album);
  }

  @Test
  public void whenDeletingAlbum_thenAlbumsShouldBeDeleted() {
    Album album = prepareDummyAlbum(101L, "Test");
    doReturn(Optional.of(album)).when(albumRepository).findById(101l);
    albumService.deleteAlbum(album);
    verify(albumRepository, times(1)).delete(album);
  }

  @Test
  public void whenGetAllAlbumsByArtist_thenAlbumsShouldBeReturned() {
    Artist artist = prepareDummyArtist(1l, "TestArtist");
    artist.getAlbums().addAll(prepareListOfAlbums());
    doReturn(artist).when(artistService).getArtistById(1l);
    List<Album> foundList = albumService.getAllAlbumsByArtist(1l);
    assertEquals(foundList.size(), artist.getAlbums().size());
  }

  @Test
  public void whenGetAllAlbumsByArtist_thenEmptyAlbumsShouldBeReturned() {
    Artist artist = prepareDummyArtist(1l, "TestArtist");
    doReturn(artist).when(artistService).getArtistById(1l);
    List<Album> foundList = albumService.getAllAlbumsByArtist(1l);
    assertEquals(foundList.size(), artist.getAlbums().size());
  }


}