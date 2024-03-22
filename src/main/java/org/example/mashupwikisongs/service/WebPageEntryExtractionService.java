package org.example.mashupwikisongs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
@Slf4j
public class WebPageEntryExtractionService {
     String extractWikiEntryFromUrl(String searchTerm, String url, String mbid) {
        String wikiEntry = "not found";
        //todo "not found" into enum
        try {
            Scanner scanner = new Scanner((new URL(url)).openStream());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(searchTerm)) {
                    int startIndex = line.indexOf(searchTerm) + searchTerm.length();
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
