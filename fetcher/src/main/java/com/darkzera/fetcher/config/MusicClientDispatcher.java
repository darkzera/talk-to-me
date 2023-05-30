package com.darkzera.fetcher.config;

import com.darkzera.fetcher.entity.enumerator.ClientProvider;
import com.darkzera.fetcher.service.client.LastFMClient;
import com.darkzera.fetcher.service.client.SpotifyClient;
import com.darkzera.fetcher.service.client.provider.MusicClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MusicClientDispatcher {

    private List<MusicClient> elegibleClients;

    public MusicClientDispatcher(LastFMClient lastFMClient,
                                 SpotifyClient spotifyClient) {
        this.elegibleClients = Arrays.asList(lastFMClient, spotifyClient);
    }


    public List<MusicClient> doFilter(List<ClientProvider> clients){
        List<String> name = clients.stream().map(ClientProvider::name)
                .collect(Collectors.toList());

        return elegibleClients.stream()
                .filter(clientClasz -> name.contains(clientClasz.getClass().getSimpleName().toUpperCase()))
                .collect(Collectors.toList());

    }

}
