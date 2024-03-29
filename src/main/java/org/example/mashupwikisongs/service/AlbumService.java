package org.example.mashupwikisongs.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mashupwikisongs.model.AlbumAndImage;
import org.example.mashupwikisongs.model.Albums;
import org.example.mashupwikisongs.config.Url;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 21.02.2024
 *
 * @author Nikolay Zinin
 */
@Service
@AllArgsConstructor
@Slf4j
public class AlbumService {
    private final RestTemplate restTemplate;
    private final ImageService imageService;

    public List<org.example.mashupwikisongs.model.AlbumAndImage> getAlbums(String mbid){
       String albumUrl = Url.MUSICBRAINZ_RELATIONS_URL_PREFIX + mbid + Url.MUSICBRAINZ_RELEASE_GROUPS_URL_SUFFIX;
       List<AlbumAndImage> albumAndImageList = new ArrayList<>();

       try {
           Albums albums = restTemplate.getForObject(
                   albumUrl, Albums.class);

           if (albums != null) {
               albums.getRelease_groups().stream()
                       .filter(a -> a.getPrimaryType().equals("Album"))
                       .forEach(a -> {
                           String imageUrl = imageService.extractImageUrl(a);
                           if (imageUrl != null) {
                               albumAndImageList.add(new AlbumAndImage(a.getTitle(), a.getId(), imageUrl));
                           }
                       });
           }
       }
       catch (Exception e){
           log.info("no albums for url {}", albumUrl);
       }

       return albumAndImageList;
    }
}
