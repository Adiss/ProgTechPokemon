package hu.experiment_team;

import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jakab on 2016.05.17..
 */
public class TrainerTest {

    @Test
    public void testTrainerCreation(){
        Trainer t = new Trainer();
        List<Pokemon> pokes = new ArrayList<Pokemon>(){{
            add(MyObjectFactory.INSTANCE.getPokemonBulbasaur());
            add(MyObjectFactory.INSTANCE.getPokemonCharmander());
        }};


        t.setId(999);
        t.setUsername("adiss.b17");
        t.setDisplayName("Adiss");
        t.setPassword("824e456e4cfd675348965387a08924fffaf2ae06");
        t.setEmail("adiss.b17@gmail.com");
        t.setRegister_date(Timestamp.valueOf("2016-05-17 18:39:00"));
        t.setOwnedPokemons(pokes);
        t.setMatchLoose(0);
        t.setMatchWin(0);
        t.setPartyPokemons(pokes);
        t.setOnline(1);


        assertEquals(t.getId(), 999);
        assertEquals(t.getUsername(), "adiss.b17");
        assertEquals(t.getDisplayName(), "Adiss");
        assertEquals(t.getPassword(), "824e456e4cfd675348965387a08924fffaf2ae06");
        assertEquals(t.getEmail(), "adiss.b17@gmail.com");
        assertEquals(t.getRegister_date(), Timestamp.valueOf("2016.05.17"));
        assertTrue(t.getOwnedPokemons().size() == 2);
        assertEquals(t.getMatchLoose(), 0);
        assertEquals(t.getMatchWin(), 0);
        assertTrue(t.getPartyPokemons().size() == 2);
        assertEquals(t.getOnline(), 1);

    }

}
