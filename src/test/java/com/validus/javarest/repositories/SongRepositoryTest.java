package com.validus.javarest.repositories;

import com.validus.javarest.entities.Album;
import com.validus.javarest.entities.Song;
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
public class SongRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private SongRepository songRepository;

  @Test
  public void whenFindByName_thenReturnSong() {
    // given
    Song song = new Song();
    song.setId(101);
    song.setName("TestAlbum");
    song.setTrack(12);
    entityManager.persist(song);
    entityManager.flush();

    // when
    List<Song> foundList = songRepository.findByName(song.getName());
    assertEquals(song.getName(), foundList.get(0).getName());
  }
}