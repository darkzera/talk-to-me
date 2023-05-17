package com.darkzera.fetcher.entity.dto;

import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import javax.persistence.ElementCollection;
import java.util.Collection;
import java.util.List;

@Data
public class MusicArtist {
    private String id;
    private String name;
    @ElementCollection
    private Collection<String> genres;
    private String externalLink;

}
