package hu.experiment_team;

import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

import java.util.Random;

/**
 * Created by Jakab on 2016.03.18..
 */
public enum Move_Functions {
    INSTANCE;

    private Random r = new Random();


    /**
     * Function code: 000.
     * Nincs a spellnek semmilyen effektje, csak sebez.
     * */
    public void Move_Function_000(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
    }

    /**
     * Function code: 001.
     * Nem csinál semmit, abszolút semmit (Splash).
     * */
    public void Move_Function_001(Pokemon d, Pokemon a, int damage){
        /* Magikarp-karp-karp... */
    }

    /**
     * Function code: 002.
     * Stagger.
     * An attack that is used in desperation only if the user has no PP. This also damages the user a little.
     * */
    public void Move_Function_002(Pokemon d, Pokemon a, int damage){
        if(a.getMove1().getActualPP() == 0 && a.getMove2().getActualPP() == 0 && a.getMove3().getActualPP() == 0 && a.getMove4().getActualPP() == 0){
            d.setHp(d.getHp()- damage);
            a.setHp(a.getHp() - ((int)Math.round(a.getClonedOne().getHp() * 0.15)));
        }
    }

    /**
     * Function code: 003.
     * Puts the target to sleep. (1-3 rounds)
     * */
    public void Move_Function_003(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setSleep((r.nextInt(3) + 1));
    }

    /**
     * Function code: 004.
     * Makes the target drowsy.  It will fall asleep at the end of the next turn.
     * */
    public void Move_Function_004(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setDrowsy(1);
    }

    /**
     * Function code: 005.
     * Poisons the target.
     * */
    public void Move_Function_005(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setPoison(1);
    }

    /**
     * Function code: 006.
     * Badly poisons the target.
     * */
    public void Move_Function_006(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setBadlyPoison(1);
    }

    /**
     * Function code: 007.
     * Paralyzes the target.
     * */
    public void Move_Function_007(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setParalyze(1);
    }

    /**
     * Function code: 008.
     * Paralyzes the target.  Hits some semi-invulnerable targets.  (Thunder)
     * Always hits in rain.
     * (Handled in pbCheck): Accuracy 50% in sunshine.
     * */
    public void Move_Function_008(Pokemon d, Pokemon a, int damage){
        // TODO -> Nincs időjárás, majd a battlefieldbe lesz beépítve.
        d.setHp(d.getHp()-damage);
        d.setParalyze(1);
    }

    /**
     * Function code: 009.
     * Paralyzes the target.  May cause the target to flinch.
     * Az esély a flinch-re 1:10-hez
     * */
    public void Move_Function_009(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        if((r.nextInt(10)+1) == 1)
            d.setFlinch(1);
    }

    /**
     * Function code: 00A.
     * Burns the target.
     * */
    public void Move_Function_00A(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setBurn(1);
    }

    /**
     * Function code: 00B.
     * Burns the target.  May cause the target to flinch.
     * Az esély a flinch-re 1:10-hez
     * */
    public void Move_Function_00B(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setBurn(1);
        if((r.nextInt(10)+1) == 1)
            d.setFlinch(1);
    }

    /**
     * Function code: 00C.
     * Freezes the target.
     * */
    public void Move_Function_00C(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setFreeze(1);
    }

    /**
     * Function code: 00D.
     * Freezes the target.  Always hits in hail.
     * */
    public void Move_Function_00D(Pokemon d, Pokemon a, int damage){
        // TODO -> időjárás..
        d.setHp(d.getHp()-damage);
        d.setFreeze(1);
    }

    /**
     * Function code: 00E.
     * Freezes the target.  May cause the target to flinch.
     * Az esély a flinch-re 1:10-hez
     * */
    public void Move_Function_00E(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setFreeze(1);
        if((r.nextInt(10)+1) == 1)
            d.setFlinch(1);
    }

    /**
     * Function code: 00F.
     * Causes the target to flinch.
     * */
    public void Move_Function_00F(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setFlinch(1);
    }

