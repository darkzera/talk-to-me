package com.darkzera.fetcher.entity;

import com.darkzera.fetcher.entity.enumerator.AuthSupplier;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
}
