package org.example.mashupwikisongs.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Date: 18.02.2024
 *
 * @author Nikolay Zinin
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MashupResponse {
    private String mbid;
    private String description;
    private List<AlbumAndImage> albums = new ArrayList<>();
}
