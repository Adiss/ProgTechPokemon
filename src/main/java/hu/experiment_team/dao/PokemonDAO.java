package hu.experiment_team.dao;

import hu.experiment_team.dao.interfaces.MoveDaoInterface;
import hu.experiment_team.dao.interfaces.PokemonDaoInterface;
import hu.experiment_team.dao.interfaces.TrainerDaoInterface;
import hu.experiment_team.models.BasicPokemon;
import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Random;

/**
 * Ez az osztály hajtja végre az adatbázis műveleteket.
 * @author Jakab Ádám
 */
public enum PokemonDAO implements MoveDaoInterface, PokemonDaoInterface, TrainerDaoInterface{
    INSTANCE;

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PokemonDAO");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    /**
     * A megadott ID alapján lekérdez egy teljes képességet az adatbázisból.
     * @param moveId A képesség adatbázis beli ID-je
     * */
    public Move getMoveById(int moveId){
        return entityManager.find(Move.class, moveId);
    }

    /**
     * A megadott pokemon ID és szint alapján lekérdezi az adott pokemon adott szintjén és alatta tudható spelleket.
     * @param level A pokémon szintje.
     * @param pokemonId A pokémon ID-je
     * */
    public List<Move> getKnownMove(int level, int pokemonId){
        return entityManager.createQuery("SELECT m FROM Move m WHERE m.Id IN (SELECT moveId FROM PokemonMovesByLevelTable WHERE plevel <= " + level + " AND pokemonId = " + pokemonId + ")", Move.class).getResultList();
    }

    /**
     * Lekérdezi az adatbázisból az összes spellt egy listába.
     * */
    public List<Move> pullMoves(){
        return entityManager.createQuery("SELECT m FROM Move m", Move.class).getResultList();
    }

    /**
     * Hozzáad egy trainert (felhasználót) az adatbázishoz.
     * @param t A trainer osztály egy példánya a megfelelő mezőkkel feltöltve.
     * */
    public void insertTrainer(Trainer t){
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
    }

    /**
     * Kiválaszt egy trainert (felhasználót) a neve alapján.
     * @param username A trainer felhasználó neve.
     * */
    public Trainer selectTrainerByName(String username){
        Trainer t = entityManager.createQuery("SELECT t FROM Trainer t WHERE t.username = '" + username + "'", Trainer.class).getSingleResult();
        t.setOwnedPokemons(getOwnedPokemons(t.getId()));
        return t;
    }

    /**
     * Kiválaszt egy trainert (felhasználót) a jelszava alapján.
     * Ennek a jelszónak már az SHA1 kódolt jelszónak kell lennie.
     * @param pass SHA1 kódolt jelszó.
     * */
    public Trainer selectTrainerByPassword(String pass){
        Trainer t = entityManager.createQuery("SELECT t FROM Trainer t WHERE t.password = '" + pass + "'", Trainer.class).getSingleResult();
        t.setOwnedPokemons(getOwnedPokemons(t.getId()));
        return t;
    }

    /**
     * Kiválaszt egy trainert (felhasználót) az e-mail címe alapján.
     * @param email A trainer email címe.
     * */
    public Trainer selectTrainerByEmail(String email){
        Trainer t = entityManager.createQuery("SELECT t FROM Trainer t WHERE t.email = '" + email + "'", Trainer.class).getSingleResult();
        t.setOwnedPokemons(getOwnedPokemons(t.getId()));
        return t;
    }

    /**
     * A megadott pokémon ID alapján lekérdez az adatbázisból egy alap pokémont.
     * @param pokemonId a pokémon adatbázis beli ID-je
     * */
    public Pokemon getPokemonById(int pokemonId){
        BasicPokemon bp = entityManager.find(BasicPokemon.class, pokemonId);
        List<Move> moves = PokemonDAO.INSTANCE.getKnownMove(1, bp.getId());
        Pokemon p = new Pokemon();

        p.setId(bp.getId());
        p.setDisplayName(bp.getDisplayName());
        p.setType1(bp.getType1());
        p.setType2(bp.getType2());
        p.setHp(bp.getHp());
        p.setAttack(bp.getAttack());
        p.setDefense(bp.getDefense());
        p.setSpeed(bp.getSpeed());
        p.setSpecialAttack(bp.getSpecialAttack());
        p.setSpecialDefense(bp.getSpecialDefense());
        p.setHiddenAbility(bp.getHiddenAbility());
        p.setCurrentXp(0);
        p.setLevel(1);
        if(moves.size() >= 1)
            p.setMove1(moves.get(0));
        if(moves.size() >= 2)
            p.setMove2(moves.get(1));
        if(moves.size() >= 3)
            p.setMove3(moves.get(2));
        if(moves.size() >= 4)
            p.setMove4(moves.get(3));
        p.setLastMove(null);
        p.setClonedOne();

        return p;
    }

    /**
     * Kiválaszt egy random alap pokémont az adatbázisból.
     * */
    public Pokemon getRandomPokemon(int level){
        Random r = new Random();

        BasicPokemon bp = entityManager.find(BasicPokemon.class, r.nextInt(649)+1);
        List<Move> moves = getKnownMove(level, bp.getId());
        Pokemon p = new Pokemon();

        p.setId(bp.getId());
        p.setDisplayName(bp.getDisplayName());
        p.setType1(bp.getType1());
        p.setType2(bp.getType2());
        p.setHp(bp.getHp());
        p.setAttack(bp.getAttack());
        p.setDefense(bp.getDefense());
        p.setSpeed(bp.getSpeed());
        p.setSpecialAttack(bp.getSpecialAttack());
        p.setSpecialDefense(bp.getSpecialDefense());
        p.setHiddenAbility(bp.getHiddenAbility());
        p.setCurrentXp(0);
        p.setLevel(level);
        if(moves.size() >= 1)
            p.setMove1(moves.get(0));
        if(moves.size() >= 2)
            p.setMove2(moves.get(1));
        if(moves.size() >= 3)
            p.setMove3(moves.get(2));
        if(moves.size() >= 4)
            p.setMove4(moves.get(3));
        p.setLastMove(null);
        p.setClonedOne();

        return p;
    }

    /**
     * Hozzáad egy pokémont a trainerhez, majd berakja az adatbázisba.
     * @param id A trainer azonosító száma
     * @param p A Pokémon objektuma
     */
    public void addOwnedPokemon(int id, Pokemon p) {
        entityManager.getTransaction().begin();
        p.setOwnerId(id);
        entityManager.persist(p);
        entityManager.getTransaction().commit();
    }


    /**
     * Visszaadja egy listába az össze pokémont, amit a trainer birtokol.
     * @param id A trainer azonosító száma
     * @return A birtokolt pokémonok listája
     */
    public List<Pokemon> getOwnedPokemons(int id) {
        return entityManager.createQuery("SELECT p FROM Pokemon p WHERE p.ownerId = " + id, Pokemon.class).getResultList();
    }

}
