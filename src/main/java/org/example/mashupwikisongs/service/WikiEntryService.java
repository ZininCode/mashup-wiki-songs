package org.example.mashupwikisongs.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mashupwikisongs.model.Artist;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class WikiEntryService {
    private final RestTemplate restTemplate;
    private final WebPageEntryExtractionService webPageEntryExtractionService;

    public String getEntry(String mbid) {

        //try to find wikiEntry via relations to Wikipedia in MusicBrainz API
        String wikiEntry = "not found";
        String musicBrainzApiRelations = "http://musicbrainz.org/ws/2/artist/" + mbid + "?&fmt=json&inc=url-rels";
        try {
            Artist artist = restTemplate.getForObject(musicBrainzApiRelations, Artist.class);
            Optional<String> wikiUrl = Optional.empty();
            if (artist != null) {
                wikiUrl = artist.getRelations().stream()
                        .filter(rr -> "wikipedia".equals(rr.getType()))
                        .map(rr -> rr.getUrl().getResource())
                        .map(urlWiki -> urlWiki.split("/"))
                        .map(result -> result[result.length - 1])
                        .findFirst();
            }

            if (wikiUrl.isPresent()) {
                wikiEntry = wikiUrl.orElse(wikiEntry);
                return wikiEntry;
            }
        } catch (Exception e) {
            log.error("the artist with {} for Wikipedia was not found in MusicBrainz", mbid);
        }

        //if not found in relation try to find wikiEntry via link to wiki in MusicBrainz Artist's page
        String url = "https://musicbrainz.org/artist/" + mbid;
        String searchTerm = "<a href=\"https://en.wikipedia.org/wiki/";
        wikiEntry = webPageEntryExtractionService.extractWikiEntryFromUrl(searchTerm, url, mbid);

        return wikiEntry;
    }


}
