package hu.experiment_team;

import hu.experiment_team.dao.PokemonDao;
import hu.experiment_team.models.OwnedPokemon;
import hu.experiment_team.models.Pokemon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args ) {
        /*
        OwnedPokemon defender = PokemonDao.INSTANCE.getRandomPokemon();
        OwnedPokemon attacker = PokemonDao.INSTANCE.getRandomPokemon();
        System.out.println("DEFENDER: " + defender);
        System.out.println("ATTACKER: " + attacker);
        System.out.println(defender.getStats().hp);
        defender.hurt(attacker, MoveDao.INSTANCE.getRandomKnownMove(attacker));
        System.out.println(attacker.getLastMove());
        System.out.println(defender.getStats().hp);
        */
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pokemon App");
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));

        Scene loginScene = new Scene(root, 400, 300);
        primaryStage.setScene(loginScene);
        primaryStage.show();

    }
}
