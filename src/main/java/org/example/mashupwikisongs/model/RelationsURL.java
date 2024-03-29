package org.example.mashupwikisongs.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Date: 18.02.2024
 *
 * @author Nikolay Zinin
 */
@Getter
@Setter
@Builder
public class RelationsURL {
    private String resource;
    private String id;
}
