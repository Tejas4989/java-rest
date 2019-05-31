package com.validus.javarest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.validus.javarest.controllers.SongRestAPIController;
import com.validus.javarest.entities.Song;
import com.validus.javarest.util.CustomError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.validus.javarest.Utils.TestUtils.prepareListOfSongs;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SongRestAPIControllerTest {

  private static final ObjectMapper om = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SongRestAPIController songRestAPIController;

  @Before
  public void init() {
    Song song = prepareDummySong(1l, "TestSong");
    doReturn(new ResponseEntity<>(song, HttpStatus.OK)).when(songRestAPIController).getSongById(1l);
  }

  private Song prepareDummySong(Long id, String name) {
    Song song = new Song();
    song.setId(id);
    song.setName(name);
    return song;
  }

  @Test
  public void find_songId_OK() throws Exception {

    mockMvc.perform(get("/api/song/1"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("TestSong")));

    verify(songRestAPIController, times(1)).getSongById(1L);

  }

  @Test
  public void find_allSongs_OK() throws Exception {

   doReturn(new ResponseEntity<List<Song>>(prepareListOfSongs(), HttpStatus.OK)).when(songRestAPIController).getAllSongs();

    mockMvc.perform(get("/api/songs"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].name", is("TestSong1")))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].name", is("TestSong2")))
        .andExpect(jsonPath("$[2].id", is(3)))
        .andExpect(jsonPath("$[2].name", is("TestSong3")));

    verify(songRestAPIController, times(1)).getAllSongs();
  }

  @Test
  public void find_songIdNotFound_404() throws Exception {
    Long songId = 5l;
    doReturn(new ResponseEntity(new CustomError("Song with id " + songId + " not found."), HttpStatus.NOT_FOUND)).when(songRestAPIController).getSongById(songId);
    mockMvc.perform(get("/api/song/"+songId)).andExpect(status().isNotFound());
  }

//  @Test
  public void add_song_OK() throws Exception {

    Song song = prepareDummySong(1000L, "TestSong1000");
    doReturn(new ResponseEntity<Void>(HttpStatus.CREATED)).when(songRestAPIController).addSong(1l,song);

    mockMvc.perform(post("/api/song")
        .content(om.writeValueAsString(prepareDummySong(1000L, "TestSong1000")))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    verify(songRestAPIController, times(1)).addSong(1l,song);

  }

  @Test
  public void update_song_OK() throws Exception {

    Song song = prepareDummySong(1L, "TestSong1");
    doReturn(new ResponseEntity<Song>(song, HttpStatus.OK)).when(songRestAPIController).updateSong(song);

    mockMvc.perform(put("/api/song")
        .content(om.writeValueAsString(song))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());


  }

  @Test
  public void delete_song_OK() throws Exception {
    doReturn(new ResponseEntity<Void>(HttpStatus.OK)).when(songRestAPIController).deleteSong(1l);

    mockMvc.perform(delete("/api/song/1"))
        .andExpect(status().isOk());

    verify(songRestAPIController, times(1)).deleteSong(1l);
  }

}
