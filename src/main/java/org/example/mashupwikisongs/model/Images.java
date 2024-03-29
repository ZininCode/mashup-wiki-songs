package org.example.mashupwikisongs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Date: 18.02.2024
 *
 * @author Nikolay Zinin
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Images {
    private List<Image> images;
}
