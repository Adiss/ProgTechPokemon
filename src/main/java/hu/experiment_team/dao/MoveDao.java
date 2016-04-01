package hu.experiment_team.dao;

import hu.experiment_team.dao.interfaces.MoveDaoInterface;
import hu.experiment_team.models.Move;
import hu.experiment_team.models.OwnedPokemon;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * Ez az osztály fogja kezelni a képességekkel kapcsolatos adatbázis műveleteket.
 * Singleton osztály, nem kell példányosítani, az INSTANCE-on keresztül használható.
 * @author Jakab Ádám
 * */
public enum MoveDao implements MoveDaoInterface {

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
     * A megadott ID alapján lekérdez egy teljes képességet az adatbázisból.
     * @param moveId A képesség adatbázis beli ID-je
     * */
    @Override
    public Move getMoveById(int moveId){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Move m = null;
        String selectStatement = "SELECT * FROM POKEMON_MOVES WHERE id = ?";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, moveId);
            rs = prepStmt.executeQuery();
            if(rs.next()) {
                m = new Move.Builder(rs.getInt("id"), rs.getInt("baseDamage"), rs.getString("type"), rs.getString("moveCategory"), rs.getInt("accuracy"), rs.getInt("totalPP"), rs.getInt("additionalEffectChance"))
                        .priority(rs.getInt("priority"))
                        .flags(rs.getString("flags"))
                        .displayName(rs.getString("displayName"))
                        .functionCode(rs.getString("functionCode"))
                        .build();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return m;
    }

    /**
     * A megadott pokemon ID és szint alapján lekérdezi az adott pokemon adott szintjén és alatta tudható spelleket.
     * @param level A pokémon szintje.
     * @param pokemonId A pokémon ID-je
     * */
    @Override
    public List<Integer> getKnownMove(int level, int pokemonId){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> moveIds = new ArrayList<>();
        String selectStatement = "SELECT moveId FROM POKEMON_MOVES_BY_LEVEL WHERE plevel <= ? AND pokemonId = ? AND ROWNUM < 5 ORDER BY plevel DESC";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, level);
            prepStmt.setInt(2, pokemonId);
            rs = prepStmt.executeQuery();
            while(rs.next()) {
                moveIds.add(rs.getInt("moveId"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return moveIds;
    }

    /**
     * Lekérdez az adatbázisból egy random képességet.
     * */
    @Override
    public Move getRandomKnownMove(OwnedPokemon p){
        Random r = new Random();
        int random = r.nextInt(4-1) + 1;
        switch(random){
            case 1:
                if(p.getMoves().size() == 1)
                    return p.getMoves().get(0);
            case 2:
                if(p.getMoves().size() == 2)
                    return p.getMoves().get(1);
            case 3:
                if(p.getMoves().size() == 3)
                    return p.getMoves().get(2);
            case 4:
                if(p.getMoves().size() == 4)
                    return p.getMoves().get(3);
            default:
                return p.getMoves().get(0);
        }
    }

    /**
     * Lekérdezi az adatbázisból az összes spellt egy listába.
     * */
    @Override
    public List<Move> pullMoves(){

        try {
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Move> moves = new ArrayList<>();
        String selectStatement = "SELECT * FROM POKEMON_MOVES ORDER BY ID";
        try{
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            rs = prepStmt.executeQuery();
            while(rs.next()) {
                moves.add(new Move.Builder(rs.getInt("id"), rs.getInt("baseDamage"), rs.getString("type"), rs.getString("moveCategory"), rs.getInt("accuracy"), rs.getInt("totalPP"), rs.getInt("additionalEffectChance"))
                        .priority(rs.getInt("priority"))
                        .flags(rs.getString("flags"))
                        .displayName(rs.getString("displayName"))
                        .functionCode(rs.getString("functionCode"))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return moves;
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
