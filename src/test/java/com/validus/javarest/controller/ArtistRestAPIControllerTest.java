package com.validus.javarest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.validus.javarest.controllers.ArtistRestAPIController;
import com.validus.javarest.entities.Artist;
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

import static com.validus.javarest.Utils.TestUtils.prepareDummyArtist;
import static com.validus.javarest.Utils.TestUtils.prepareListOfArtists;
import static org.hamcrest.Matchers.any;
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
public class ArtistRestAPIControllerTest {

  private static final ObjectMapper om = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ArtistRestAPIController artistRestAPIController;

  @Before
  public void init() {
    Artist artist = prepareDummyArtist(1l, "TestArtist");
    doReturn(new ResponseEntity<>(artist, HttpStatus.OK)).when(artistRestAPIController).getArtistById(1l);
  }

  @Test
  public void find_artistId_OK() throws Exception {

    mockMvc.perform(get("/api/artist/1"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("TestArtist")));

    verify(artistRestAPIController, times(1)).getArtistById(1L);

  }

  @Test
  public void find_allArtists_OK() throws Exception {

   doReturn(new ResponseEntity<List<Artist>>(prepareListOfArtists(), HttpStatus.OK)).when(artistRestAPIController).getAllArtists();

    mockMvc.perform(get("/api/artists"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].name", is("TestArtist1")))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].name", is("TestArtist2")))
        .andExpect(jsonPath("$[2].id", is(3)))
        .andExpect(jsonPath("$[2].name", is("TestArtist3")));

    verify(artistRestAPIController, times(1)).getAllArtists();
  }

  @Test
  public void find_artistIdNotFound_404() throws Exception {
    Long artistId = 5l;
    doReturn(new ResponseEntity(new CustomError("Artist with id " + artistId + " not found."), HttpStatus.NOT_FOUND)).when(artistRestAPIController).getArtistById(artistId);
    mockMvc.perform(get("/api/artist/"+artistId)).andExpect(status().isNotFound());
  }

//  @Test
  public void add_artist_OK() throws Exception {

    Artist artist = prepareDummyArtist(1000L, "TestArtist1000");
    doReturn(new ResponseEntity<Void>(HttpStatus.CREATED)).when(artistRestAPIController).addArtist(artist);

    mockMvc.perform(post("/api/artist")
        .content(om.writeValueAsString(prepareDummyArtist(1000L, "TestArtist1000")))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    verify(artistRestAPIController, times(1)).addArtist(artist);

  }

  @Test
  public void update_artist_OK() throws Exception {

    Artist artist = prepareDummyArtist(1L, "TestArtist1");
    doReturn(new ResponseEntity<Artist>(artist, HttpStatus.OK)).when(artistRestAPIController).updateArtist(artist);

    mockMvc.perform(put("/api/artist")
        .content(om.writeValueAsString(artist))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());


  }

  @Test
  public void delete_artist_OK() throws Exception {
    doReturn(new ResponseEntity<Void>(HttpStatus.OK)).when(artistRestAPIController).deleteArtist(1l);

    mockMvc.perform(delete("/api/artist/1"))
        .andExpect(status().isOk());

    verify(artistRestAPIController, times(1)).deleteArtist(1l);
  }

}
