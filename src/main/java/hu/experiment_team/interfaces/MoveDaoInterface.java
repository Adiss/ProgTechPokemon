package hu.experiment_team.interfaces;

import hu.experiment_team.Move;
import hu.experiment_team.OwnedPokemon;
import java.util.List;

/**
 * A képességekkel kapcsolatos adatbázis műveleteket tartalmazó osztály interfésze.
 * */
public interface MoveDaoInterface {
    /**
     * A megadott ID alapján lekérdez egy teljes képességet az adatbázisból.
     * @param moveId A képesség adatbázis beli ID-je
     * @return A lekérdezett képesség objektumát adja vissza
     * */
    Move getMoveById(int moveId);

    /**
     * A megadott pokemon ID és szint alapján lekérdezi az adott pokemon adott szintjén és alatta tudható spelleket.
     * @param level A pokémon szintje.
     * @param pokemonId A pokémon ID-je
     * @return A pokémon álltal ismert képességek listáját adja vissza
     * */
    List<Integer> getKnownMove(int level, int pokemonId);

    /**
     * A pokémon álltal ismert képességekből ad vissza egyet véletlenszerűen.
     * @param p A pokémon ID-je
     * @return Egy random move
     * */
    Move getRandomKnownMove(OwnedPokemon p);

    /**
     * Az adatbázisban található összes képességet lekérdezi egy listába.
     * @return A játékban fellelhető képességek listája
     * */
    List<Move> pullMoves();
}
