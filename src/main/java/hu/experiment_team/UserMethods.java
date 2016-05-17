package hu.experiment_team;

import hu.experiment_team.dao.PokemonDAO;
import hu.experiment_team.dao.PokemonDAO;
import hu.experiment_team.models.Trainer;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class do the registration and login.
 * @author Jakab �d�m
 * */
public enum UserMethods {
    /**
     * Instance of the singleton class.
     * */
    INSTANCE;

    /**
     * This method registers the user.
     * @param rUsername Entered username of the user
     * @param displayName Entered display name of the user
     * @param rPassword First entered password of the user
     * @param rPassword2 Second entered password of the user
     * @param rEmail Entered email of the user
     * @return Errors as a String list
     * */
    public List<String> register(String rUsername, String displayName, String rPassword, String rPassword2,String rEmail) {
        List<String> errors = new ArrayList<>();

        if(!Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(rEmail).matches()){ errors.add("Ez az email-cim nem valodi."); }
        if(PokemonDAO.INSTANCE.selectTrainerByEmail(rEmail) != null){ errors.add("Mar van felhasznalo ilyen e-mail cimmel."); }
        if(PokemonDAO.INSTANCE.selectTrainerByName(rUsername) != null){ errors.add("Mar van felhasznalo ilyen nevvel."); }
        if(!rPassword.equals(rPassword2)){ errors.add("A ket beirt jelszo nem egyezik meg."); }
        if(rUsername.length() < 2){ errors.add("A felhasznalonev tol rovid."); }
        if(rUsername.length() > 32){ errors.add("A felhasznalonev tol hosszu."); }
        if(rPassword.length() < 3){ errors.add("A jelszo tul rovid."); }
        if(rPassword.length() > 32){ errors.add("A jelszo tul hosszu."); }
        if(errors.isEmpty()){
            Trainer t = new Trainer();
            t.setUsername(rUsername);
            t.setPassword(DigestUtils.sha1Hex(rUsername + rPassword));
            t.setDisplayName(displayName);
            t.setEmail(rEmail);
            t.setMatchLoose(0);
            t.setMatchWin(0);
            PokemonDAO.INSTANCE.insertTrainer(t);
            errors.add("Sikeres regisztracio!");
            return errors;
        } else {
            return errors;
        }
    }

    /**
     * Log in the user.
     * @param lUsername Username of the user
     * @param lPassword Password of the user
     * @return The user as Trainer object
     * */
    public Trainer login (String lUsername, String lPassword){
        Trainer user = PokemonDAO.INSTANCE.selectTrainerByPassword(DigestUtils.sha1Hex(lUsername + lPassword));
        if(user != null)
            user.setOnline(1);
        return user;
    }

    public void logout(Trainer t){
        t.setOnline(0);
    }

}
