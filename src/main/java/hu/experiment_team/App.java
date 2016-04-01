package hu.experiment_team;

import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        /*
        OwnedPokemon defender = PokemonDao.INSTANCE.getRandomPokemon();
        OwnedPokemon attacker = PokemonDao.INSTANCE.getRandomPokemon();
        System.out.println("DEFENDER: " + defender);
        System.out.println("ATTACKER: " + attacker);
        System.out.println(defender.getStats().hp);
        defender.hurt(attacker, MoveDao.INSTANCE.getRandomKnownMove(attacker));
        System.out.println(attacker.getLastMove());
        System.out.println(defender.getStats().hp);
        */

        OwnedPokemon defender = PokemonDao.INSTANCE.getRandomPokemon();
        OwnedPokemon attacker = PokemonDao.INSTANCE.getRandomPokemon();
        System.out.println("DEFENDER: " + defender);
        System.out.println("ATTACKER: " + attacker);
        System.out.println(defender.getStats().hp);
        defender.hurt(attacker, MoveDao.INSTANCE.getRandomKnownMove(attacker));
        System.out.println(attacker.getLastMove());
        System.out.println(defender.getStats().hp);

    }
}
