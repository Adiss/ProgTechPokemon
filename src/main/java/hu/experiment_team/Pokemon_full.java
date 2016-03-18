package hu.experiment_team;

/**
 * A {@code Pokemon} osztály reprezentál egy adatbázis-beli pokémont.
 * @author Jakab Ádám.
 *
 * @deprecated Use "Pokemon" class instead.
 * */
@Deprecated
public class Pokemon_full implements Cloneable {

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
     * This is the name the scripts refer to and use.
     * Typically written only in capital letters with no spaces or symbols.
     * It is never shown to the player.
     * */
    private final String internalName;

    /**
     * The species' kind (e.g. Bulbasaur is the Seed Pokémon).
     * The word "Pokémon" is automatically added to the end, so only "Seed" needs to be here.
     * */
    private final String kind;

    /**
     * The Pokédex entry.
     * */
    private final String pokeDex;

    /**
     * The primary and secondary elemental types of this species.
     * */
    private final String type1;

    /**
     * Type2 is optional.
     * */
    private final String type2;

    /**
     * 11 mutating values, corresponding to:
     *  - HP
     *  - Attack
     *  - Defense
     *  - Speed
     *  - Special Attack
     *  - Special Defense
     *
     *  The number of EVs gained by defeating a Pokémon of this species:
     *  - Effort Point Attack
     *  - Effort Point Defense
     *  - Effort Point Speed
     *  - Effort Point Special Attack
     *  - Effort Point Special Defense
     * Each value can be between 0 and 255.
     * */
    private Stats stats;

    /**
     * The catch rate of this species. Is a number between 0 and 255.
     * The higher the number, the more likely a capture (0 means it cannot be caught by anything except a Master Ball).
     * */
    private final int rareness;

    /**
     * The base amount of Experience gained from defeating a Pokémon of this species. Is a number between 0 and 65535.
     * This base amount is then modified depending on the level and ownership of the defeated Pokémon, along with several other factors.
     * */
    private final int baseXp;

    /**
     * The amount of happiness a newly caught Pokémon of this species will have.
     * Is a number between 0 and 255, although is typically 70.
     * */
    private final int happiness;

    /**
     * The rate at which a Pokémon of this species gains levels (i.e. how much Experience is needed to level up). One of:
     *  - Fast
     *  - Medium or MediumFast
     *  - Slow
     *  - Parabolic or MediumSlow
     *  - Erratic
     *  - Fluctuating
     * */
    private final String growthRate;

    /**
     * The number of steps it takes to hatch an egg of this species.
     * Is typically a multiple of 255, and is typically 5355.
     * */
    private final int stepsToHatch;

    /**
     * The main colour of this species. Must be one of:
     *   - Black
     *   - Blue
     *   - Brown
     *   - Gray
     *   - Green
     *   - Pink
     *   - Purple
     *   - Red
     *   - White
     *   - Yellow
     * */
    private final String color;

    /**
     * The location this species can typically be found in. Is one of:
     *   - Cave
     *   - Forest
     *   - Grassland
     *   - Mountain
     *   - Rare
     *   - RoughTerrain
     *   - Sea
     *   - Urban
     *   - WatersEdge
     * "Rare" can be taken to mean "unknown" here.
     * */
    private final String habitat;

    /**
     * Up to 4 additional abilities this species can have.
     * Is the internal names of those abilities, separated by commas.
     * Pokémon cannot have any hidden ability naturally, and must be specially given one.
     * */
    private final String hiddenAbility;

    /**
     * The egg groups this species belongs to.
     * Is either one word or two comma-separated words, depending on how many egg groups this species belongs to.
     * If either egg group is "Undiscovered", this species cannot breed.
     *  - Monster
     *  - Water1
     *  - Bug
     *  - Flying
     *  - Field
     *  - Fairy
     *  - Grass
     *  - Humanlike
     *  - Water3
     *  - Mineral
     *  - Amorphous
     *  - Water2
     *  - Ditto
     *  - Dragon
     *  - Undiscovered
     * "Water1" is for sea creatures, "Water2" is for fish, and "Water3" is for shellfish.
     * "Ditto" should contain only Ditto, as a species in this group can breed with any other breedable Pokémon.
     * */
    private final String compatibility;

    /**
     * The height of the species in meters, to one decimal place.
     * Use a period for the decimal point, and do not use commas for thousands.
     * The Pokédex will automatically show this height in feet/inches, if the game recognises that the player is in the USA.
     * This is only cosmetic; the rest of the scripts still calculate using the metres value defined.
     * */
    private final double height;

    /**
     * The weight of the species in kilograms, to one decimal place.
     * Use a period for the decimal point, and do not use commas for thousands.
     * The Pokédex will automatically show this weight in pounds, if the game recognises that the player is in the USA.
     * This is only cosmetic; the rest of the scripts still calculate using the kilograms value defined.
     * */
    private final double weight;

    /**
     * The likelihood of a Pokémon of this species being a certain gender. Must be one of:
     *   - AlwaysMale
     *   - FemaleOneEighth
     *   - Female25Percent
     *   - Female50Percent
     *   - Female75Percent
     *   - FemaleSevenEighths
     *   - AlwaysFemale
     *   - Genderless
     * */
    private final String genderRate;

