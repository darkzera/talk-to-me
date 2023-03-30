package com.darkzera.fetcher.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class ArtistData {

    private String name;
    private String externalLink;
    private String spotifyId;

    private Set<String> genres;

}
