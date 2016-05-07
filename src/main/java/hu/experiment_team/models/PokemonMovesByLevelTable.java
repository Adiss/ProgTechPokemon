package hu.experiment_team.models;

import javax.persistence.*;

/**
 * Created by Jakab on 2016.05.06..
 */
@Entity
@Table(name = "pokemon_moves_by_level")
public class PokemonMovesByLevelTable {

    @Id
    private int id;
    @Column(name = "pokemonId")
    private int pokemonId;
    @Column(name = "plevel")
    private int plevel;
    @Column(name = "moveId")
    private int moveId;

    public PokemonMovesByLevelTable(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public int getPlevel() {
        return plevel;
    }

    public void setPlevel(int plevel) {
        this.plevel = plevel;
    }

    public int getMoveId() {
        return moveId;
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }
}
