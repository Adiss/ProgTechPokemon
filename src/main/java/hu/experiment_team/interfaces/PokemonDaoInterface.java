package hu.experiment_team.interfaces;

import hu.experiment_team.OwnedPokemon;

/**
 * A pokémonokkal kapcsolatos adatbázis műveleteket tartalmazó osztály interfésze.
 * @author Jakab Ádám
 * */
public interface PokemonDaoInterface {
    /**
     * A megadott pokémon ID alapján lekérdez az adatbázisból egy alap pokémont.
     * @param pokemonId a pokémon adatbázis beli ID-je
     * @return A pokémon objektumát adja vissza
     * */
    OwnedPokemon getPokemonById(int pokemonId);

    /**
     * Kiválaszt egy random alap pokémont az adatbázisból.
     * @return Egy pokémon objektuma
     * */
    OwnedPokemon getRandomPokemon();
}
