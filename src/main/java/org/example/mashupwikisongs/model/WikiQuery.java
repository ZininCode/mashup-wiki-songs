package org.example.mashupwikisongs.model;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class WikiQuery {
    public WikiQuery() {
    }
    private Map<String, Page> pages= new HashMap<>();
}
