package org.example.mashupwikisongs.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
