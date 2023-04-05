package builder;

import com.darkzera.fetcher.entity.dto.ArtistData;
import org.mockito.internal.util.collections.Sets;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class ArtistDataBuilder {

    private String name;
    private String externalLink;
    private String spotifyId;

    private List<String> genres = new ArrayList<>();

    private List<String> names = new ArrayList<>();

    private final String ASSERT_DEFAULT_MSG = "Given list cannot be empty. Check implementation class";

    private ArtistDataBuilder(String... name){
        if (name.length == 1) {
            this.names.add(name[0]);
        }
        else {
            this.names = List.of(name);
        }
    }


    public static ArtistDataBuilder fromNames(String... name){
        return new ArtistDataBuilder(name);
    }

    public ArtistDataBuilder withGenresInOrder(String... genres){
        assertNotNull(ASSERT_DEFAULT_MSG, names);
        if (genres.length == 1){
            this.genres.add(genres[0]);
        } else {
            this.genres.addAll(List.of(genres));
        }
        return this;
    }

    /* So utilizar em casos mais simples
        Talvez em casos mais complexos pode ser que nao seja possivel atender a logica adequada
        Considere 1 artista para cada genero no momento de criar o cenario
        Do contrario nao sera possivel i guess
     */
    public List<ArtistData> buildRestrictedList(){
        assertNotNull(ASSERT_DEFAULT_MSG, genres);
        assertNotNull(ASSERT_DEFAULT_MSG, names);
        assertEquals(ASSERT_DEFAULT_MSG, genres.size(), names.size());

        final List<ArtistData> artistDataList = new ArrayList<>();


        for (int i = 0; i < names.size() ; i++) {
            artistDataList.add(ArtistData.builder()
                    .name(names.get(i))
                    .genres(Sets.newSet(names.get(i)))
                    .build());
        }

        return artistDataList;
    }

    public Paging<Artist> buildRestrictedPaging(){
        assertNotNull(ASSERT_DEFAULT_MSG, genres);
        assertNotNull(ASSERT_DEFAULT_MSG, names);
        assertEquals(ASSERT_DEFAULT_MSG, genres.size(), names.size());
        Artist[] artistArr = new Artist[names.size()];


        for (int i = 0; i < names.size() ; i++) {
            artistArr[i] = new Artist.Builder()
                    .setHref("http://fake." + names.get(i))
                    .setName(names.get(i))
                    .setGenres(genres.get(i))
                    .setPopularity(7)
                    .build();
        }

        return new Paging.Builder<Artist>()
                .setHref("http://fake.pageable.net")
                .setItems(artistArr)
                .build();
    }

}
