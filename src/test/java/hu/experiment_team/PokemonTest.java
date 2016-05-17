package hu.experiment_team;

import hu.experiment_team.models.Pokemon;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jakab on 2016.05.14..
 */
public class PokemonTest {

    @Test
    public void testPokemonCreation(){
        Pokemon p = MyObjectFactory.INSTANCE.getPokemonBulbasaur();

        assertEquals(p.getId(), 1);
        assertEquals(p.getDisplayName(), "Bulbasaur");
        assertEquals(p.getType1(), "GRASS");
        assertEquals(p.getType2(), "POISON");
        assertEquals(p.getHp(), 45);
        assertEquals(p.getAttack(), 49);
        assertEquals(p.getDefense(), 49);
        assertEquals(p.getSpeed(), 45);
        assertEquals(p.getSpecialAttack(), 65);
        assertEquals(p.getSpecialDefense(), 65);
        assertEquals(p.getHiddenAbility(), "CHLOROPHYLL");
        assertEquals(p.getMove1(), MyObjectFactory.INSTANCE.getMoveTackle());
        assertEquals(p.getMove2(), null);
        assertEquals(p.getMove3(), null);
        assertEquals(p.getMove4(), null);
        assertEquals(p.getLastMove(), null);
        assertEquals(p.getSleep(), 0);
        assertEquals(p.getDrowsy(), 0);
        assertEquals(p.getPoison(), 0);
        assertEquals(p.getBadlyPoison(), 0);
        assertEquals(p.getParalyze(), 0);
        assertEquals(p.getBurn(), 0);
        assertEquals(p.getFlinch(), 0);
        assertEquals(p.getFreeze(), 0);
        assertEquals(p.getMinimized(), 0);
        assertEquals(p.getConfuse(), 0);
        assertEquals(p.getAttract(), 0);
        assertEquals(p.getEvasion(), 0);

        Pokemon charmander = MyObjectFactory.INSTANCE.getPokemonCharmander();
        charmander.hurt(p, MyObjectFactory.INSTANCE.getMoveTackle());
        assertTrue(charmander.getHp() < 0);
        assertTrue(p.getClonedOne() != null);

        charmander = charmander.getClonedOne();
        assertEquals(charmander.getHp(), 39);

        assertTrue(charmander.equals(MyObjectFactory.INSTANCE.getPokemonCharmander()));

    }

}
