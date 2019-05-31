package com.validus.javarest.repositories;

import com.validus.javarest.entities.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

  List<Album> findByName(String name);
}
