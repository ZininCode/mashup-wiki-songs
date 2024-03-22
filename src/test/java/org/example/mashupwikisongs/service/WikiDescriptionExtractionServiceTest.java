package org.example.mashupwikisongs.service;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WikiDescriptionExtractionServiceTest {

    @Mock
    private WikiEntryService wikiEntryService;

    @Mock
    private RestTemplate restTemplate;

    private WikiDescriptionExtractionService wikiDescriptionExtractionService;

    @BeforeEach
   void setUp() {
        wikiDescriptionExtractionService  = new WikiDescriptionExtractionService(wikiEntryService, restTemplate);
    }

    @Test
    public void testGetWikiExtract_WhenEntryFound() {
        String mbid = "test_mbid";
        String wikiEntry = "Test_Entry";
        String expectedWikiExtract = "Test extract";

        when(wikiEntryService.getEntry(mbid)).thenReturn(wikiEntry);

        String apiUrlTemplate = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles=" + wikiEntry;
        String jsonResponse = "{\"query\":{\"pages\":{\"1\":{\"extract\":\"Test extract\"}}}}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(apiUrlTemplate, String.class)).thenReturn(responseEntity);

        String actualWikiExtract = wikiDescriptionExtractionService.getWikiExtract(mbid);

        assertEquals(expectedWikiExtract, actualWikiExtract);
        verify(wikiEntryService, times(1)).getEntry(mbid);
        verify(restTemplate, times(1)).getForEntity(apiUrlTemplate, String.class);
    }

    @Test
    public void testGetWikiExtract_WhenEntryNotFound() {
        String mbid = "test_mbid";

        when(wikiEntryService.getEntry(mbid)).thenReturn("not found");

        String actualWikiExtract = wikiDescriptionExtractionService.getWikiExtract(mbid);

        assertEquals("not found", actualWikiExtract);
        verify(wikiEntryService, times(1)).getEntry(mbid);
        verify(restTemplate, never()).getForEntity(anyString(), any());
    }
}

