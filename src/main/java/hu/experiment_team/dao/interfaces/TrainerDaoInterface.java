package hu.experiment_team.dao.interfaces;

import hu.experiment_team.models.Trainer;

/**
 * A trainerekkel (felhasználókkal) kapcsolatos adatbázis műveleteket tartalmazó osztály interfésze.
 * @author Jakab Ádám
 * */
public interface TrainerDaoInterface {
    /**
     * Hozzáad egy trainert (felhasználót) az adatbázishoz.
     * @param trainer A trainer osztály egy példánya a megfelelő mezőkkel feltöltve.
     * */
    void insert(Trainer trainer);
    /**
     * Kiválaszt egy trainert (felhasználót) a neve alapján.
     * @param username A trainer felhasználó neve.
     * @return A trainer objektumát adja vissza
     * */
    Trainer selectByName(String username);
    /**
     * Kiválaszt egy trainert (felhasználót) a jelszava alapján.
     * Ennek a jelszónak már az SHA1 kódolt jelszónak kell lennie.
     * @param password SHA1 kódolt jelszó.
     * @return A trainer objektumát adja vissza
     * */
    Trainer selectByPassword(String password);
    /**
     * Kiválaszt egy trainert (felhasználót) az e-mail címe alapján.
     * @param email A trainer email címe.
     * @return A trainer objektumát adja vissza
     * */
    Trainer selectByEmail(String email);
}
