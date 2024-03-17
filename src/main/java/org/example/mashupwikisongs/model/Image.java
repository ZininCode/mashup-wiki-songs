package org.example.mashupwikisongs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private String image;
}
