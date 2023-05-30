package com.darkzera.fetcher.service.client;

import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReorganizeArtistData {

    public SearchArtistByNameDTO rearrange(@NotNull final List<ArtistData> artistDataList,
                                           @NotNull final String artistName) {

        final var validMatches = extractOnlyExactlyMatches(artistDataList, artistName);

        if (validMatches.isEmpty()) {
            throw new RuntimeException();
        }

        final SearchArtistByNameDTO builded = new SearchArtistByNameDTO();

        for (ArtistData artist : artistDataList) {
            builded.getGenres().addAll(artist.getGenres());

            if (artist.getName().equalsIgnoreCase(artistName)) {
                builded.setFoundArtist(artist);
            }
        }

        validMatches.remove(builded.getFoundArtist());

        builded.setSuggestions(validMatches);
        builded.setQueryName(builded.getFoundArtist().getName());

        return builded;
    }

    private List<ArtistData> extractOnlyExactlyMatches(@NotNull final List<ArtistData> artistListSource,
                                                       @NotNull final String artistName) {

        return artistListSource.stream()
                .filter(artist -> artist.getName().contains(artistName))
                .collect(Collectors.toList());
    }

}