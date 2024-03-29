package org.example.mashupwikisongs.controller;

import org.example.mashupwikisongs.service.AlbumService;
import org.example.mashupwikisongs.service.WikiDescriptionExtractionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Date: 24.02.2024
 *
 * @author Nikolay Zinin
 */
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(MashupController.class)
public class MashupControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    AlbumService albumService;
    @MockBean
    WikiDescriptionExtractionService wikiDescriptionExtractionService;
    @Test
    public void testGetInfo() throws Exception {
        String mbid = "5b11f4ce-a62d-471e-81fc-a69a8278c7da";

        mockMvc.perform(MockMvcRequestBuilders.get("/mashup/{mbid}", mbid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(albumService).getAlbums(mbid);
        verify(wikiDescriptionExtractionService).getWikiExtract(mbid);
    }
}
