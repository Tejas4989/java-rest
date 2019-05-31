package com.validus.javarest.repositories;

import com.validus.javarest.entities.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {

  List<Song> findByName(String name);
}
