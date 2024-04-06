package org.example.mashupwikisongs.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mashupwikisongs.model.Artist;

import org.example.mashupwikisongs.config.Url;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Date: 21.02.2024
 *
 * @author Nikolay Zinin
 */
@Service
@Slf4j
@AllArgsConstructor
public class WikiEntryService {
    private final RestTemplate restTemplate;
    private final WebPageSearchTermExtractionService webPageSearchTermExtractionService;

    public String getEntry(String mbid) {
        try {
            String wikiUrl = findWikiUrlInMusicBrainzRelations(mbid);
            if (wikiUrl != null) {
                return wikiUrl;
            }
        } catch (Exception e) {
            log.info("the artist with {} for Wikipedia was not found in relations in MusicBrainz", mbid);
        }

        return findWikiEntryViaMusicBrainzArtistPage(mbid);
    }

    private String findWikiUrlInMusicBrainzRelations(String musicBrainzId) {
        String musicBrainzApiRelations = Url.MUSICBRAINZ_RELATIONS_URL_PREFIX + musicBrainzId + Url.MUSICBRAINZ_RELATIONS_URL_SUFFIX;
        try {
            Artist artist = restTemplate.getForObject(musicBrainzApiRelations, Artist.class);
            return artist.getRelations().stream()
                        .filter(rr -> Url.WIKIPEDIA_RELATION_TYPE.equals(rr.getType()))
                        .map(rr -> rr.getUrl().getResource())
                        .map(urlWiki -> urlWiki.split("/"))
                        .map(result -> result[result.length - 1])
                        .findFirst()
                        .orElse(null);
        }
        catch (Exception e){
            return null;
        }
    }

    private String findWikiEntryViaMusicBrainzArtistPage(String musicBrainzId) {
        String url = Url.MUSICBRAINZ_PAGE_PREFIX + musicBrainzId;
        String searchTerm = Url.WIKIPEDIA_SEARCH_TERM;
        return webPageSearchTermExtractionService.extractWikiEntryFromUrl(searchTerm, url, musicBrainzId);
    }
}

