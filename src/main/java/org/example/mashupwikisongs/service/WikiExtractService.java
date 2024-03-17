package org.example.mashupwikisongs.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
@AllArgsConstructor
@Slf4j
public class WikiExtractService {
    private final WikiEntryService wikiEntryService;
    public String getWikiExtract(String mbid) {
        RestTemplate restTemplate = new RestTemplate();
        String wikiExtract = "not found";
        String wikiEntry = wikiEntryService.getEntry(mbid);

        if (!wikiEntry.equals("not found")) {
            String apiUrlTemplate = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles=" + wikiEntry;

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
