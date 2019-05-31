package com.validus.javarest.repositories;

import com.validus.javarest.entities.Album;
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
public class AlbumRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AlbumRepository albumRepository;

  @Test
  public void whenFindByName_thenReturnAlbum() {
    // given
    Album album = new Album();
    album.setId(101);
    album.setName("TestAlbum");
    entityManager.persist(album);
    entityManager.flush();

    // when
    List<Album> foundList = albumRepository.findByName(album.getName());
    assertEquals(album.getName(), foundList.get(0).getName());
  }
}
