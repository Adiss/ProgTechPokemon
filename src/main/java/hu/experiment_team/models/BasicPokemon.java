package hu.experiment_team.models;

import javax.persistence.*;

/**
 * Created by Jakab on 2016.05.07..
 */
@Entity
@Table(name = "pokemon_pokemons")
public class BasicPokemon {

    /**
     * A pokémon adatbázis beli ID-je
     * */
    @Id
    @Column(name = "id")
    private int id;

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
    private int hp;
    @Column(name = "attack")
    private int attack;
    @Column(name = "defense")
    private int defense;
    @Column(name = "speed")
    private int speed;
    @Column(name = "spAttack")
    private int specialAttack;
    @Column(name = "spDefense")
    private int specialDefense;

    /**
     * Up to 4 additional abilities this species can have.
     * Is the internal names of those abilities, separated by commas.
     * Pokémon cannot have any hidden ability naturally, and must be specially given one.
     * */
    @Column(name = "hiddenAbility")
    private String hiddenAbility;

    public BasicPokemon(){

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

    public String getHiddenAbility() {
        return hiddenAbility;
    }

    public void setHiddenAbility(String hiddenAbility) {
        this.hiddenAbility = hiddenAbility;
    }
}