    /**
     * Function code: 010.
     * Causes the target to flinch.  Does double damage if the target is Minimized.
     * */
    public void Move_Function_010(Pokemon d, Pokemon a, int damage){
        if(d.getMinimized() >= 1)
            d.setHp(d.getHp() - damage*2);
        else
            d.setHp(d.getHp() - damage);
        d.setFlinch(1);
    }

    /**
     * Function code: 011.
     * Causes the target to flinch.  Fails if the user is not asleep.
     * */
    public void Move_Function_011(Pokemon d, Pokemon a, int damage){
        if(a.getSleep() == 0) {
            d.setHp(d.getHp()-damage);
            d.setFlinch(1);
        }
        // else Failed..
    }

    /**
     * Function code: 012.
     * Causes the target to flinch.  Fails if this isn't the user's first turn.
     * */
    public void Move_Function_012(Pokemon d, Pokemon a, int damage){
        // TODO -> nincs még körszámláló.
        d.setHp(d.getHp()-damage);
        d.setFlinch(1);
    }

    /**
     * Function code: 014.
     * Confuses the target.  Chance of causing confusion depends on the cry's volume.
     * Works for Chatot only.  (Chatter)
     * */
    public void Move_Function_014(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        if((r.nextInt(10)+1) == 1)
            d.setConfuse((r.nextInt(5)+1));
    }

    /**
     * Function code: 015.
     * Confuses the target.  Hits some semi-invulnerable targets.  (Hurricane)
     * Always hits in rain.
     * Accuracy 50% in sunshine.
     * */
    public void Move_Function_015(Pokemon d, Pokemon a, int damage){
        // TODO -> Nincs idojaras.
        d.setHp(d.getHp()-damage);
        d.setConfuse((r.nextInt(5)+1));
    }

    /**
     * Function code: 016.
     * Attracts the target.
     * */
    public void Move_Function_016(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setAttract(1);
    }

    /**
     * Function code: 017.
     * Burns, freezes or paralyzes the target.
     * */
    public void Move_Function_017(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        d.setBurn(1);
        d.setFreeze((r.nextInt(5)+1));
        d.setParalyze((r.nextInt(5)+1));
    }

    /**
     * Function code: 018.
     * Cures user of burn, poison and paralysis.
     * */
    public void Move_Function_018(Pokemon d, Pokemon a, int damage){
        a.setBurn(0);
        a.setPoison(0);
        a.setBadlyPoison(0);
        a.setParalyze(0);
    }

    /**
     * Function code: 019.
     * Cures all party Pokémon of permanent status problems.
     * */
    public void Move_Function_019(Pokemon d, Pokemon a, int damage){
        // TODO -> All party pokemon? talan majd ki lehet venni a battleScene-bol.
    }

    /**
     * Function code: 01B.
     * User passes its status problem to the target.
     * */
    public void Move_Function_01B(Pokemon d, Pokemon a, int damage){
        // TODO
    }

    /**
     * Function code: 01C.
     * Increases the user's Attack by 1 stage.
     * One stage increase means attack*1.5
     * */
    public void Move_Function_01C(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        a.setAttack((int)Math.round(a.getAttack() * 1.5));
    }

    /**
     * Function code: 01D.
     * Increases the user's Defense by 1 stage.
     * */
    public void Move_Function_01D(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        a.setDefense((int)Math.round(a.getDefense() * 1.5));
    }

    /**
     * Function code: 01E.
     * Increases the user's Defense by 1 stage.
     * User curls up (making the user's Ice Ball/Rollout do double damage), even if Defense is not boosted. Curling up is not cumulative.
     * */
    public void Move_Function_01E(Pokemon d, Pokemon a, int damage){
        // TODO -> Nincs curl..
        a.setDefense((int)Math.round(a.getDefense() * 1.5));
    }

    /**
     * Function code: 01F.
     * Increases the user's Speed by 1 stage.
     * */
    public void Move_Function_01F(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        a.setSpeed((int)Math.round(a.getSpeed() * 1.5));
    }

    /**
     * Function code: 020.
     * Increases the user's Special Attack by 1 stage.
     * */
    public void Move_Function_020(Pokemon d, Pokemon a, int damage){
        d.setHp(d.getHp()-damage);
        a.setSpecialAttack((int) Math.round(a.getSpecialAttack() * 1.5));
    }

