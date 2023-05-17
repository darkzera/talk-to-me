package com.darkzera.fetcher.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class SearchArtistByNameDTO {


    private String queryName;

    private ArtistData foundArtist;

    private List<ArtistData> suggestions;
    private Set<String> genres = new HashSet<>();
    public SearchArtistByNameDTO(){}

    public String getQueryName(){
        return foundArtist.getName();
    }
}
