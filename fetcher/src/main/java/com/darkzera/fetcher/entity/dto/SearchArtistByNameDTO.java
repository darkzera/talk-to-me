package com.darkzera.fetcher.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class SearchArtistByNameDTO {


    private String queryName;

    private ArtistData foundArtist;

    private List<ArtistData> suggestions;
    private Set<String> genres;

}
