package org.example.mashupwikisongs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;


@Getter
public class Relations {
    private  String type;
    private RelationsURL url;
}
