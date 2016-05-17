package hu.experiment_team;

import hu.experiment_team.battleAI.BattleAI;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */

public class BattleAITest {

    @Test
    public void testBattleAICalculateNextMove(){
        assertEquals(
                BattleAI.INSTANCE.calculateNextMove(
                        MyObjectFactory.INSTANCE.getPokemonBulbasaur(),
                        MyObjectFactory.INSTANCE.getPokemonCharmander()),
                MyObjectFactory.INSTANCE.getMoveTackle()
        );
    }

    @Test
    public void testBattleAIDamageCalculator(){
        int eredmeny = BattleAI.INSTANCE.damageCalculator(
                MyObjectFactory.INSTANCE.getPokemonBulbasaur(),
                MyObjectFactory.INSTANCE.getPokemonCharmander(),
                MyObjectFactory.INSTANCE.getMoveTackle());
        assertTrue(eredmeny < 0);
    }

}