    /**
     * How far down the screen the back sprite is in battle.
     * A higher number means the back sprite is placed lower down the screen.
     * Can be positive or negative, and is 0 by default.
     * */
    private final int battlerPlayerY;

    /**
     * How far down the screen the enemy (front) sprite is in battle.
     * A higher number means the front sprite is placed lower down the screen.
     * Can be positive or negative, and is 0 by default.
     * */
    private final int battlerEnemyY;

    /**
     * How far up the screen the enemy (front) sprite is in battle.
     * A higher number means the front sprite is placed lower down the screen.
     * Can only be positive or 0, and is 0 by default.
     * This is the exact opposite of BattlerEnemyY.
     * If this value is greater than 0, the Pokémon's shadow is shown in battle.
     * */
    private final String battlerAltitude;

    /**
     * This is a clone os the current pokemon. We use it when a pokemon starts a battle.
     * After the battle ends this clone will be reseted.
     * */
    public Pokemon_full clonedPokemon;

    public static class Builder {

        /**
         * Required parameters to build a pokemon which is ready to fight.
         * */
        private final int id;
        private final String displayName;
        private final String internalName;
        private final String type1;
        private final String type2;
        private final int hp;
        private final int attack;
        private final int defense;
        private final int speed;
        private final int spAttack;
        private final int spDefense;
        private final int effortPointsAttack;
        private final int effortPointsDefense;
        private final int effortPointsSpeed;
        private final int effortPointsSpAttack;
        private final int effortPointsSpDefense;

        /**
         * Optional parameters but still recommended to fill it.
         * */
        private String pokeDex = "";
        private String kind = "";
        private int rareness = 0;
        private int baseXp = 0;
        private int happiness = 0;
        private String growthRate = "";
        private int stepsToHatch = 0;
        private String color = "";
        private String habitat = "";
        private String hiddenAbility = "";
        private String compatibility = "";
        private double height = 0.0;
        private double weight = 0.0;
        private String genderRate = "";
        private int battlerPlayerY = 0;
        private int battlerEnemyY = 0;
        private String battlerAltitude = "";

        public Builder(int id, String displayName, String internalName, String type1, String type2,
                       int hp, int attack, int defense, int speed, int spAttack, int spDefense,
                       int effortPointsAttack, int effortPointsDefense, int effortPointsSpeed, int effortPointsSpAttack, int effortPointsSpDefense){
            this.id = id;
            this.displayName = displayName;
            this.internalName = internalName;
            this.type1 = type1;
            this.type2 = type2;
            this.hp = hp;
            this.attack = attack;
            this.defense = defense;
            this.speed = speed;
            this.spAttack = spAttack;
            this.spDefense = spDefense;
            this.effortPointsAttack = effortPointsAttack;
            this.effortPointsDefense = effortPointsDefense;
            this.effortPointsSpeed = effortPointsSpeed;
            this.effortPointsSpAttack = effortPointsSpAttack;
            this.effortPointsSpDefense = effortPointsSpDefense;
        }

        public Builder pokeDex(String val){
            this.pokeDex = val;
            return this;
        }
        public Builder kind(String val){
            this.kind = val;
            return this;
        }
        public Builder rareness(int val){
            this.rareness = val;
            return this;
        }
        public Builder baseXp(int val){
            this.baseXp = val;
            return this;
        }
        public Builder happiness(int val){
            this.happiness = val;
            return this;
        }
        public Builder growthRate(String val){
            this.growthRate = val;
            return this;
        }
        public Builder stepsToHatch(int val){
            this.stepsToHatch = val;
            return this;
        }
        public Builder color(String val){
            this.color = val;
            return this;
        }
        public Builder habitat(String val){
            this.habitat = val;
            return this;
        }
        public Builder hiddenAbility(String val){
            this.hiddenAbility = val;
            return this;
        }
        public Builder compatibility(String val){
            this.compatibility = val;
            return this;
        }
        public Builder height(double val){
            this.height = val;
            return this;
        }
        public Builder weight(double val){
            this.weight = val;
            return this;
        }
        public Builder genderRate(String val){
            this.genderRate = val;
            return this;
        }
        public Builder battlerPlayerY(int val){
            this.battlerPlayerY = val;
            return this;
        }
        public Builder battlerEnemyY(int val){
            this.battlerEnemyY = val;
            return this;
        }
        public Builder battlerAltitude(String val){
            this.battlerAltitude = val;
            return this;
        }
        public Pokemon_full build(){
            return new Pokemon_full(this);
        }
    }

