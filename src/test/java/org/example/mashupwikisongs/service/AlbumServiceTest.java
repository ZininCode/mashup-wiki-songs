package org.example.mashupwikisongs.service;

import org.example.mashupwikisongs.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AlbumServiceTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ImageService imageService;
    private AlbumService albumService;
    @BeforeEach
    public void setUp() {
        albumService = new AlbumService(restTemplate, imageService);
    }

    @Test
    public void testAlbumService() {
        String imageUrl = "image.example.com";
        String mbid = "5b11f4ce-a62d-471e-81fc-a69a8278c7da";
        Album album = Album.builder()
                .id("albumId123")
                .title("albumTittle")
                .primaryType("Album")
                .build();
        List<Album> release_groups = new ArrayList<>();
        release_groups.add(album);
        Albums albums = new Albums();
        albums.setRelease_groups(release_groups);


        Mockito.when(imageService.extractImageUrl(Mockito.any(Album.class))).thenReturn(imageUrl);
        Mockito.when(restTemplate.getForObject(any(String.class), eq(Albums.class))).thenReturn(albums);

        List<AlbumAndImage> result = albumService.getAlbums(mbid);

        assertEquals(1, result.size());
        assertEquals("albumId123", result.get(0).getId());
        assertEquals("albumTittle", result.get(0).getTitle());
        assertEquals(imageUrl, result.get(0).getImage());
    }
}