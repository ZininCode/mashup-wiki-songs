package org.example.mashupwikisongs.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import java.util.List;

/**
 * Date: 18.02.2024
 *
 * @author Nikolay Zinin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Albums {
    @JsonProperty("release-groups")
    private List<Album> release_groups;
}
