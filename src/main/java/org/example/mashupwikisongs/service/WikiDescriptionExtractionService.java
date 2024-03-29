package org.example.mashupwikisongs.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.mashupwikisongs.config.Status;
import org.example.mashupwikisongs.config.Url;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
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
public class WikiDescriptionExtractionService {
    private final WikiEntryService wikiEntryService;
    private final RestTemplate restTemplate;
    public String getWikiExtract(String mbid) {
        String wikiExtract = Status.NOT_FOUND;
        String wikiEntry = wikiEntryService.getEntry(mbid);

        if (!wikiEntry.equals(Status.NOT_FOUND)) {
            String apiUrlTemplate = Url.WIKI_ARTICLE_URL_PREFIX + wikiEntry;

            try {
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrlTemplate, String.class);
                String jsonResponse = responseEntity.getBody();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject query = jsonObject.getJSONObject("query");
                JSONObject pages = query.getJSONObject("pages");
                JSONObject pageData = pages.getJSONObject(pages.keys().next());
                wikiExtract = pageData.getString("extract");
            } catch (Exception e) {
                log.info("could not extract the description from the wikipedia API");
            }
        }
        return wikiExtract;
    }
}
