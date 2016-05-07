package hu.experiment_team.dao.interfaces;

import hu.experiment_team.models.Pokemon;

import java.util.List;

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
    Pokemon getPokemonById(int pokemonId);

    /**
     * Kiválaszt egy random alap pokémont az adatbázisból.
     * @return Egy pokémon objektuma
     * */
    Pokemon getRandomPokemon(int level);

    /**
     * Hozzáad egy pokémont az egyik játékos gyűjteményéhez.
     * @param id A Trainer ID-ja
     * @param p Egy pokémon objektum amit hozzá kívánunk adni a gyűjteményhez
     * */
    void addOwnedPokemon(int id, Pokemon p);

    /**
     * Lekérdezi a felhasználó összes birtokolt pokémonját.
     * @param id a felhasználóhoz tartozó azonosító szám
     * */
    List<Pokemon> getOwnedPokemons(int id);
}
