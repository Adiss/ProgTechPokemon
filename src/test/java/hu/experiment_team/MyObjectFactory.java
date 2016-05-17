package hu.experiment_team;

import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

/**
 * Created by Jakab on 2016.05.14..
 */
public enum MyObjectFactory {

    INSTANCE;

    public Pokemon getPokemonBulbasaur(){
        Pokemon p = new Pokemon();

        p.setId(1);
        p.setDisplayName("Bulbasaur");
        p.setType1("GRASS");
        p.setType2("POISON");
        p.setHp(45);
        p.setAttack(49);
        p.setDefense(49);
        p.setSpeed(45);
        p.setSpecialAttack(65);
        p.setSpecialDefense(65);
        p.setHiddenAbility("CHLOROPHYLL");
        p.setCurrentXp(0);
        p.setLevel(1);
        p.setMove1(getMoveTackle());
        p.setLastMove(null);
        p.setClonedOne();

        return p;
    }

    public Pokemon getPokemonCharmander(){
        Pokemon p = new Pokemon();

        p.setId(4);
        p.setDisplayName("Charmander");
        p.setType1("FIRE");
        p.setType2(null);
        p.setHp(39);
        p.setAttack(52);
        p.setDefense(43);
        p.setSpeed(65);
        p.setSpecialAttack(60);
        p.setSpecialDefense(50);
        p.setHiddenAbility("SOLARPOWER");
        p.setCurrentXp(0);
        p.setLevel(1);
        p.setMove1(getMoveTackle());
        p.setLastMove(null);
        p.setClonedOne();

        return p;
    }

    public Move getMoveTackle(){
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

        return m;
    }

}
