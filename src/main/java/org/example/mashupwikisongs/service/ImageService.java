package org.example.mashupwikisongs.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mashupwikisongs.model.Album;
import org.example.mashupwikisongs.model.Images;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class ImageService {
    private final RestTemplate restTemplate;
    public String extractImageUrl(Album album){
        String imageUrl = "not found";
        String albumUrl = "http://coverartarchive.org/release-group/" +album.getId();
        try {
            Images images = restTemplate.getForObject(albumUrl, Images.class);
            imageUrl = images.getImages().isEmpty() ? "not found" : images.getImages().get(0).getImage();
        } catch (Exception e) {
            imageUrl = "not found";
            log.info("no image found for album {} ", album.getId());
        }
        return imageUrl;
    }
}

