package com.validus.javarest.repositories;

import com.validus.javarest.entities.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {

  List<Artist> findArtistByName(String name);

}
