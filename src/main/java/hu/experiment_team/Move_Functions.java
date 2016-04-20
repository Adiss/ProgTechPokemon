package hu.experiment_team;

import hu.experiment_team.models.Move;
import hu.experiment_team.models.OwnedPokemon;

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
    public void Move_Function_000(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
    }

    /**
     * Function code: 001.
     * Nem csinál semmit, abszolút semmit (Splash).
     * */
    public void Move_Function_001(OwnedPokemon d, OwnedPokemon a, int damage){
        /* Magikarp-karp-karp... */
    }

    /**
     * Function code: 002.
     * Stagger.
     * An attack that is used in desperation only if the user has no PP. This also damages the user a little.
     * */
    public void Move_Function_002(OwnedPokemon d, OwnedPokemon a, int damage){
        if(a.getMoves().stream().map(Move::getActualPP).filter(pp -> pp == 0).count() == 0) {
            d.getStats().hp -= damage;
            a.getStats().hp -= a.getClonedOne().getStats().hp * 0.15;
        }
    }

    /**
     * Function code: 003.
     * Puts the target to sleep. (1-3 rounds)
     * */
    public void Move_Function_003(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().sleep = (r.nextInt(3) + 1);
    }

    /**
     * Function code: 004.
     * Makes the target drowsy.  It will fall asleep at the end of the next turn.
     * */
    public void Move_Function_004(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().drowsy = 1;
    }

    /**
     * Function code: 005.
     * Poisons the target.
     * */
    public void Move_Function_005(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().poison = 1;
    }

    /**
     * Function code: 006.
     * Badly poisons the target.
     * */
    public void Move_Function_006(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().badlyPoison = 1;
    }

    /**
     * Function code: 007.
     * Paralyzes the target.
     * */
    public void Move_Function_007(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().paralyze = 1;
    }

    /**
     * Function code: 008.
     * Paralyzes the target.  Hits some semi-invulnerable targets.  (Thunder)
     * Always hits in rain.
     * (Handled in pbCheck): Accuracy 50% in sunshine.
     * */
    public void Move_Function_008(OwnedPokemon d, OwnedPokemon a, int damage){
        // TODO -> Nincs időjárás, majd a battlefieldbe lesz beépítve.
        d.getStats().hp -= damage;
        d.getEffects().paralyze = 1;
    }

    /**
     * Function code: 009.
     * Paralyzes the target.  May cause the target to flinch.
     * Az esély a flinch-re 1:10-hez
     * */
    public void Move_Function_009(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        if((r.nextInt(10)+1) == 1)
            d.getEffects().flinch = 1;
    }

    /**
     * Function code: 00A.
     * Burns the target.
     * */
    public void Move_Function_00A(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().burn = 1;
    }

    /**
     * Function code: 00B.
     * Burns the target.  May cause the target to flinch.
     * Az esély a flinch-re 1:10-hez
     * */
    public void Move_Function_00B(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().burn = 1;
        if((r.nextInt(10)+1) == 1)
            d.getEffects().flinch = 1;
    }

    /**
     * Function code: 00C.
     * Freezes the target.
     * */
    public void Move_Function_00C(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().freeze = 1;
    }

    /**
     * Function code: 00D.
     * Freezes the target.  Always hits in hail.
     * */
    public void Move_Function_00D(OwnedPokemon d, OwnedPokemon a, int damage){
        // TODO -> időjárás..
        d.getStats().hp -= damage;
        d.getEffects().freeze = 1;
    }

    /**
     * Function code: 00E.
     * Freezes the target.  May cause the target to flinch.
     * Az esély a flinch-re 1:10-hez
     * */
    public void Move_Function_00E(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().freeze = 1;
        if((r.nextInt(10)+1) == 1)
            d.getEffects().flinch = 1;
    }

    /**
     * Function code: 00F.
     * Causes the target to flinch.
     * */
    public void Move_Function_00F(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().flinch = 1;
    }

    /**
     * Function code: 010.
     * Causes the target to flinch.  Does double damage if the target is Minimized.
     * */
    public void Move_Function_010(OwnedPokemon d, OwnedPokemon a, int damage){
        if(d.getEffects().minimized >= 1)
            d.getStats().hp -= damage*2;
        else
            d.getStats().hp -= damage;
        d.getEffects().flinch = 1;
    }

    /**
     * Function code: 011.
     * Causes the target to flinch.  Fails if the user is not asleep.
     * */
    public void Move_Function_011(OwnedPokemon d, OwnedPokemon a, int damage){
        if(a.getEffects().sleep == 0) {
            d.getStats().hp -= damage;
            d.getEffects().flinch = 1;
        }
        // else Failed..
    }

    /**
     * Function code: 012.
     * Causes the target to flinch.  Fails if this isn't the user's first turn.
     * */
    public void Move_Function_012(OwnedPokemon d, OwnedPokemon a, int damage){
        // TODO -> nincs még körszámláló.
        d.getStats().hp -= damage;
        d.getEffects().flinch = 1;
    }

    /**
     * Function code: 014.
     * Confuses the target.  Chance of causing confusion depends on the cry's volume.
     * Works for Chatot only.  (Chatter)
     * */
    public void Move_Function_014(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        if((r.nextInt(10)+1) == 1)
            d.getEffects().confuse = (r.nextInt(5)+1);
    }

    /**
     * Function code: 015.
     * Confuses the target.  Hits some semi-invulnerable targets.  (Hurricane)
     * Always hits in rain.
     * Accuracy 50% in sunshine.
     * */
    public void Move_Function_015(OwnedPokemon d, OwnedPokemon a, int damage){
        // TODO -> Nincs idojaras.
        d.getStats().hp -= damage;
        d.getEffects().confuse = (r.nextInt(5)+1);
    }

    /**
     * Function code: 016.
     * Attracts the target.
     * */
    public void Move_Function_016(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().attract = 1;
    }

    /**
     * Function code: 017.
     * Burns, freezes or paralyzes the target.
     * */
    public void Move_Function_017(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        d.getEffects().burn = 1;
        d.getEffects().freeze = (r.nextInt(5)+1);
        d.getEffects().paralyze = (r.nextInt(5)+1);
    }

    /**
     * Function code: 018.
     * Cures user of burn, poison and paralysis.
     * */
    public void Move_Function_018(OwnedPokemon d, OwnedPokemon a, int damage){
        a.getEffects().burn = 0;
        a.getEffects().poison = 0;
        a.getEffects().badlyPoison = 0;
        a.getEffects().paralyze = 0;
    }

    /**
     * Function code: 019.
     * Cures all party Pokémon of permanent status problems.
     * */
    public void Move_Function_019(OwnedPokemon d, OwnedPokemon a, int damage){
        // TODO -> All party pokemon? talan majd ki lehet venni a battleScene-bol.
    }

    /**
     * Function code: 01B.
     * User passes its status problem to the target.
     * */
    public void Move_Function_01B(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getEffects().sleep = 0;
        d.getEffects().poison = 0;
        d.getEffects().badlyPoison = 0;
        d.getEffects().paralyze = 0;
        d.getEffects().burn = 0;
        d.getEffects().freeze = 0;
        d.getEffects().confuse = 0;
        d.getEffects().attract = 0;

        a.getEffects().sleep = 0;
        a.getEffects().poison = 0;
        a.getEffects().badlyPoison = 0;
        a.getEffects().paralyze = 0;
        a.getEffects().burn = 0;
        a.getEffects().freeze = 0;
        a.getEffects().confuse = 0;
        a.getEffects().attract = 0;
    }

    /**
     * Function code: 01C.
     * Increases the user's Attack by 1 stage.
     * One stage increase means attack*1.5
     * */
    public void Move_Function_01C(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        a.getStats().attack *= 1.5;
    }

    /**
     * Function code: 01D.
     * Increases the user's Defense by 1 stage.
     * */
    public void Move_Function_01D(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        a.getStats().defense *= 1.5;
    }

    /**
     * Function code: 01E.
     * Increases the user's Defense by 1 stage.
     * User curls up (making the user's Ice Ball/Rollout do double damage), even if Defense is not boosted. Curling up is not cumulative.
     * */
    public void Move_Function_01E(OwnedPokemon d, OwnedPokemon a, int damage){
        // TODO -> Nincs curl..
        a.getStats().defense *= 1.5;
    }

    /**
     * Function code: 01F.
     * Increases the user's Speed by 1 stage.
     * */
    public void Move_Function_01F(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        a.getStats().speed *= 1.5;
    }

    /**
     * Function code: 020.
     * Increases the user's Special Attack by 1 stage.
     * */
    public void Move_Function_020(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().hp -= damage;
        a.getStats().specialAttack*= 1.5;
    }

    /**
     * Function code: 021.
     * Increases the user's Special Defense by 1 stage.
     * Charges up Electric attacks, until the end of the next round, the power of the user's damaging Electric attacks is doubled.
     * The effect ends if the user is switched out.
     * */
    public void Move_Function_021(OwnedPokemon d, OwnedPokemon a, int damage){
        // TODO -> Nincs chargeUp..
        a.getStats().specialDefense*= 1.5;
    }

    /**
     * Function code: 022.
     * Increases the user's evasion by 1 stage.
     * */
    public void Move_Function_022(OwnedPokemon d, OwnedPokemon a, int damage){
        a.getEffects().evasion += 1;
    }

    /**
     * Function code: 023.
     * Increases the user's critical hit rate by 2 stages.
     * One stage = 10.
     * Stage is in percent.
     * */
    public void Move_Function_023(OwnedPokemon d, OwnedPokemon a, int damage){
        d.getStats().criticalChance += 20;
    }

    /**
     * Function code: 024.
     * Increases the user's Attack and Defense by 1 stage each.
     * */
    public void Move_Function_024(OwnedPokemon d, OwnedPokemon a, int damage){
        a.getStats().attack *= 1.5;
        a.getStats().defense *= 1.5;
    }

    /**
     * Function code: 025.
     * Increases the user's Attack, Defense and accuracy by 1 stage each.
     * Accuracy is in percent.
     * */
    public void Move_Function_025(OwnedPokemon d, OwnedPokemon a, int damage){
        a.getStats().attack *= 1.5;
        a.getStats().defense *= 1.5;
        a.getStats().accuracy += 10;
    }

    /**
     * Function code: 026.
     * Increases the user's Attack and Speed by 1 stage each.
     * */
    public void Move_Function_026(OwnedPokemon d, OwnedPokemon a, int damage){
        a.getStats().attack *= 1.5;
        a.getStats().speed *= 1.5;
    }

    /**
     * Function code: 027.
     * Increases the user's Attack and Special Attack by 1 stage each.
     * */
    public void Move_Function_027(OwnedPokemon d, OwnedPokemon a, int damage){
        a.getStats().attack *= 1.5;
        a.getStats().specialAttack *= 1.5;
    }

    /**
     * Function code: 028.
     * Increases the user's Attack and Special Attack by 1 stage each (2 each in sunshine).
     * */
    public void Move_Function_028(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 029.
     * Increases the user's Attack and accuracy by 1 stage each.
     * */
    public void Move_Function_029(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 02A.
     * Increases the user's Defense and Special Defense by 1 stage each.
     * */
    public void Move_Function_02A(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 02B.
     * Increases the user's Speed, Special Attack and Special Defense by 1 stage each.
     * */
    public void Move_Function_02B(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 02C.
     * Increases the user's Special Attack and Special Defense by 1 stage each.
     * */
    public void Move_Function_02C(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 02D.
     * Increases the user's Attack, Defense, Speed, Special Attack and Special Defense by 1 stage each.
     * */
    public void Move_Function_02D(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 02E.
     * Increases the user's Attack by 2 stages.
     * */
    public void Move_Function_02E(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 02F.
     * Increases the user's Defense by 2 stages.
     * */
    public void Move_Function_02F(OwnedPokemon d, OwnedPokemon a, int damage){
    }

}
