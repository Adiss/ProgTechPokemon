package hu.experiment_team;

import hu.experiment_team.models.BasicPokemon;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jakab on 2016.05.14..
 */
public class BasicPokemonTest {

    @Test
    public void testBasicPokemonCreation(){
        BasicPokemon bp = new BasicPokemon();
        bp.setId(1);
        bp.setDisplayName("Bulbasaur");
        bp.setType1("GRASS");
        bp.setType2("POISON");
        bp.setHp(45);
        bp.setAttack(49);
        bp.setDefense(49);
        bp.setSpeed(45);
        bp.setSpecialAttack(65);
        bp.setSpecialDefense(65);
        bp.setHiddenAbility("CHLOROPHYLL");

        assertEquals(bp.getId(), 1);
        assertEquals(bp.getDisplayName(), "Bulbasaur");
        assertEquals(bp.getType1(), "GRASS");
        assertEquals(bp.getType2(), "POISON");
        assertEquals(bp.getHp(), 45);
        assertEquals(bp.getAttack(), 49);
        assertEquals(bp.getDefense(), 49);
        assertEquals(bp.getSpeed(), 45);
        assertEquals(bp.getSpecialAttack(), 65);
        assertEquals(bp.getSpecialDefense(), 65);
        assertEquals(bp.getHiddenAbility(), "CHLOROPHYLL");

    }

}
