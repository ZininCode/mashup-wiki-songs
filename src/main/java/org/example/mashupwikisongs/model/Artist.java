package org.example.mashupwikisongs.model;


import lombok.Builder;
import lombok.Getter;


import java.util.List;



@Getter
@Builder
public class Artist {
    private List<Relations> relations;
}
