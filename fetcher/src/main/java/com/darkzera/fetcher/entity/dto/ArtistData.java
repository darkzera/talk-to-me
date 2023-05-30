package com.darkzera.fetcher.entity.dto;

import lombok.*;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
public class ArtistData {

    private String name;
    private String externalLink;
    private String spotifyId;
    private Set<String> genres = new HashSet<>();


    public ArtistData(de.umass.lastfm.Artist lastFMArtist){
        this.name = lastFMArtist.getName();
        this.genres.addAll(lastFMArtist.getTags());
        this.externalLink = lastFMArtist.getUrl();
    }

    public ArtistData(se.michaelthelin.spotify.model_objects.specification.Artist spotifyArtist){
        this.name = spotifyArtist.getName();
        Collections.addAll(this.genres, spotifyArtist.getGenres());
        this.externalLink = spotifyArtist.getHref();
    }


    private ArtistData(String name, String externalLink, String spotifyId, Collection<String> genres) {
        this.name = name;
        this.externalLink = externalLink;
        this.spotifyId = spotifyId;
        this.genres = genres.stream().collect(Collectors.toSet());
    }

    public static ArtistData fromSpotify(se.michaelthelin.spotify.model_objects.specification.Artist spotifyArtist){
        return new ArtistData(
                spotifyArtist.getName(),
                spotifyArtist.getHref(),
                spotifyArtist.getId(),
                Arrays.stream(spotifyArtist.getGenres()).collect(Collectors.toSet())
        );
    }

    public static ArtistData fromLastfm(de.umass.lastfm.Artist lastFMArtist){
        return new ArtistData(
                lastFMArtist.getName(),
                lastFMArtist.getUrl(),
                lastFMArtist.getId(),
                lastFMArtist.getTags()
        );
    }
}
