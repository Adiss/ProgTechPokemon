package hu.experiment_team.models;

import hu.experiment_team.Effectiveness;
import hu.experiment_team.dao.MoveDao;
import hu.experiment_team.Move_Functions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OwnedPokemon extends Pokemon implements Cloneable {

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
     * Egy mentés a pokemon harc előtti állapotáról.
     * */
    private OwnedPokemon clonedOne;

    /**
     * A pokémon harc közbeni effektjeinek egy gyűjtő osztálya.
     * */
    public enum battleEffects {
        GET;
        public int sleep = 0;
        public int drowsy = 0;
        public int poison = 0;
        public int badlyPoison = 0;
        public int paralyze = 0;
        public int burn = 0;
        public int flinch = 0;
        public int freeze = 0;
        public int minimized = 0;
        public int confuse = 0;
        public int attract = 0;
        public int evasion = 0;
    }

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
    public OwnedPokemon(int id, String displayName, String type1, String type2, String hiddenAbility, int hp, int attack, int defense, int speed, int spAttack, int spDefense, int level) {
        super(id, displayName, type1, type2, hiddenAbility, hp, attack, defense, speed, spAttack, spDefense);
        this.currentXp = 0;
        this.level = level;
        this.moves = new ArrayList<Move>(){{
            MoveDao.INSTANCE.getKnownMove(1, id).stream()
                    .forEach(move -> add(MoveDao.INSTANCE.getMoveById(move)));
        }};
        this.lastMove = null;

        try {
            clonedOne = (OwnedPokemon) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
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

    public OwnedPokemon getClonedOne() {
        return clonedOne;
    }

    public battleEffects getEffects(){
        return battleEffects.GET;
    }

    public void hurt(OwnedPokemon attacker, Move move){

        // If the move is not a status modifier
        if(!move.getType().equals("Status")){

            // Count damage
            int damage = 0;
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

            // Count accuracy
            if((r.nextInt(100) + 1) <= (move.getAccuracy() + attacker.getStats().accuracy)){
                // Count Crit-chance
                if(r.nextInt(100)+1 > attacker.getStats().criticalChance){
                    damage = (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers);
                    selectMoveFunction(this, attacker, move, damage);
                    attacker.lastMove = move;
                } else {
                    damage = (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers) * attacker.getStats().criticalChance;
                    selectMoveFunction(this, attacker, move, damage);
                    attacker.lastMove = move;
                }
            }

        }
    }

    private void selectMoveFunction(OwnedPokemon d, OwnedPokemon a, Move m, int damage){
        int countSelected = 0;
        try {
            for(Method method : Move_Functions.class.getDeclaredMethods()){
                if(method.getName().contains(m.getFunctionCode())){
                    method.invoke(Move_Functions.INSTANCE, this, d, a, damage);
                    countSelected++;
                }
            }
            if(countSelected == 0)
                d.getStats().hp -= damage;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnedPokemon)) return false;

        OwnedPokemon that = (OwnedPokemon) o;

        if (currentXp != that.currentXp) return false;
        if (level != that.level) return false;
        if (moves != null ? !moves.equals(that.moves) : that.moves != null) return false;
        if (lastMove != null ? !lastMove.equals(that.lastMove) : that.lastMove != null) return false;
        return clonedOne.equals(that.clonedOne);

    }

    @Override
    public int hashCode() {
        int result = currentXp;
        result = 31 * result + level;
        result = 31 * result + (moves != null ? moves.hashCode() : 0);
        result = 31 * result + (lastMove != null ? lastMove.hashCode() : 0);
        result = 31 * result + clonedOne.hashCode();
        return result;
    }
}
