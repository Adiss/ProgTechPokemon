package hu.experiment_team.models;

import hu.experiment_team.dao.PokemonDao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This model contains the trainer.
 * @author Jakab �d�m
 * */
public class Trainer {

    /**
     * Database ID of the trainer
     * */
    private final int id;
    /**
     * Username of the user.
     * */
    private final String username;
    /**
     * Display name of the user.
     * */
    private final String displayName;
    /**
     * Password of the user.
     * */
    private final String password;
    /**
     * Email of the user.
     * */
    private final String email;
    /**
     * The date when the user registered.
     * */
    private final Date register_date;
    /**
     * List of owned pokémons.
     * */
    private List<OwnedPokemon> ownedPokemons;
    /**
     * List of actually carried pokemons.
     * */
    private List<OwnedPokemon> partyPokemons;
    /**
     * Number of wins.
     * */
    private int matchWin;
    /**
     * Number of looses.
     * */
    private int matchLoose;
    /**
     * Ez jelzi a player online állapotát.
     * */
    private int online = 0;

    /**
     * This inner class builds the object.
     * @author Jakab �d�m
     * */
    public static class Builder {

        /**
         * Database ID of the trainer
         * */
        private int id;
        /**
         * Username of the user.
         * */
        private String username;
        /**
         * Display name of the user.
         * */
        private String displayName;
        /**
         * Password of the user.
         * */
        private String password;
        /**
         * Email of the user.
         * */
        private String email;

        /**
         * The date when the user registered.
         * */
        private Date register_date = null;
        /**
         * List of owned pokémons
         * */
        private List<OwnedPokemon> ownedPokemons;
        /**
         * List of actually carried pokemons
         * */
        private List<OwnedPokemon> partyPokemons = null;
        /**
         * Amount of wins
         * */
        private int matchWin = 0;
        /**
         * Amount of looses
         * */
        private int matchLoose = 0;

        /**
         * Constructor.
         * @param username Username of the user
         * @param displayName Display name of the user
         * @param password Password of the user
         * @param email Email of the user
         * */
        public Builder(String username, String displayName, String password, String email){
            this.username = username;
            this.displayName = displayName;
            this.password = password;
            this.email = email;
        }
        public Builder register_date(Date val){ register_date = val; return this; }
        public Builder ownedPokemons(List<OwnedPokemon> val){ this.ownedPokemons = val; return this; }
        public Builder partyPokemons(List<OwnedPokemon> val){ this.partyPokemons = val; return this; }
        public Builder matchWin(int val){ matchWin = val; return this; }
        public Builder matchLoose(int val){ matchLoose = val; return this; }
        public Builder id(int val){ id = val; return this; }
        public Trainer build(){ return new Trainer(this); }

    }

    /**
     * Constructor.
     * @param builder Data came from the builder class
     * */
    private Trainer(Builder builder){
        this.id = builder.id;
        this.username = builder.username;
        this.displayName = builder.displayName;
        this.password = builder.password;
        this.email = builder.email;
        this.register_date = builder.register_date;
        this.ownedPokemons = builder.ownedPokemons;
        this.partyPokemons = builder.partyPokemons;
        this.matchWin = builder.matchWin;
        this.matchLoose = builder.matchLoose;
    }

    /**
     * @return Username of the user
     * */
    public String getUsername(){ return username; }
    /**
     * @return Display name of the user
     * */
    public String getDisplayName(){ return displayName; }
    /**
     * @return Password of the user
     * */
    public String getPassword(){ return password; }
    /**
     * @return Email of the user
     * */
    public String getEmail(){ return email; }
    /**
     * @return Register date of the user
     * */
    public Date getRegisterDate(){ return register_date; }
    /**
     * @return List of owned pokemons
     * */
    public List<OwnedPokemon> getOwnedPokemons() { return ownedPokemons; }
    /**
     * @return List of party pokemons
     * */
    public List<OwnedPokemon> getPartyPokemons() {
        return new ArrayList<OwnedPokemon>(){{
            addAll(partyPokemons.stream().filter(p -> p.getStats().hp > 0).collect(Collectors.toList()));
        }};
    }
    /**
     * @return Amount of wins
     * */
    public int getMatchWin() { return matchWin; }
    /**
     * @return Amount os looses
     * */
    public int getMatchLoose() { return matchLoose; }
    /**
     * @return The trainer's database id
     * */
    public int getId() { return id; }
    /**
     * @param ownedPokemons List of owned pokemons
     * */
    public void setOwnedPokemons(List<OwnedPokemon> ownedPokemons) { this.ownedPokemons = ownedPokemons; }
    /**
     * @param partyPokemons List of carried pokemons
     * */
    public void setPartyPokemons(List<OwnedPokemon> partyPokemons) { this.partyPokemons = partyPokemons; }
    /**
     * @param matchWin Amount of wins
     * */
    public void setMatchWin(int matchWin) { this.matchWin = matchWin; }
    /**
     * @param matchLoose Amount of looses
     * */
    public void setMatchLoose(int matchLoose) { this.matchLoose = matchLoose; }

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
    public void addPokemon(OwnedPokemon p){
        PokemonDao.INSTANCE.addOwnedPokemon(this.id, p);
    }

}