    /**
     * Function code: 021.
     * Increases the user's Special Defense by 1 stage.
     * Charges up Electric attacks, until the end of the next round, the power of the user's damaging Electric attacks is doubled.
     * The effect ends if the user is switched out.
     * */
    public void Move_Function_021(Pokemon d, Pokemon a, int damage){
        // TODO -> Nincs chargeUp..
        a.setSpecialDefense((int)Math.round(a.getSpecialDefense() * 1.5));
    }

    /**
     * Function code: 022.
     * Increases the user's evasion by 1 stage.
     * */
    public void Move_Function_022(Pokemon d, Pokemon a, int damage){
        a.setEvasion(a.getEvasion() + 1);
    }

    /**
     * Function code: 023.
     * Increases the user's critical hit rate by 2 stages.
     * One stage = 10.
     * Stage is in percent.
     * */
    public void Move_Function_023(Pokemon d, Pokemon a, int damage){
        d.setCriticalChance(a.getCriticalChance() + 20);
    }

    /**
     * Function code: 024.
     * Increases the user's Attack and Defense by 1 stage each.
     * */
    public void Move_Function_024(Pokemon d, Pokemon a, int damage){
        a.setAttack((int)Math.round(a.getAttack() * 1.5));
        a.setDefense((int)Math.round(a.getDefense() * 1.5));
    }

    /**
     * Function code: 025.
     * Increases the user's Attack, Defense and accuracy by 1 stage each.
     * Accuracy is in percent.
     * */
    public void Move_Function_025(Pokemon d, Pokemon a, int damage){
        a.setAttack((int)Math.round(a.getAttack() * 1.5));
        a.setDefense((int)Math.round(a.getDefense() * 1.5));
        a.setAccuracy(a.getAccuracy() + 10);
    }

    /**
     * Function code: 026.
     * Increases the user's Attack and Speed by 1 stage each.
     * */
    public void Move_Function_026(Pokemon d, Pokemon a, int damage){
        a.setAttack((int)Math.round(a.getAttack() * 1.5));
        a.setSpeed((int)Math.round(a.getSpeed() + 1.5));
    }

    /**
     * Function code: 027.
     * Increases the user's Attack and Special Attack by 1 stage each.
     * */
    public void Move_Function_027(Pokemon d, Pokemon a, int damage){
        a.setAttack((int)Math.round(a.getAttack() * 1.5));
        a.setSpecialAttack((int)Math.round(a.getSpecialAttack() * 1.5));
    }

    /**
     * Function code: 028.
     * Increases the user's Attack and Special Attack by 1 stage each (2 each in sunshine).
     * */
    public void Move_Function_028(Pokemon d, Pokemon a, int damage){
    }

    /**
     * Function code: 029.
     * Increases the user's Attack and accuracy by 1 stage each.
     * */
    public void Move_Function_029(Pokemon d, Pokemon a, int damage){
    }

    /**
     * Function code: 02A.
     * Increases the user's Defense and Special Defense by 1 stage each.
     * */
    public void Move_Function_02A(Pokemon d, Pokemon a, int damage){
    }

    /**
     * Function code: 02B.
     * Increases the user's Speed, Special Attack and Special Defense by 1 stage each.
     * */
    public void Move_Function_02B(Pokemon d, Pokemon a, int damage){
    }

    /**
     * Function code: 02C.
     * Increases the user's Special Attack and Special Defense by 1 stage each.
     * */
    public void Move_Function_02C(Pokemon d, Pokemon a, int damage){
    }

    /**
     * Function code: 02D.
     * Increases the user's Attack, Defense, Speed, Special Attack and Special Defense by 1 stage each.
     * */
    public void Move_Function_02D(Pokemon d, Pokemon a, int damage){
    }

    /**
     * Function code: 02E.
     * Increases the user's Attack by 2 stages.
     * */
    public void Move_Function_02E(Pokemon d, Pokemon a, int damage){
    }

    /**
     * Function code: 02F.
     * Increases the user's Defense by 2 stages.
     * */
    public void Move_Function_02F(Pokemon d, Pokemon a, int damage){
    }

}
