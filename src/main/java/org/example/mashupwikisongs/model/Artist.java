package org.example.mashupwikisongs.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class Artist {
    private List<Relations> relations;
}
