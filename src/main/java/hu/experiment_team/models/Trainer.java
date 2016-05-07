package hu.experiment_team.models;

import hu.experiment_team.dao.PokemonDAO;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * This model contains the trainer.
 * @author Jakab �d�m
 * */
@Entity
@Table(name = "pokemon_trainers")
public class Trainer {

    /**
     * Database ID of the trainer
     * */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Username of the user.
     * */
    @Column(name = "username")
    private String username;
    /**
     * Display name of the user.
     * */
    @Column(name = "displayName")
    private String displayName;
    /**
     * Password of the user.
     * */
    @Column(name = "password")
    private String password;
    /**
     * Email of the user.
     * */
    @Column(name = "email")
    private String email;
    /**
     * The date when the user registered.
     * */
    @Column(name = "register_date")
    private Timestamp register_date;
    /**
     * List of owned pokémons.
     * */
    @Transient
    private List<Pokemon> ownedPokemons;
    /**
     * List of actually carried pokemons.
     * */
    @Transient
    private List<Pokemon> partyPokemons = new ArrayList<>();
    /**
     * Number of wins.
     * */
    @Column(name = "wins")
    private int matchWin;
    /**
     * Number of looses.
     * */
    @Column(name = "looses")
    private int matchLoose;
    /**
     * Ez jelzi a player online állapotát.
     * */
    @Transient
    private int online = 0;

    /**
     * Empty constructor for JPA
     * */
    public Trainer(){

    }

    /**
     * Setters and Getters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Timestamp register_date) {
        this.register_date = register_date;
    }

    public List<Pokemon> getOwnedPokemons() {
        return ownedPokemons;
    }

    public void setOwnedPokemons(List<Pokemon> ownedPokemons) {
        this.ownedPokemons = ownedPokemons;
    }

    public List<Pokemon> getPartyPokemons() {
        return partyPokemons;
    }

    public void setPartyPokemons(List<Pokemon> partyPokemons) {
        this.partyPokemons = partyPokemons;
    }

    public void addPartyPokemon(Pokemon p){
        this.partyPokemons.add(p);
    }

    public int getMatchWin() {
        return matchWin;
    }

    public void setMatchWin(int matchWin) {
        this.matchWin = matchWin;
    }

    public int getMatchLoose() {
        return matchLoose;
    }

    public void setMatchLoose(int matchLoose) {
        this.matchLoose = matchLoose;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    /**
     * Hozzáad a Trainerhez egy pokémont.
     * @param p A pokémon objektuma
     * */
    public void addPokemon(Pokemon p){ PokemonDAO.INSTANCE.addOwnedPokemon(this.id, p); }


    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", register_date=" + register_date +
                ", ownedPokemons=" + ownedPokemons +
                ", partyPokemons=" + partyPokemons +
                ", matchWin=" + matchWin +
                ", matchLoose=" + matchLoose +
                ", online=" + online +
                '}';
    }
}
