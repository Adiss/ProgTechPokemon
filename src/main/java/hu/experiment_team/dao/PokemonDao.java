package hu.experiment_team.dao;

import hu.experiment_team.dao.interfaces.PokemonDaoInterface;
import hu.experiment_team.models.OwnedPokemon;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
                p = new OwnedPokemon(rs.getInt("POKEMONID"), rs.getString("DISPLAYNAME"), rs.getString("TYPE1"),
                        rs.getString("TYPE2"), rs.getString("HIDDENABILITY"), rs.getInt("HP"), rs.getInt("ATTACK"),
                        rs.getInt("DEFENSE"), rs.getInt("SPEED"), rs.getInt("SPATTACK"), rs.getInt("SPDEFENSE"), rs.getInt("pokemonlevel"));
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
     * TODO -> NEM IGEN VANNAK SPELLJEI? SEM ITT SEM A GETOWNEDBEN? ELLENŐRIZNI KELL!
     * */
    @Override
    public OwnedPokemon getRandomPokemon(int level){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> moveIds = null;
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
                moveIds = MoveDao.INSTANCE.getKnownMove(level, rs.getInt("ID"));
                p = new OwnedPokemon(rs.getInt("ID"), rs.getString("DISPLAYNAME"), rs.getString("TYPE1"),
                        rs.getString("TYPE2"), rs.getString("HIDDENABILITY"), rs.getInt("HP"), rs.getInt("ATTACK"),
                        rs.getInt("DEFENSE"), rs.getInt("SPEED"), rs.getInt("SPATTACK"), rs.getInt("SPDEFENSE"), level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return p;
    }

    @Override
    public void addOwnedPokemon(int id, OwnedPokemon p) {

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String insertStatement = "INSERT INTO POKEMON_OWNED_POKEMONS (ownerId, pokemonId, displayName, type1, type2, pokemonlevel, hp, attack, defense, speed, spAttack, spDefense, currentXp, hiddenAbility, move1Id, move2Id, move3Id, move4Id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            List<Integer> moveIds = MoveDao.INSTANCE.getKnownMove(1, p.getId());
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(insertStatement);
            prepStmt.setInt(1, id);
            prepStmt.setInt(2, p.getId());
            prepStmt.setString(3, p.getDisplayName());
            prepStmt.setString(4, p.getType1());
            prepStmt.setString(5, p.getType2());
            prepStmt.setInt(6, 1);
            prepStmt.setInt(7, p.getStats().hp);
            prepStmt.setInt(8, p.getStats().attack);
            prepStmt.setInt(9, p.getStats().defense);
            prepStmt.setInt(10, p.getStats().speed);
            prepStmt.setInt(11, p.getStats().specialAttack);
            prepStmt.setInt(12, p.getStats().specialDefense);
            prepStmt.setInt(13, 0);
            prepStmt.setString(14, p.getHiddenAbility());
            prepStmt.setInt(15, moveIds.get(0));
            if(moveIds.size() >= 2) prepStmt.setInt(16, moveIds.get(1)); else prepStmt.setInt(16, 0);
            if(moveIds.size() >= 3) prepStmt.setInt(17, moveIds.get(2)); else prepStmt.setInt(17, 0);
            if(moveIds.size() == 4) prepStmt.setInt(18, moveIds.get(3)); else prepStmt.setInt(18, 0);
            prepStmt.executeUpdate();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    @Override
    public List<OwnedPokemon> getOwnedPokemons(int id) {
        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<OwnedPokemon> owneds = new ArrayList<>();
        String selectStatement = "SELECT * FROM POKEMON_OWNED_POKEMONS WHERE ownerid = ?";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, id);
            rs = prepStmt.executeQuery();
            while(rs.next()){
                owneds.add(new OwnedPokemon(rs.getInt("POKEMONID"), rs.getString("DISPLAYNAME"), rs.getString("TYPE1"),
                        rs.getString("TYPE2"), rs.getString("HIDDENABILITY"), rs.getInt("HP"), rs.getInt("ATTACK"),
                        rs.getInt("DEFENSE"), rs.getInt("SPEED"), rs.getInt("SPATTACK"), rs.getInt("SPDEFENSE"), rs.getInt("pokemonlevel")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return owneds;
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
