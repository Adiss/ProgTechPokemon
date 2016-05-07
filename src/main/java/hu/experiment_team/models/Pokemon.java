package hu.experiment_team.models;

import hu.experiment_team.Effectiveness;
import hu.experiment_team.Move_Functions;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A {@code Pokemon} osztály reprezentál egy adatbázis-beli pokémont.
 * @author Jakab Ádám
 * */
@Entity
@Table(name = "pokemon_owned_pokemons")
public class Pokemon implements Cloneable {

    /**
     * A pokémon adatbázis beli ID-je
     * */
    @Id
    @Column(name = "pokemonId")
    private int id;

    @Column(name = "ownerId")
    private int ownerId;

    /**
     * The actual name of this species.
     * This is only used when displaying the species' name on the screen.
     * */
    @Column(name = "displayName")
    private String displayName;

    /**
     * The primary elemental type of this species.
     * */
    @Column(name = "type1")
    private String type1;

    /**
     * The secondary elemental type of this species.
     * Type2 is optional.
     * */
    @Column(name = "type2")
    private String type2;

    /**
     * 8 mutating values, corresponding to:
     *  - HP
     *  - Attack
     *  - Defense
     *  - Speed
     *  - Special Attack
     *  - Special Defense
     *  - Accuracy
     *  - Critical Strike Chance
     * Each value can be between 0 and 255.
     * */
    @Column(name = "hp")
    public int hp;
    @Column(name = "attack")
    public int attack;
    @Column(name = "defense")
    public int defense;
    @Column(name = "speed")
    public int speed;
    @Column(name = "spAttack")
    public int specialAttack;
    @Column(name = "spDefense")
    public int specialDefense;

    @Transient
    public int accuracy = 100;
    @Transient
    public int criticalChance = 0;

    /**
     * Up to 4 additional abilities this species can have.
     * Is the internal names of those abilities, separated by commas.
     * Pokémon cannot have any hidden ability naturally, and must be specially given one.
     * */
    @Column(name = "hiddenAbility")
    private String hiddenAbility;

    /**
     * A pokémon aktuális szintjén összegyűjtött aktuális XP
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az Pokemons táblába az adatbázisban.
     * */
    @Column(name = "currentXp")
    private int currentXp;

    /**
     * A pokémon aktuális szintje
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az Pokemons táblába az adatbázisban.
     * */
    @Column(name = "pokemonlevel")
    private int level;

    /**
     * A pokémon első képességei
     * Ez a mező csak akkor használatos, ha a Pokémont valamelyik trainer birtokolja, azaz benne van az Pokemons táblába az adatbázisban.
     * */
    @ManyToOne
    @JoinColumn(name = "move1Id", referencedColumnName = "id")
    private Move move1;
    @ManyToOne
    @JoinColumn(name = "move2Id", referencedColumnName = "id")
    private Move move2;
    @ManyToOne
    @JoinColumn(name = "move3Id", referencedColumnName = "id")
    private Move move3;
    @ManyToOne
    @JoinColumn(name = "move4Id", referencedColumnName = "id")
    private Move move4;
    /**
     * A pokémon álltal használt utolsó képesség.
     * */
    @Transient
    private Move lastMove;

    /**
     * Egy mentés a pokemon harc előtti állapotáról.
     * */
    @Transient
    private Pokemon clonedOne;

    /**
     * A pokémon harc közbeni effektjeinek egy gyűjtő osztálya.
     * */
    @Transient
    public int sleep = 0;
    @Transient
    public int drowsy = 0;
    @Transient
    public int poison = 0;
    @Transient
    public int badlyPoison = 0;
    @Transient
    public int paralyze = 0;
    @Transient
    public int burn = 0;
    @Transient
    public int flinch = 0;
    @Transient
    public int freeze = 0;
    @Transient
    public int minimized = 0;
    @Transient
    public int confuse = 0;
    @Transient
    public int attract = 0;
    @Transient
    public int evasion = 0;

