package hu.experiment_team.controllers;

import hu.experiment_team.PokemonUtils;
import hu.experiment_team.dao.PokemonDAO;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
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
    public AnchorPane OpponentPokemonStatusPanel;
    @FXML
    public Text OpponentPokemonName;
    @FXML
    public ProgressBar OpponentPokemonHpProgressBar;
    @FXML
    public Text OpponentPokemonHpText;
    @FXML
    public AnchorPane MyPokemonStatusPanel;
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
    @FXML
    public Button Move1Button;
    @FXML
    public Button Move2Button;
    @FXML
    public Button Move3Button;
    @FXML
    public Button Move4Button;


    private Trainer trainer;
    private Pokemon opponent;
    private Random r;

    private int rand;

    private ChangeListener OpponentPokemonHpchangeListener = (observableValue, oldValue, newValue) -> {
        double oldV = opponent.getClonedOne().getHp();
        double newV = (int)newValue;
        OpponentPokemonHpProgressBar.progressProperty().set(newV / oldV);
    };

    public FxmlBattleSceneController(Trainer t) {
        this.trainer = t;
        this.opponent = PokemonDAO.INSTANCE.getRandomPokemon(t.getPartyPokemons().get(0).getLevel());
        opponent.gethpProperty().addListener(OpponentPokemonHpchangeListener);
        this.r = new Random();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getResourceAsStream("/fonts/pkmndp.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/pkmndpb.ttf"), 14);

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
        AnchorPane.setTopAnchor(MyPokemonName, 15.0);
        AnchorPane.setLeftAnchor(MyPokemonName, 10.0);
        MyPokemonHpText.setText(String.valueOf(trainer.getPartyPokemons().get(0).getHp()));
        AnchorPane.setTopAnchor(MyPokemonHpText, 50.0);
        AnchorPane.setLeftAnchor(MyPokemonHpText, 60.0);

        OpponentPokemonName.setText(opponent.getDisplayName());
        AnchorPane.setTopAnchor(OpponentPokemonName, 15.0);
        AnchorPane.setLeftAnchor(OpponentPokemonName, 10.0);
        OpponentPokemonHpText.setText(String.valueOf(opponent.getHp()));
        AnchorPane.setTopAnchor(OpponentPokemonHpText, 50.0);
        AnchorPane.setLeftAnchor(OpponentPokemonHpText, 60.0);

        OpponentPokemonHpProgressBar.progressProperty().setValue(1);
        MyPokemonHpProgressBar.progressProperty().setValue(1);
        AnchorPane.setTopAnchor(OpponentPokemonHpProgressBar, 39.0);
        AnchorPane.setLeftAnchor(OpponentPokemonHpProgressBar, 1.0);
        AnchorPane.setTopAnchor(MyPokemonHpProgressBar, 39.0);
        AnchorPane.setLeftAnchor(MyPokemonHpProgressBar, 1.0);

        if(trainer.getPartyPokemons().get(0).getMove1() != null) {
            Move1Button.setText(trainer.getPartyPokemons().get(0).getMove1().getDisplayName());
            Move1Button.setOnAction(event -> {
                opponent.hurt(trainer.getOwnedPokemons().get(0), trainer.getOwnedPokemons().get(0).getMove1());
            });
        }
        if(trainer.getPartyPokemons().get(0).getMove2() != null) {
            Move2Button.setText(trainer.getPartyPokemons().get(0).getMove2().getDisplayName());
            Move2Button.setOnAction(event -> {
                opponent.hurt(trainer.getOwnedPokemons().get(0), trainer.getOwnedPokemons().get(0).getMove2());
            });
        }
        if(trainer.getPartyPokemons().get(0).getMove3() != null) {
            Move3Button.setText(trainer.getPartyPokemons().get(0).getMove3().getDisplayName());
            Move3Button.setOnAction(event -> {
                opponent.hurt(trainer.getOwnedPokemons().get(0), trainer.getOwnedPokemons().get(0).getMove3());
            });
        }
        if(trainer.getPartyPokemons().get(0).getMove4() != null) {
            Move4Button.setText(trainer.getPartyPokemons().get(0).getMove4().getDisplayName());
            Move4Button.setOnAction(event -> {
                opponent.hurt(trainer.getOwnedPokemons().get(0), trainer.getOwnedPokemons().get(0).getMove4());
            });
        }

    }

}
