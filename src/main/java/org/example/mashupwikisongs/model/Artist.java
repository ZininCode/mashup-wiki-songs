package org.example.mashupwikisongs.model;


import lombok.Builder;
import lombok.Getter;


import java.util.List;
/**
 * Date: 18.02.2024
 *
 * @author Nikolay Zinin
 */
@Getter
@Builder
public class Artist {
    private List<Relations> relations;
}
