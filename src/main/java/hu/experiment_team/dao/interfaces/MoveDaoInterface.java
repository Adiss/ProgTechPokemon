package hu.experiment_team.dao.interfaces;

import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

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
    List<Move> getKnownMove(int level, int pokemonId);

    /**
     * Az adatbázisban található összes képességet lekérdezi egy listába.
     * @return A játékban fellelhető képességek listája
     * */
    List<Move> pullMoves();
}
