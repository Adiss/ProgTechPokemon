package hu.experiment_team.controllers;

import hu.experiment_team.PokemonUtils;
import hu.experiment_team.dao.PokemonDao;
import hu.experiment_team.models.OwnedPokemon;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Jakab on 2016.04.25..
 */
public class FxmlBattleSceneController implements Initializable {

    @FXML
    public AnchorPane top_battle_scene_holder;
    @FXML
    public GridPane OpponentPokemonStatusPanel;
    @FXML
    public Text OpponentPokemonName;
    @FXML
    public ProgressBar OpponentPokemonHpProgressBar;
    @FXML
    public Text OpponentPokemonHpText;
    @FXML
    public GridPane MyPokemonStatusPanel;
    @FXML
    public Text MyPokemonName;
    @FXML
    public ProgressBar MyPokemonHpProgressBar;
    @FXML
    public Text MyPokemonHpText;
    @FXML
    public ImageView myPokemonImage;
    @FXML
    public ImageView opponentPokemonImage;


    private Trainer trainer;
    private OwnedPokemon opponent;
    private Random r;

    private int rand;

    public FxmlBattleSceneController(Trainer t) {
        this.trainer = t;
        this.opponent = PokemonDao.INSTANCE.getRandomPokemon(t.getPartyPokemons().get(0).getLevel());
        this.r = new Random();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set battle background
        rand = r.nextInt(14)+1;
        top_battle_scene_holder.backgroundProperty().set(
                new Background(
                        new BackgroundImage(
                                new Image("/images/battle_bgs/battle_bg_" + rand + ".png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
                        )
                )
        );

        myPokemonImage.setImage(PokemonUtils.INSTANCE.getPokemonBackImage(trainer.getPartyPokemons().get(0).getId()));
        AnchorPane.setLeftAnchor(myPokemonImage, 0.0);
        AnchorPane.setBottomAnchor(myPokemonImage, 0.0);

        opponentPokemonImage.setImage(PokemonUtils.INSTANCE.getPokemonImage(opponent.getId()));
        AnchorPane.setRightAnchor(opponentPokemonImage, 60.0);
        AnchorPane.setTopAnchor(opponentPokemonImage, 150.0);

        AnchorPane.setTopAnchor(OpponentPokemonStatusPanel, 50.0);
        AnchorPane.setLeftAnchor(OpponentPokemonStatusPanel, 50.0);

        AnchorPane.setBottomAnchor(MyPokemonStatusPanel, 40.0);
        AnchorPane.setRightAnchor(MyPokemonStatusPanel, 40.0);

        MyPokemonName.setText(trainer.getPartyPokemons().get(0).getDisplayName());
        MyPokemonHpText.setText(String.valueOf(trainer.getPartyPokemons().get(0).getStats().hp));

        OpponentPokemonName.setText(opponent.getDisplayName());
        OpponentPokemonHpText.setText(String.valueOf(opponent.getStats().hp));

    }

}
