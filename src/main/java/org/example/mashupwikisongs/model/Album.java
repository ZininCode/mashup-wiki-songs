package org.example.mashupwikisongs.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {
    private String title;
    private String id;
    @JsonProperty("primary-type")
    private String primaryType;
}
