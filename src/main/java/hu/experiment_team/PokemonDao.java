package hu.experiment_team;

import hu.experiment_team.interfaces.PokemonDaoInterface;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

/**
 * Ez az osztály fogja kezelni a pokémonokkal kapcsolatos adatbázis műveleteket.
 * Singleton osztály, nem kell példányosítani, az INSTANCE-on keresztül használható.
 * */
public enum PokemonDao implements PokemonDaoInterface {

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
     * A megadott pokémon ID alapján lekérdez az adatbázisból egy alap pokémont.
     * @param pokemonId a pokémon adatbázis beli ID-je
     * */
    @Override
    public OwnedPokemon getPokemonById(int pokemonId){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        OwnedPokemon p = null;
        String selectStatement = "SELECT * FROM POKEMON_POKEMONS WHERE id = ?";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, pokemonId);
            rs = prepStmt.executeQuery();
            while(rs.next()){
                p = new OwnedPokemon(rs.getInt("ID"), rs.getString("DISPLAYNAME"), rs.getString("TYPE_1"),
                        rs.getString("TYPE_2"), rs.getString("HIDDENABILITY"), rs.getInt("HP"), rs.getInt("ATTACK"),
                        rs.getInt("DEFENSE"), rs.getInt("SPEED"), rs.getInt("SPATTACK"), rs.getInt("SPDEFENSE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return p;
    }


    /**
     * Kiválaszt egy random alap pokémont az adatbázisból.
     * */
    @Override
    public OwnedPokemon getRandomPokemon(){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random r = new Random();
        OwnedPokemon p = null;
        String selectStatement = "SELECT * FROM POKEMON_POKEMONS WHERE id = ?";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, r.nextInt(649-1) + 1);
            rs = prepStmt.executeQuery();
            while(rs.next()){
                p = new OwnedPokemon(rs.getInt("ID"), rs.getString("DISPLAYNAME"), rs.getString("TYPE_1"),
                        rs.getString("TYPE_2"), rs.getString("HIDDENABILITY"), rs.getInt("HP"), rs.getInt("ATTACK"),
                        rs.getInt("DEFENSE"), rs.getInt("SPEED"), rs.getInt("SPATTACK"), rs.getInt("SPDEFENSE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return p;
    }

    /**
     * Ez a függvény fogja lezárni az adatbázis kapcsolatokat.
     * */
    private void close() {
        try {
            if (this.rs != null) {
                this.rs.close();
            }
            if (this.prepStmt != null) {
                this.prepStmt.close();
            }
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
