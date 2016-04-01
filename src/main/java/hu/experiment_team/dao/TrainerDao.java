package hu.experiment_team.dao;

import hu.experiment_team.dao.interfaces.TrainerDaoInterface;
import hu.experiment_team.models.OwnedPokemon;
import hu.experiment_team.models.Trainer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Ez az osztály fogja kezelni a trainerekkel (felhasználókkal) kapcsolatos adatbázis műveleteket.
 * Singleton osztály, nem kell példányosítani, az INSTANCE-on keresztül használható.
 * */
public enum TrainerDao implements TrainerDaoInterface {

    /**
     * Ezen a mezőn keresztül érhetőek el az osztály metódusai.
     * */
    INSTANCE;
    /**
     * Létrehozunk egy props változót a properties fájlnak, amiben az adatbázis eléréséhez szükséges információk vannak.
     * */
    Properties props = new Properties();
    InputStream propFile = getClass().getResourceAsStream("/database.properties");
    /**
     * This contains the actual connection.
     * */
    private Connection conn = null;
    /**
     * This contains the mysql statement.
     * */
    private PreparedStatement prepStmt = null;
    /**
     * This contains the result of the query.
     * */
    private ResultSet rs = null;

    /**
     * Hozzáad egy trainert (felhasználót) az adatbázishoz.
     * @param t A trainer osztály egy példánya a megfelelő mezőkkel feltöltve.
     * */
    @Override
    public void insert(Trainer t){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String insertStatement = "INSERT INTO POKEMON_TRAINERS (id, username, displayName, password, email, register_date, partypokemon1, partypokemon2, partypokemon3, partypokemon4, partypokemon5, partypokemon6, wins, looses) VALUES(?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(insertStatement);
            prepStmt.setInt(1, 0);
            prepStmt.setString(2, t.getUsername());
            prepStmt.setString(3, t.getDisplayName());
            prepStmt.setString(4, t.getPassword());
            prepStmt.setString(5, t.getEmail());
            prepStmt.setString(6, null);
            prepStmt.setString(7, null);
            prepStmt.setString(8, null);
            prepStmt.setString(9, null);
            prepStmt.setString(10, null);
            prepStmt.setString(11, null);
            prepStmt.setInt(12, 0);
            prepStmt.setInt(13, 0);
            prepStmt.executeUpdate();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * Kiválaszt egy trainert (felhasználót) a neve alapján.
     * @param name A trainer felhasználó neve.
     * */
    @Override
    public Trainer selectByName(String name){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Trainer t = null;
        String selectStatement = "SELECT * FROM POKEMON_TRAINERS WHERE username = ?";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, name);
            rs = prepStmt.executeQuery();
            if(rs.next()){
                List<OwnedPokemon> partyPokemons = new ArrayList<OwnedPokemon>(){{
                    add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon1")));
                    add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon2")));
                    add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon3")));
                    add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon4")));
                    add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon5")));
                    add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon6")));
                }};
                t = new Trainer.Builder(rs.getString("username"), rs.getString("displayName"), rs.getString("password"), rs.getString("email"))
                        .partyPokemons(partyPokemons)
                        .matchWin(rs.getInt("wins"))
                        .matchLoose(rs.getInt("looses"))
                        .register_date(rs.getDate("register_date"))
                        .id(rs.getInt("id"))
                        .ownedPokemons(PokemonDao.INSTANCE.getOwnedPokemons(rs.getInt("id")))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return t;
    }

    /**
     * Kiválaszt egy trainert (felhasználót) a jelszava alapján.
     * Ennek a jelszónak már az SHA1 kódolt jelszónak kell lennie.
     * @param pass SHA1 kódolt jelszó.
     * */
    @Override
    public Trainer selectByPassword(String pass){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Trainer t = null;
        String selectStatement = "SELECT * FROM POKEMON_TRAINERS WHERE password = ?";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, pass);
            rs = prepStmt.executeQuery();
            if(rs.next()){
                List<OwnedPokemon> partyPokemons = new ArrayList<OwnedPokemon>(){{
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon1")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon2")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon3")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon4")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon5")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon6")));
                }};
                t = new Trainer.Builder(rs.getString("username"), rs.getString("displayName"), rs.getString("password"), rs.getString("email"))
                        .partyPokemons(partyPokemons)
                        .matchWin(rs.getInt("wins"))
                        .matchLoose(rs.getInt("looses"))
                        .register_date(rs.getDate("register_date"))
                        .id(rs.getInt("id"))
                        .ownedPokemons(PokemonDao.INSTANCE.getOwnedPokemons(rs.getInt("id")))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return t;
    }

    /**
     * Kiválaszt egy trainert (felhasználót) az e-mail címe alapján.
     * @param email A trainer email címe.
     * */
    @Override
    public Trainer selectByEmail(String email){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Trainer t = null;
        String selectStatement = "SELECT * FROM POKEMON_TRAINERS WHERE email = ?";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, email);
            rs = prepStmt.executeQuery();
            if(rs.next()){
                List<OwnedPokemon> partyPokemons = new ArrayList<OwnedPokemon>(){{
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon1")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon2")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon3")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon4")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon5")));
                    this.add(PokemonDao.INSTANCE.getPokemonById(rs.getInt("PartyPokemon6")));
                }};
                t = new Trainer.Builder(rs.getString("username"), rs.getString("displayName"), rs.getString("password"), rs.getString("email"))
                        .partyPokemons(partyPokemons)
                        .matchWin(rs.getInt("wins"))
                        .matchLoose(rs.getInt("looses"))
                        .register_date(rs.getDate("register_date"))
                        .id(rs.getInt("id"))
                        .ownedPokemons(PokemonDao.INSTANCE.getOwnedPokemons(rs.getInt("id")))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return t;
    }

    /**
     * Ez a függvény fogja lezárni az adatbázis kapcsolatokat.
     * */
    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (prepStmt != null) {
                prepStmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
