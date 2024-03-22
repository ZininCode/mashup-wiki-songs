package org.example.mashupwikisongs.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class RelationsURL {
    private String resource;
    private String id;
}
