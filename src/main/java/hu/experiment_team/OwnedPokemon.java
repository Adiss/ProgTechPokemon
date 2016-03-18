package hu.experiment_team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jakab on 2016.03.18..
 */
public class OwnedPokemon extends Pokemon {

    /**
     * A pokémon aktuális szintjén összegyűjtött aktuális XP
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int currentXp;

    /**
     * A pokémon aktuális szintje
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int level;

    /**
     * A pokémon első képességei
     * Ez a mező csak akkor használatos, ha a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private List<Move> moves;

    /**
     * A pokémon álltal használt utolsó képesség.
     * */
    private Move lastMove;

    /**
     * Konstruktor a pokémon osztályhoz.
     *
     * @param id            A pokémon adatbázis beli azonosító száma, típusa <code>int</code>
     * @param displayName   A pokémon neve, típusa <code>String</code>
     * @param type1         A pokémon elsődleges típusa, ez alapján lehet eldönteni, hogy egy pokémon milyen támadásokkal szemben mennyire ellenálló. Típusa <code>String</code>
     * @param type2         A pokémon másodlagos típusa, ez alapján lehet eldönteni, hogy egy pokémon milyen támadásokkal szemben mennyire ellenálló. Típusa <code>String</code>
     * @param hiddenAbility Néhány pokémon rendelkezik ilyen rejtett képességgel, amik valamilyen típussal szemben, vagy környezettől függően, vagy stb.. előnyt biztosítanak neki a harcban. Típusa <code>String</code>
     * @param hp            A pokémon életpontjainak mennyisége. Ha 0-ra csökken a pokémon kiesett a harcból.
     * @param attack        A pokémon testi támadó ereje.
     * @param defense       A pokémon testi védekező ereje.
     * @param speed         A pokémon sebessége.
     * @param spAttack      A pokémon mentális ereje.
     * @param spDefense     A pokémon mentális állóképessége.
     */
    public OwnedPokemon(int id, String displayName, String type1, String type2, String hiddenAbility, int hp, int attack, int defense, int speed, int spAttack, int spDefense) {
        super(id, displayName, type1, type2, hiddenAbility, hp, attack, defense, speed, spAttack, spDefense);
        this.currentXp = 0;
        this.level = 1;
        this.moves = new ArrayList<Move>(){{
            MoveDao.INSTANCE.getKnownMove(1, id).stream()
                    .forEach(move -> add(MoveDao.INSTANCE.getMoveById(move)));
        }};
        this.lastMove = null;
    }

    public int getCurrentXp() {
        return currentXp;
    }

    public int getLevel() {
        return level;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public Stats getStats(){
        return Stats.GET;
    }

    public void hurt(OwnedPokemon attacker, Move move){
        if(!move.getType().equals("Status")){
            double STAB = attacker.getType1().equals(move.getType()) || attacker.getType2().equals(move.getType()) ? 1.5 : 1.0;
            double typeEffectiveness = Effectiveness.INSTANCE.get(move.getType(), super.getType1())*10;
            Random r = new Random();
            double rand = 0.85 + (1.0-0.85) * r.nextDouble();

            double userAttack;
            double oppDefense;
            if(move.getMoveCategory().equals("Physical")){
                userAttack = (2 * attacker.getLevel() + 10) * attacker.getStats().attack * move.getBaseDamage();
                oppDefense = 250 * (Stats.GET.defense);
            } else {
                userAttack = (2 * attacker.getLevel() + 10) * attacker.getStats().specialAttack * move.getBaseDamage();
                oppDefense = 250 * (Stats.GET.specialDefense);
            }
            double modifiers = typeEffectiveness * STAB * rand;

            if(r.nextInt(100)+1 > attacker.getStats().criticalChance){
                Stats.GET.hp -= (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers);
                attacker.lastMove = move;
            } else {
                Stats.GET.hp -= (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers) * attacker.getStats().criticalChance;
            }
        }
    }

    @Override
    public String toString() {
        return "OwnedPokemon = {" + super.toString() + "}" +
                ", currentXp = " + currentXp +
                ", level = " + level +
                ", moves = " + moves +
                ", lastMove = " + lastMove +
                '}';
    }
}
