package com.validus.javarest.services;

import com.validus.javarest.entities.Artist;
import com.validus.javarest.repositories.ArtistRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.validus.javarest.Utils.TestUtils.prepareDummyArtist;
import static com.validus.javarest.Utils.TestUtils.prepareListOfArtists;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ArtistServiceTest {

  @InjectMocks
  private ArtistService artistService;

  @Mock
  private ArtistRepository artistRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void whenSearchById_thenArtistShouldBeFound() {
    Artist Artist = prepareDummyArtist(101L, "Test");
    doReturn(Optional.of(Artist)).when(artistRepository).findById(101l);
    Artist found = artistService.getArtistById(101L);
    assertEquals(found.getName(), Artist.getName());
  }


  @Test
  public void whenGetAllArtists_thenArtistsShouldBeFound() {
    List<Artist> Artists = prepareListOfArtists();
    doReturn(Artists).when(artistRepository).findAll();
    List<Artist> foundList = artistService.getAllArtists();
    assertEquals(foundList.size(), Artists.size());
  }

  @Test
  public void whenAddArtist_thenArtistsShouldBeAdded() {
    Artist artist = prepareDummyArtist(1l, "TestArtist");
    artistService.addArtist(artist);
    verify(artistRepository, times(1)).save(artist);
  }

  @Test
  public void whenAddingDuplicateArtist_thenArtistsShouldNotBeAdded() {
    Artist artist = prepareDummyArtist(101L, "Test");
    doReturn(prepareListOfArtists()).when(artistRepository).findArtistByName("Test");
    artistService.addArtist(artist);
    verify(artistRepository, times(0)).save(artist);
  }

  @Test
  public void whenUpdatingArtist_thenArtistsShouldBeUpdated() {
    Artist artist = prepareDummyArtist(101L, "Test");
    artistService.updateArtist(artist);
    verify(artistRepository, times(1)).save(artist);
  }

  @Test
  public void whenDeletingArtist_thenArtistsShouldBeDeleted() {
    Artist artist = prepareDummyArtist(101L, "Test");
    doReturn(Optional.of(artist)).when(artistRepository).findById(101l);
    artistService.deleteArtist(artist);
    verify(artistRepository, times(1)).delete(artist);
  }

}