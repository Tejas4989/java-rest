package com.validus.javarest.client;

import com.validus.javarest.entities.Artist;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class ArtistClientUtilTest {

  public void getArtistByIdTest() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:9090/api/artist/{id}";
    HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
    ResponseEntity<Artist> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Artist.class, 1);
    Artist artist = responseEntity.getBody();
    System.out.println("Id:"+artist.getId()+", Name:"+artist.getName());
  }
  public void getAllArtistsTest() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:9090/api/artists";
    HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
    ResponseEntity<Artist[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Artist[].class);
    Artist[] artists = responseEntity.getBody();
    for(Artist artist : artists) {
      System.out.println("Id:"+artist.getId()+", Name:"+artist.getName());
    }
  }
  public void addArtistTest() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:9090/api/artist";
    Artist artist = new Artist();
    artist.setName("Drake");
    HttpEntity<Artist> requestEntity = new HttpEntity<Artist>(artist, headers);
    URI uri = restTemplate.postForLocation(url, requestEntity);
    System.out.println(uri.getPath());
  }
  public void updateArtistTest() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:9090/api/artist";
    Artist artist = new Artist();
    artist.setId(100);
    artist.setName("Drake 1");
    HttpEntity<Artist> requestEntity = new HttpEntity<Artist>(artist, headers);
    restTemplate.put(url, requestEntity);
  }
  public void deleteArtistTest() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:9090/api/artist/{id}";
    HttpEntity<Artist> requestEntity = new HttpEntity<Artist>(headers);
    restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 4);
  }
  public static void main(String args[]) {
    ArtistClientUtilTest util = new ArtistClientUtilTest();
    util.getArtistByIdTest();
    util.addArtistTest();
    util.updateArtistTest();
    util.deleteArtistTest();
    util.getAllArtistsTest();
  }

}
