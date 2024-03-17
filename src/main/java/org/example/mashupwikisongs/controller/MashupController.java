package org.example.mashupwikisongs.controller;

import lombok.AllArgsConstructor;
import org.example.mashupwikisongs.model.*;
import org.example.mashupwikisongs.service.AlbumService;
import org.example.mashupwikisongs.service.WikiExtractService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
public class MashupController {

    private final AlbumService albumService;
    private final WikiExtractService wikiExtractService;

    @GetMapping("/mashup/{mbid}")//http://localhost:8080/mashup/5b11f4ce-a62d-471e-81fc-a69a8278c7da
    public MashupResponse getInfo(@PathVariable("mbid") String mbid) {
        //get list of albums
        List<org.example.mashupwikisongs.model.AlbumAndImage> albumsAndImages = albumService.getAlbums(mbid);

        //get info from Wikipedia
        String wikiExtract = wikiExtractService.getWikiExtract(mbid);
        return MashupResponse.builder()
                .mbid(mbid)
                .albums(albumsAndImages)
                .description(wikiExtract)
                .build();
    }
}
