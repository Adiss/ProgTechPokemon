package hu.experiment_team;

import hu.experiment_team.models.Move;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jakab on 2016.05.14..
 */
public class MoveTest {

    @Test
    public void testMoveCreation(){
        Move m = new Move();
        m.setId(303);
        m.setDisplayName("Tackle");
        m.setFunctionCode("000");
        m.setBaseDamage(50);
        m.setType("NORMAL");
        m.setMoveCategory("Physical");
        m.setAccuracy(100);
        m.setTotalPP(35);
        m.setAdditionalEffectChance(0);
        m.setFlags("abef");
        m.setPriority(0);

        assertEquals(m.getId(), 303);
        assertEquals(m.getDisplayName(), "Tackle");
        assertEquals(m.getFunctionCode(), "000");
        assertEquals(m.getBaseDamage(), 50);
        assertEquals(m.getType(), "NORMAL");
        assertEquals(m.getMoveCategory(), "Physical");
        assertEquals(m.getAccuracy(), 100);
        assertEquals(m.getTotalPP(), 35);
        assertEquals(m.getAdditionalEffectChance(), 0);
        assertEquals(m.getFlags(), "abef");
        assertEquals(m.getPriority(), 0);

        Move m2 = new Move();
        m2.setId(303);
        m2.setDisplayName("Tackle");
        m2.setFunctionCode("000");
        m2.setBaseDamage(50);
        m2.setType("NORMAL");
        m2.setMoveCategory("Physical");
        m2.setAccuracy(100);
        m2.setTotalPP(35);
        m2.setAdditionalEffectChance(0);
        m2.setFlags("abef");
        m2.setPriority(0);

        assertTrue(m.equals(m2));

    }

}
