package hu.experiment_team.models;

import javax.persistence.*;

/**
 * This class contains information about the pokemon moves
 * @author Jakab Ádám
 * */
@Entity
@Table(name = "pokemon_moves")
public class Move {

    /**
     * Each move's ID number must be different.
     * The ID number must be a whole number greater than 0 (i.e. 1,2,3...), up to 999.
     * Missing numbers are not a problem (e.g. the sequence 23,24,25,58,59,60... is allowed).
     * */
    @Id
    @Column(name = "id")
    private int Id;

    /**
     * The actual name of the move.
     * This is only used when displaying the move's name on the screen.
     * */
    @Column(name = "displayName")
    private String displayName;

    /**
     * The move's function code.
     * A function code is a 3-digit hexadecimal number that describes a defined effect (e.g. poisons the opponent).
     * */
    @Column(name = "functionCode")
    private String functionCode;

    /**
     * The base amount of damage the move will inflict on the attack target.
     * Is 0 if it is a status move (i.e. doesn't inflict damage).
     * Is 1 if the move calculates the base damage later (e.g. depending on the user's weight).
     * For multi-hit moves, this is the damage dealt per hit.
     * */
    @Column(name = "baseDamage")
    private int baseDamage;

    /**
     * The internal name of the move's elemental type.
     * */
    @Column(name = "type")
    private String type;

    /**
     * Is one of the following:
     *  - Physical (calculates damage using Attack and Defense)
     *  - Special (calculates damage using Special Attack and Special Defense)
     *  - Status (inflicts no damage)
     * */
    @Column(name = "moveCategory")
    private String moveCategory;

    /**
     * The move's accuracy, out of 100.
     * Set to 0 if the move doesn't perform an accuracy check (i.e. it cannot be evaded).
     * */
    @Column(name = "accuracy")
    private int accuracy;

    /**
     * The maximum amount of PP this move can have, not counting modifiers such as the item PP Up.
     * If this is 0, the move is infinite use.
     * */
    @Column(name = "totalPP")
    private int totalPP;

    /**
     * The probability that the move's additional effect occurs, out of 100.
     * For example, Poison Sting poisons the opponent 30% of the time, so this value would be 30.
     * Set to 0 if this move does nothing other than its effect (e.g. for all status moves).
     * */
    @Column(name = "additionalEffectChance")
    private int additionalEffectChance;

    /**
     * The order the move will be used in (overrides Speed calculations).
     * Usually 0, but can be between -6 and 6.
     * A higher value means higher priority.
     * Moves with equal priority will be used depending on their user's Speed.
     * - For example, Quick Attack has a priority of 1.
     * */
    @Column(name = "priority")
    private int priority;

    /**
     * Any combination of the following letters:
     *  - a - This move makes physical contact with the opponent.
     *  - b - The opponent can use Protect or Detect to protect itself from this move.
     *  - c - The opponent can use Magic Coat to redirect the effect of this move. Use this flag if the move deals no damage but causes a negative effect on an opponent. (Flags c and d are mutually exclusive.)
     *  - d - The opponent can use Snatch to steal the effect of this move. Use this flag for most moves that target the user. (Flags c and d are mutually exclusive.)
     *  - e - This move can be copied by Mirror Move.
     *  - f - This move has a 10% chance of making the opponent flinch if the user is holding a King's Rock. Use this flag for all damaging moves.
     *  - g - If the user is frozen, this move will thaw it out before it is used.
     *  - h - This move has a high critical hit rate.
     *  - i - This is a healing move.
     *  - j - This move is a punching move.
     *  - k - This is a sound-based move.
     *  - l - While Gravity is in effect, this move is disabled for all active Pokémon.
     * */
    @Column(name = "flags")
    private String flags;

    /**
     * A move aktuális PP-je
     * */
    @Transient
    private int actualPP;

    /**
     * Empty constructor for JPA
     * */
    public Move(){
    }

    /**
     * Getters and Setters
     * */
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoveCategory() {
        return moveCategory;
    }

    public void setMoveCategory(String moveCategory) {
        this.moveCategory = moveCategory;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getTotalPP() {
        return totalPP;
    }

    public void setTotalPP(int totalPP) {
        this.totalPP = totalPP;
    }

    public int getAdditionalEffectChance() {
        return additionalEffectChance;
    }

    public void setAdditionalEffectChance(int additionalEffectChance) {
        this.additionalEffectChance = additionalEffectChance;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public int getActualPP() {
        return actualPP;
    }

    public void setActualPP(int actualPP) {
        this.actualPP = actualPP;
    }

    @Override
    public String toString() {
        return "Move{" +
                "Id=" + Id +
                ", displayName='" + displayName + '\'' +
                ", functionCode='" + functionCode + '\'' +
                ", baseDamage=" + baseDamage +
                ", type='" + type + '\'' +
                ", moveCategory='" + moveCategory + '\'' +
                ", accuracy=" + accuracy +
                ", totalPP=" + totalPP +
                ", additionalEffectChance=" + additionalEffectChance +
                ", priority=" + priority +
                ", flags='" + flags + '\'' +
                ", actualPP=" + actualPP +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;

        Move move = (Move) o;

        if (Id != move.Id) return false;
        if (baseDamage != move.baseDamage) return false;
        if (accuracy != move.accuracy) return false;
        if (totalPP != move.totalPP) return false;
        if (additionalEffectChance != move.additionalEffectChance) return false;
        if (priority != move.priority) return false;
        if (actualPP != move.actualPP) return false;
        if (displayName != null ? !displayName.equals(move.displayName) : move.displayName != null) return false;
        if (functionCode != null ? !functionCode.equals(move.functionCode) : move.functionCode != null) return false;
        if (type != null ? !type.equals(move.type) : move.type != null) return false;
        if (moveCategory != null ? !moveCategory.equals(move.moveCategory) : move.moveCategory != null) return false;
        return flags != null ? flags.equals(move.flags) : move.flags == null;

    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (functionCode != null ? functionCode.hashCode() : 0);
        result = 31 * result + baseDamage;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (moveCategory != null ? moveCategory.hashCode() : 0);
        result = 31 * result + accuracy;
        result = 31 * result + totalPP;
        result = 31 * result + additionalEffectChance;
        result = 31 * result + priority;
        result = 31 * result + (flags != null ? flags.hashCode() : 0);
        result = 31 * result + actualPP;
        return result;
    }
}
