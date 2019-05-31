package com.validus.javarest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.validus.javarest.controllers.AlbumRestAPIController;
import com.validus.javarest.entities.Album;
import com.validus.javarest.util.CustomError;
import org.junit.Before;
import org.junit.Ignore;
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

import static com.validus.javarest.Utils.TestUtils.prepareDummyAlbum;
import static com.validus.javarest.Utils.TestUtils.prepareListOfAlbums;
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
public class AlbumRestAPIControllerTest {

  private static final ObjectMapper om = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AlbumRestAPIController albumRestAPIController;

  @Before
  public void init() {
    Album album = prepareDummyAlbum(1l, "TestAlbum");
    doReturn(new ResponseEntity<>(album, HttpStatus.OK)).when(albumRestAPIController).getAlbumById(1l);
  }

  @Test
  public void find_albumId_OK() throws Exception {

    mockMvc.perform(get("/api/album/1"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("TestAlbum")));

    verify(albumRestAPIController, times(1)).getAlbumById(1L);

  }

  @Test
  public void find_allAlbums_OK() throws Exception {

   doReturn(new ResponseEntity<List<Album>>(prepareListOfAlbums(), HttpStatus.OK)).when(albumRestAPIController).getAllAlbums();

    mockMvc.perform(get("/api/albums"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].name", is("TestAlbum1")))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].name", is("TestAlbum2")))
        .andExpect(jsonPath("$[2].id", is(3)))
        .andExpect(jsonPath("$[2].name", is("TestAlbum3")));

    verify(albumRestAPIController, times(1)).getAllAlbums();
  }

  @Test
  public void find_albumIdNotFound_404() throws Exception {
    Long albumId = 5l;
    doReturn(new ResponseEntity(new CustomError("Album with id " + albumId + " not found."), HttpStatus.NOT_FOUND)).when(albumRestAPIController).getAlbumById(albumId);
    mockMvc.perform(get("/api/album/"+albumId)).andExpect(status().isNotFound());
  }

//  @Test
  public void add_album_OK() throws Exception {

    Album album = prepareDummyAlbum(1000L, "TestAlbum1000");
    doReturn(new ResponseEntity<Void>(HttpStatus.CREATED)).when(albumRestAPIController).addAlbum(1l,album);

    mockMvc.perform(post("/api/album")
        .content(om.writeValueAsString(prepareDummyAlbum(1000L, "TestAlbum1000")))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    verify(albumRestAPIController, times(1)).addAlbum(1l,album);

  }

  @Test
  public void update_album_OK() throws Exception {

    Album album = prepareDummyAlbum(1L, "TestAlbum1");
    doReturn(new ResponseEntity<Album>(album, HttpStatus.OK)).when(albumRestAPIController).updateAlbum(album);

    mockMvc.perform(put("/api/album")
        .content(om.writeValueAsString(album))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());


  }

  @Test
  public void delete_album_OK() throws Exception {
    doReturn(new ResponseEntity<Void>(HttpStatus.OK)).when(albumRestAPIController).deleteAlbum(1l);

    mockMvc.perform(delete("/api/album/1"))
        .andExpect(status().isOk());

    verify(albumRestAPIController, times(1)).deleteAlbum(1l);
  }

}