    /**
     * Paraméter nélküli konstruktor a JPA-nak
     */
    public Pokemon(){

    }

    /**
     * Konstruktor a pokémon osztályhoz.
     *
     * @param id A pokémon adatbázis beli azonosító száma, típusa <code>int</code>
     * @param displayName A pokémon neve, típusa <code>String</code>
     * @param type1 A pokémon elsődleges típusa, ez alapján lehet eldönteni, hogy egy pokémon milyen támadásokkal szemben mennyire ellenálló. Típusa <code>String</code>
     * @param type2 A pokémon másodlagos típusa, ez alapján lehet eldönteni, hogy egy pokémon milyen támadásokkal szemben mennyire ellenálló. Típusa <code>String</code>
     * @param hiddenAbility Néhány pokémon rendelkezik ilyen rejtett képességgel, amik valamilyen típussal szemben, vagy környezettől függően, vagy stb.. előnyt biztosítanak neki a harcban. Típusa <code>String</code>
     * @param hp A pokémon életpontjainak mennyisége. Ha 0-ra csökken a pokémon kiesett a harcból.
     * @param attack A pokémon testi támadó ereje.
     * @param defense A pokémon testi védekező ereje.
     * @param speed A pokémon sebessége.
     * @param spAttack A pokémon mentális ereje.
     * @param spDefense A pokémon mentális állóképessége.
     */
    public Pokemon(int id, String displayName, String type1, String type2, String hiddenAbility, int hp, int attack, int defense, int speed, int spAttack, int spDefense, int level) {
        this.hiddenAbility = hiddenAbility;
        this.id = id;
        this.displayName = displayName;
        this.type1 = type1;
        this.type2 = type2;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.specialAttack = spAttack;
        this.specialDefense = spDefense;
        this.currentXp = 0;
        this.level = level;
        this.lastMove = null;
    }

    public void hurt(Pokemon attacker, Move move){

        // If the move is not a status modifier
        if(!move.getType().equals("Status")){

            // Count damage
            int damage = 0;
            double STAB = attacker.getType1().equals(move.getType()) || attacker.getType2().equals(move.getType()) ? 1.5 : 1.0;
            double typeEffectiveness = Effectiveness.INSTANCE.get(move.getType(), this.getType1())*10;
            Random r = new Random();
            double rand = 0.85 + (1.0-0.85) * r.nextDouble();

            double userAttack;
            double oppDefense;
            if(move.getMoveCategory().equals("Physical")){
                userAttack = (2 * attacker.getLevel() + 10) * attacker.attack * move.getBaseDamage();
                oppDefense = 250 * (this.defense);
            } else {
                userAttack = (2 * attacker.getLevel() + 10) * attacker.specialAttack * move.getBaseDamage();
                oppDefense = 250 * (this.specialDefense);
            }
            double modifiers = typeEffectiveness * STAB * rand;

            // Count accuracy
            if((r.nextInt(100) + 1) <= (move.getAccuracy() + attacker.accuracy)){
                // Count Crit-chance
                if(r.nextInt(100)+1 > attacker.criticalChance){
                    damage = (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers);
                    selectMoveFunction(this, attacker, move, damage);
                    attacker.lastMove = move;
                } else {
                    damage = (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers) * attacker.criticalChance;
                    selectMoveFunction(this, attacker, move, damage);
                    attacker.lastMove = move;
                }
            }

        }
    }

