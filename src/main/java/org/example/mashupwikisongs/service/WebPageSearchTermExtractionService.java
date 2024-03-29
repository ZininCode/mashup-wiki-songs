package org.example.mashupwikisongs.service;

import lombok.extern.slf4j.Slf4j;
import org.example.mashupwikisongs.config.Status;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
/**
 * Date: 21.02.2024
 *
 * @author Nikolay Zinin
 */
@Service
@Slf4j
public class WebPageSearchTermExtractionService {
    public String extractWikiEntryFromUrl(String searchTerm, String url, String mbid) {
        String wikiEntry = Status.NOT_FOUND;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream(), StandardCharsets.UTF_8))) {
            wikiEntry = searchWikiEntry(reader, searchTerm);
        } catch (IOException e) {
            log.info("A link of the artist with {} to Wikipedia was not found in MusicBrainz artist's page", mbid);
        }

        return wikiEntry;
    }

    private String searchWikiEntry(BufferedReader reader, String searchTerm) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(searchTerm)) {
                int startIndex = line.indexOf(searchTerm) + searchTerm.length();
                int endIndex = line.indexOf("\"", startIndex);
                String wikiEntry = line.substring(startIndex, endIndex);
                return URLDecoder.decode(wikiEntry, StandardCharsets.UTF_8);
            }
        }
        return Status.NOT_FOUND;
    }
}
