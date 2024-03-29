package org.example.mashupwikisongs.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mashupwikisongs.model.Album;
import org.example.mashupwikisongs.model.Images;
import org.example.mashupwikisongs.config.Status;
import org.example.mashupwikisongs.config.Url;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/**
 * Date: 21.02.2024
 *
 * @author Nikolay Zinin
 */
@Service
@AllArgsConstructor
@Slf4j
public class ImageService {
    private final RestTemplate restTemplate;
    public String extractImageUrl(Album album){
        String imageUrl = Status.NOT_FOUND;
        String albumUrl = Url.COVER_ARCHIVE_URL_PREFIX +album.getId();

        try {
            Images images = restTemplate.getForObject(albumUrl, Images.class);
            if (images != null) {
                imageUrl = images.getImages().isEmpty() ? Status.NOT_FOUND : images.getImages().get(0).getImage();
            }
        } catch (Exception e) {
            log.info("no image found for album {} ", album.getId());
        }

        return imageUrl;
    }
}

