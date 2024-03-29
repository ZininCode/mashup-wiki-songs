package org.example.mashupwikisongs.service;

import org.example.mashupwikisongs.model.Album;
import org.example.mashupwikisongs.model.Image;
import org.example.mashupwikisongs.model.Images;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
/**
 * Date: 23.02.2024
 *
 * @author Nikolay Zinin
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ImageServiceTest {
    @Mock
    private RestTemplate restTemplate;

    private ImageService imageService;

    @BeforeEach
    public void setUp() {
        imageService = new ImageService(restTemplate);
    }

    @Test
    public void testExtractImageUrl() {
        Album album = Album.builder()
                .id("albumId1")
                .build();
        Image image = new Image("http://example.com/image.jpg");
        List<Image> imageList = new ArrayList<>();
        imageList.add(image);
        Images images = Images.builder()
                .images(imageList)
                .build();

        when(restTemplate.getForObject(any(String.class), ArgumentMatchers.eq(Images.class))).thenReturn(images);
        String imageUrl = imageService.extractImageUrl(album);
        assertEquals("http://example.com/image.jpg", imageUrl);
    }
}