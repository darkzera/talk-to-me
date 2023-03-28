package com.darkzera.fetcher.entity;

import com.darkzera.fetcher.entity.enumerator.AuthSupplier;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ElementCollection
    @CollectionTable(name = "top10Genres", joinColumns = @JoinColumn(name = "users"))
    @Column(name = "valor")
    private Set<String>top10Genres;


    @ElementCollection
    @CollectionTable(name = "musicalArtists", joinColumns = @JoinColumn(name = "users"))
    @Column(name = "valor")
    private Set<String> musicalArtists;
}
