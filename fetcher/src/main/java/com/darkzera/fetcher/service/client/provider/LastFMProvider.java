package com.darkzera.fetcher.service.client.provider;

import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.service.exception.ClientException;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class LastFMProvider {

    private final String key = "b25b959554ed76058ac220b7b2e0a026"; //this is the key used in the Last.fm API examples
    public Collection<Artist> searchByName(String name) {

        try {
            return Artist.search("Metallica", key);
        } catch (NullPointerException e) {
            throw new ClientException();
        } catch (Exception e) {
            throw new ClientException();
        }

    }

    public PaginatedResult<Event> fetchEventsForArtist(ArtistData a){
        return Artist.getEvents(a.getSpotifyId(), key);
    }
}
