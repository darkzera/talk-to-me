package com.darkzera.fetcher.entity;

import com.darkzera.fetcher.entity.enumerator.AuthSupplier;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
//@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private AuthSupplier authSupplier;
    @Column(name = "topGenres")
    @ElementCollection
    private Set<String>top10Genres = new HashSet<>();

    @Column(name = "musicalArtists")
    @ElementCollection
    private Set<String> musicalArtists = new HashSet<>();

    @Column(name = "availableIn")
    @ElementCollection
    private Set<LocalDateTime> availableIn = new HashSet<>();

    public boolean addNewMusicalArtist(final @NotNull String artistName){
        return musicalArtists.add(artistName);
    }

    public boolean addNewSetOfGenres(final @NotNull Set<String> genres){
       return this.top10Genres.addAll(genres);
    }

    public boolean includeAvailableTimes(final @NotNull Set<LocalDateTime> t){
        return availableIn.addAll(t);
    }
}
