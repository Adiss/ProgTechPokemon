package hu.experiment_team.models;

import java.util.List;
import java.util.Random;

/**
 * A {@code Pokemon} osztály reprezentál egy adatbázis-beli pokémont.
 * @author Jakab Ádám
 * */
public class Pokemon {

    /**
     * A pokémon adatbázis beli ID-je
     * */
    private final int id;

    /**
     * The actual name of this species.
     * This is only used when displaying the species' name on the screen.
     * */
    private final String displayName;

    /**
     * The primary elemental type of this species.
     * */
    private final String type1;

    /**
     * The secondary elemental type of this species.
     * Type2 is optional.
     * */
    private final String type2;

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
    public enum Stats {
        GET;
        public int hp;
        public int attack;
        public int defense;
        public int speed;
        public int specialAttack;
        public int specialDefense;
        public int accuracy = 100;
        public int criticalChance = 0;
    }

    /**
     * Up to 4 additional abilities this species can have.
     * Is the internal names of those abilities, separated by commas.
     * Pokémon cannot have any hidden ability naturally, and must be specially given one.
     * */
    private final String hiddenAbility;

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
    public Pokemon(int id, String displayName, String type1, String type2, String hiddenAbility, int hp, int attack, int defense, int speed, int spAttack, int spDefense) {
        this.hiddenAbility = hiddenAbility;
        this.id = id;
        this.displayName = displayName;
        this.type1 = type1;
        this.type2 = type2;
        Stats.GET.hp = hp;
        Stats.GET.attack = attack;
        Stats.GET.defense = defense;
        Stats.GET.speed = speed;
        Stats.GET.specialAttack = spAttack;
        Stats.GET.specialDefense = spDefense;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getHiddenAbility() {
        return hiddenAbility;
    }

    @Override
    public String toString() {
        return "ID = " + id +
                ", DISPLAYNAMEs = '" + displayName + '\'' +
                ", TYPE_1 = '" + type1 + '\'' +
                ", TYPE_2 = '" + type2 + '\'' +
                ", HIDDENABILITY = '" + hiddenAbility + '\'' +
                ", HP = '" + Stats.GET.hp + '\'' +
                ", ATTACK = '" + Stats.GET.attack + '\'' +
                ", DEFENSE = '" + Stats.GET.defense + '\'' +
                ", SPEED = '" + Stats.GET.speed + '\'' +
                ", SPATTACK = '" + Stats.GET.specialAttack + '\'' +
                ", SPDEFENSE = '" + Stats.GET.specialDefense + '\'' +
                ", ACCURACY = '" + Stats.GET.accuracy + '\'' +
                ", CRITICAL_CHANCE = '" + Stats.GET.criticalChance + '\'';
    }
}
