package hu.experiment_team;

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
    }

    /**
     * Function code: 016.
     * Attracts the target.
     * */
    public void Move_Function_016(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 017.
     * Burns, freezes or paralyzes the target.
     * */
    public void Move_Function_017(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 018.
     * Cures user of burn, poison and paralysis.
     * */
    public void Move_Function_018(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 019.
     * Cures all party Pokémon of permanent status problems.
     * */
    public void Move_Function_019(OwnedPokemon d, OwnedPokemon a, int damage){
    }

    /**
     * Function code: 01B.
     * User passes its status problem to the target.
     * */
    public void Move_Function_01B(OwnedPokemon d, OwnedPokemon a, int damage){
    }
    
}
