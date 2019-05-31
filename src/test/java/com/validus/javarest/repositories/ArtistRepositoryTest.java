package com.validus.javarest.repositories;

import com.validus.javarest.entities.Artist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArtistRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ArtistRepository artistRepository;

  @Test
  public void whenFindByName_thenReturnArtist() {
    // given
    Artist artist = new Artist();
    artist.setId(111);
    artist.setName("Drake Test");
    entityManager.persist(artist);
    entityManager.flush();

    // when
    List<Artist> foundList = artistRepository.findArtistByName(artist.getName());
    assertEquals(artist.getName(), foundList.get(0).getName());
  }
}