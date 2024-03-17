package org.example.mashupwikisongs.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mashupwikisongs.model.Artist;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import java.net.URL;
import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

@Service
@Slf4j
@AllArgsConstructor
public class WikiEntryService {
    private final RestTemplate restTemplate;

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
                wikiEntry = wikiUrl.get();
                return wikiEntry;
            } else {
                return wikiEntry;
            }
        } catch (Exception e) {
            log.info("the relation of the artist with {} for Wikipedia was not found in MusicBrainz", mbid);
        }
        //if not found in relation try to find wikiEntry via link to wiki in MusicBrainz Artist's page
        String musicBrainzUrl = "https://musicbrainz.org/artist/" + mbid;
        String wikiLinkInMusicBrainz = "<a href=\"https://en.wikipedia.org/wiki/";
        try {
            URL url = new URL(musicBrainzUrl);
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(wikiLinkInMusicBrainz)) {
                    int startIndex = line.indexOf(wikiLinkInMusicBrainz) + wikiLinkInMusicBrainz.length();
                    int endIndex = line.indexOf("\"", startIndex);
                    wikiEntry = line.substring(startIndex, endIndex);
                    wikiEntry = URLDecoder.decode(wikiEntry, StandardCharsets.UTF_8);
                    break;
                }
            }
            scanner.close();
        } catch (IOException e) {
            log.info("A link of the artist with {} to Wikipedia was not found in MusicBrainz artist's page", mbid);
        }
        return wikiEntry;
    }
}