    private void selectMoveFunction(Pokemon d, Pokemon a, Move m, int damage){
        int countSelected = 0;
        try {
            for(Method method : Move_Functions.class.getDeclaredMethods()){
                if(method.getName().contains(m.getFunctionCode())){
                    method.invoke(Move_Functions.INSTANCE, d, a, damage);
                    countSelected++;
                }
            }
            if(countSelected == 0)
                d.hp -= damage;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * Getter és Setter
     * */
    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getHiddenAbility() {
        return hiddenAbility;
    }

    public void setHiddenAbility(String hiddenAbility) {
        this.hiddenAbility = hiddenAbility;
    }

    public int getCurrentXp() {
        return currentXp;
    }

    public void setCurrentXp(int currentXp) {
        this.currentXp = currentXp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Move getMove1() {
        return move1;
    }

    public void setMove1(Move move1) {
        this.move1 = move1;
    }

    public Move getMove2() {
        return move2;
    }

    public void setMove2(Move move2) {
        this.move2 = move2;
    }

    public Move getMove3() {
        return move3;
    }

    public void setMove3(Move move3) {
        this.move3 = move3;
    }

    public Move getMove4() {
        return move4;
    }

    public void setMove4(Move move4) {
        this.move4 = move4;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public Pokemon getClonedOne() {
        return clonedOne;
    }

    public void setClonedOne() {
        try {
            clonedOne = (Pokemon) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getCriticalChance() {
        return criticalChance;
    }

    public void setCriticalChance(int criticalChance) {
        this.criticalChance = criticalChance;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getDrowsy() {
        return drowsy;
    }

    public void setDrowsy(int drowsy) {
        this.drowsy = drowsy;
    }

    public int getPoison() {
        return poison;
    }

    public void setPoison(int poison) {
        this.poison = poison;
    }

    public int getBadlyPoison() {
        return badlyPoison;
    }

    public void setBadlyPoison(int badlyPoison) {
        this.badlyPoison = badlyPoison;
    }

    public int getParalyze() {
        return paralyze;
    }

    public void setParalyze(int paralyze) {
        this.paralyze = paralyze;
    }

    public int getBurn() {
        return burn;
    }

    public void setBurn(int burn) {
        this.burn = burn;
    }

    public int getFlinch() {
        return flinch;
    }

    public void setFlinch(int flinch) {
        this.flinch = flinch;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public int getMinimized() {
        return minimized;
    }

    public void setMinimized(int minimized) {
        this.minimized = minimized;
    }

    public int getConfuse() {
        return confuse;
    }

    public void setConfuse(int confuse) {
        this.confuse = confuse;
    }

    public int getAttract() {
        return attract;
    }

    public void setAttract(int attract) {
        this.attract = attract;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    @Override
    public String toString() {
        return "ID = " + id +
                ", DISPLAYNAMEs = '" + displayName + '\'' +
                ", TYPE_1 = '" + type1 + '\'' +
                ", TYPE_2 = '" + type2 + '\'' +
                ", HIDDENABILITY = '" + hiddenAbility + '\'' +
                ", HP = '" + this.hp + '\'' +
                ", ATTACK = '" + this.attack + '\'' +
                ", DEFENSE = '" + this.defense + '\'' +
                ", SPEED = '" + this.speed + '\'' +
                ", SPATTACK = '" + this.specialAttack + '\'' +
                ", SPDEFENSE = '" + this.specialDefense + '\'' +
                ", ACCURACY = '" + this.accuracy + '\'' +
                ", CRITICAL_CHANCE = '" + this.criticalChance + '\'' +
                ", MOVE1 = '" + this.move1 + '\'' +
                ", MOVE2 = '" + this.move2 + '\'' +
                ", MOVE3 = '" + this.move3 + '\'' +
                ", MOVE4 = '" + this.move4 + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pokemon)) return false;

        Pokemon pokemon = (Pokemon) o;

        if (id != pokemon.id) return false;
        if (displayName != null ? !displayName.equals(pokemon.displayName) : pokemon.displayName != null) return false;
        if (type1 != null ? !type1.equals(pokemon.type1) : pokemon.type1 != null) return false;
        if (type2 != null ? !type2.equals(pokemon.type2) : pokemon.type2 != null) return false;
        return hiddenAbility != null ? hiddenAbility.equals(pokemon.hiddenAbility) : pokemon.hiddenAbility == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (type1 != null ? type1.hashCode() : 0);
        result = 31 * result + (type2 != null ? type2.hashCode() : 0);
        result = 31 * result + (hiddenAbility != null ? hiddenAbility.hashCode() : 0);
        return result;
    }
}