    private Pokemon_full(Builder builder){
        id = builder.id;
        displayName = builder.displayName;
        internalName = builder.internalName;
        kind = builder.kind;
        pokeDex = builder.pokeDex;
        type1 = builder.type1;
        type2 = builder.type2;
        stats = new Stats(builder.hp, builder.attack, builder.defense, builder.speed, builder.spAttack, builder.spDefense,
                                builder.effortPointsAttack, builder.effortPointsDefense, builder.effortPointsSpeed,
                                builder.effortPointsSpAttack, builder.effortPointsSpDefense);
        rareness = builder.rareness;
        baseXp = builder.baseXp;
        happiness = builder.happiness;
        growthRate = builder.growthRate;
        stepsToHatch = builder.stepsToHatch;
        color = builder.color;
        habitat = builder.habitat;
        hiddenAbility = builder.hiddenAbility;
        compatibility = builder.compatibility;
        height = builder.height;
        weight = builder.weight;
        genderRate = builder.genderRate;
        battlerPlayerY = builder.battlerPlayerY;
        battlerEnemyY = builder.battlerEnemyY;
        battlerAltitude = builder.battlerAltitude;

        clonedPokemon = (Pokemon_full) this.clone();
    }

    public class Stats {
        private int hp;
        private int attack;
        private int defense;
        private int speed;
        private int spAttack;
        private int spDefense;

        private int effortPointsAttack;
        private int effortPointsDefense;
        private int effortPointsSpeed;
        private int effortPointsSpAttack;
        private int effortPointsSpDefense;

        public Stats(int hp, int attack, int defense, int speed, int spAttack, int spDefense, int effortPointsAttack,
                     int effortPointsDefense, int effortPointsSpeed, int effortPointsSpAttack, int effortPointsSpDefense) {
            this.hp = hp;
            this.attack = attack;
            this.defense = defense;
            this.speed = speed;
            this.spAttack = spAttack;
            this.spDefense = spDefense;
            this.effortPointsAttack = effortPointsAttack;
            this.effortPointsDefense = effortPointsDefense;
            this.effortPointsSpeed = effortPointsSpeed;
            this.effortPointsSpAttack = effortPointsSpAttack;
            this.effortPointsSpDefense = effortPointsSpDefense;
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

        public int getSpAttack() {
            return spAttack;
        }

        public void setSpAttack(int spAttack) {
            this.spAttack = spAttack;
        }

        public int getSpDefense() {
            return spDefense;
        }

        public void setSpDefense(int spDefense) {
            this.spDefense = spDefense;
        }

        public int getEffortPointsAttack() {
            return effortPointsAttack;
        }

        public void setEffortPointsAttack(int effortPointsAttack) {
            this.effortPointsAttack = effortPointsAttack;
        }

        public int getEffortPointsDefense() {
            return effortPointsDefense;
        }

        public void setEffortPointsDefense(int effortPointsDefense) {
            this.effortPointsDefense = effortPointsDefense;
        }

        public int getEffortPointsSpeed() {
            return effortPointsSpeed;
        }

        public void setEffortPointsSpeed(int effortPointsSpeed) {
            this.effortPointsSpeed = effortPointsSpeed;
        }

        public int getEffortPointsSpAttack() {
            return effortPointsSpAttack;
        }

        public void setEffortPointsSpAttack(int effortPointsSpAttack) {
            this.effortPointsSpAttack = effortPointsSpAttack;
        }

        public int getEffortPointsSpDefense() {
            return effortPointsSpDefense;
        }

        public void setEffortPointsSpDefense(int effortPointsSpDefense) {
            this.effortPointsSpDefense = effortPointsSpDefense;
        }

        public int getHp() {
            return hp;
        }

        public void setHp(int hp) {
            this.hp = hp;
        }
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getKind() {
        return kind;
    }

    public String getPokeDex() {
        return pokeDex;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public Stats getStats() {
        return stats;
    }

    public int getRareness() {
        return rareness;
    }

    public int getBaseXp() {
        return baseXp;
    }

    public int getHappiness() {
        return happiness;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public int getStepsToHatch() {
        return stepsToHatch;
    }

    public String getColor() {
        return color;
    }

    public String getHabitat() {
        return habitat;
    }

    public String getHiddenAbility() {
        return hiddenAbility;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getGenderRate() {
        return genderRate;
    }

    public int getBattlerPlayerY() {
        return battlerPlayerY;
    }

    public int getBattlerEnemyY() {
        return battlerEnemyY;
    }

    public String getBattlerAltitude() {
        return battlerAltitude;
    }


    public Pokemon_full clone(){
        try {
            return (Pokemon_full) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("No cloneable implemented");
        }
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", internalName='" + internalName + '\'' +
                ", kind='" + kind + '\'' +
                ", pokeDex='" + pokeDex + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", stats=" + stats +
                ", rareness=" + rareness +
                ", baseXp=" + baseXp +
                ", happiness=" + happiness +
                ", growthRate='" + growthRate + '\'' +
                ", stepsToHatch=" + stepsToHatch +
                ", color='" + color + '\'' +
                ", habitat='" + habitat + '\'' +
                ", hiddenAbility='" + hiddenAbility + '\'' +
                ", compatibility='" + compatibility + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", genderRate='" + genderRate + '\'' +
                ", battlerPlayerY=" + battlerPlayerY +
                ", battlerEnemyY=" + battlerEnemyY +
                ", battlerAltitude='" + battlerAltitude + '\'' +
                ", clonedPokemon=" + clonedPokemon +
                '}';
    }
}
