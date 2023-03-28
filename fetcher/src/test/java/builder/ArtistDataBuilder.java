package builder;

import com.darkzera.fetcher.entity.dto.ArtistData;
import org.mockito.internal.util.collections.Sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class ArtistDataBuilder {

    private String name;
    private String externalLink;
    private String spotifyId;

    private List<String> genres;

    private List<String> names;

    private ArtistDataBuilder(List<String> names){
        this.names = names;
    }

    private ArtistDataBuilder(String name){
        this.name = name;
    }

    @Deprecated
    private ArtistDataBuilder(String... name){
        if (name.length == 1) {
            this.name = name[0];
        }
        else {
            this.names = List.of(name);
        }
    }
    public static ArtistDataBuilder fromNames(List<String> namesGiven){
        return new ArtistDataBuilder(namesGiven);
    }

    @Deprecated
    public static ArtistDataBuilder fromName_(String... name){
        return new ArtistDataBuilder(name);
    }
    public static ArtistDataBuilder fromName(String name){
        return new ArtistDataBuilder(name);
    }

    public ArtistDataBuilder withGenresInOrder(List<String> genresGiven){
        assertNotNull("Names list cannot be empty. Check implementation class", names);
        this.genres = genresGiven;
        return this;
    }


    /* So utilizar em casos mais simples
        Talvez em casos mais complexos pode ser que nao seja possivel atender a logica adequada
        Considere 1 artista para cada genero no momento de criar o cenario
        Do contrario nao sera possivel i guess
     */
    public List<ArtistData> buildRestrictedList(){
        assertNotNull("Fields cannot be empty. Check implementation class", genres);
        assertNotNull("Fields cannot be empty. Check implementation class", names);
        assertEquals("Collections musthave same size", genres.size(), names.size());

        final List<ArtistData> artistDataList = new ArrayList<>();


        for (int i = 0; i < names.size() ; i++) {
            artistDataList.add(ArtistData.builder()
                    .name(names.get(i))
                    .genres(Sets.newSet(names.get(i)))
                    .build());
        }

        return artistDataList;
    }

}
