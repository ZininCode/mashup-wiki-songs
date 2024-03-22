package org.example.mashupwikisongs.service;

import org.example.mashupwikisongs.model.Artist;
import org.example.mashupwikisongs.model.Relations;
import org.example.mashupwikisongs.model.RelationsURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WikiEntryServiceTest {
    private WikiEntryService wikiEntryService;
    @Mock
    private RestTemplate restTemplate;

    @Mock
    WebPageEntryExtractionService webPageEntryExtractionService;
    @BeforeEach
    void setUp() {
        wikiEntryService = new WikiEntryService(restTemplate, webPageEntryExtractionService);
    }

    @Test
    void testGetEntryWithWikiRelations() {
        String mbid = "some-mbid";
        String wikiUrl = "https://en.wikipedia.org/wiki/artist_name";
        String wikiEntry = "artist_name";
        RelationsURL relationsURL = RelationsURL.builder()
                .id("relationUrlId1")
                .resource(wikiUrl)
                .build();
        Relations relation = Relations.builder()
                .type("wikipedia")
                .url(relationsURL)
                .build();
        List<Relations> relations = new ArrayList<>();

        relations.add(relation);
        Artist artist = Artist.builder()
                .relations(relations)
                .build();

        when(restTemplate.getForObject(any(String.class), eq(Artist.class))).thenReturn(artist);
        String result = wikiEntryService.getEntry(mbid);
        assertEquals(wikiEntry, result);
        verify(restTemplate).getForObject(anyString(), eq(Artist.class));
    }

   @Test
    void testGetEntryFromWebExtraction() {
       String mbid = "some-mbid";
       String wikiEntry = "artist_name";
       String wikiUrl = "https://en.wikipedia.org/wiki/artist_name";
       RelationsURL relationsURL = RelationsURL.builder()
               .id("relationUrlId1")
               .resource(wikiUrl)
               .build();
       Relations relation = Relations.builder()
               .type("someType")
               .url(relationsURL)
               .build();
       List<Relations> relations = new ArrayList<>();
       relations.add(relation);
       Artist artist = Artist.builder()
               .relations(relations)
               .build();

        when(restTemplate.getForObject(any(String.class), eq(Artist.class))).thenReturn(artist);
        when(webPageEntryExtractionService.extractWikiEntryFromUrl(anyString(), anyString(), anyString())).thenReturn(wikiEntry);
        String result = wikiEntryService.getEntry(mbid);
        assertEquals(wikiEntry, result);
       verify(restTemplate).getForObject(anyString(), eq(Artist.class));
       /*this line will be only executed if wikiEntry was not found via relations to Wiki
       and proceeded to search for it in webPage: */
       verify(webPageEntryExtractionService).extractWikiEntryFromUrl(anyString(), anyString(), anyString());
    }
}
