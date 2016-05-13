package hu.experiment_team.controllers;

import hu.experiment_team.PokemonUtils;
import hu.experiment_team.dao.PokemonDAO;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    @FXML
    public Text battle_textfield;


    private Trainer trainer;
    private Trainer opponent;
    private int opponentsCurrentPokemon;
    private Random r;
    private Image opponentTrainerImage;

    private ChangeListener OpponentPokemonHpchangeListener = (observableValue, oldValue, newValue) -> {
        double oldV = opponent.getPartyPokemons().get(0).getClonedOne().getHp();
        double newV = (int)newValue;
        OpponentPokemonHpProgressBar.progressProperty().set(newV / oldV);

        if(newV <= 0){
            changeOpponentsPokemon(1);
        }

    };

    FxmlBattleSceneController(Trainer t) {
        this.trainer = t;
        this.opponent = PokemonDAO.INSTANCE.createRandomTrainer(t.getPartyPokemons().get(0).getLevel());
        opponent.getPartyPokemons().get(0).gethpProperty().addListener(OpponentPokemonHpchangeListener);
        opponentTrainerImage = PokemonUtils.INSTANCE.getRandomTrainerImage();
        opponentsCurrentPokemon = 0;
        this.r = new Random();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Load the fonts.
        Font.loadFont(getClass().getResourceAsStream("/fonts/pkmndp.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/pkmndpb.ttf"), 14);

        // Sets battle scene background
        top_battle_scene_holder.backgroundProperty().set(
                new Background(
                        new BackgroundImage(
                                new Image("/images/battle_bgs/battle_bg_" + (r.nextInt(14)+1) + ".png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
                        )
                )
        );

        // Set the position of my pokemon and its image.
        myPokemonImage.setImage(PokemonUtils.INSTANCE.getPokemonBackImage(trainer.getPartyPokemons().get(0).getId()));
        AnchorPane.setLeftAnchor(myPokemonImage, 0.0);
        AnchorPane.setBottomAnchor(myPokemonImage, 0.0);

        // Sets my opponent position and its image.
        opponentPokemonImage.setImage(opponentTrainerImage);
        AnchorPane.setRightAnchor(opponentPokemonImage, 60.0);
        AnchorPane.setTopAnchor(opponentPokemonImage, 150.0);
        changeOpponentsPokemon(0);

        // Sets opponent pokemons status panel position.
        AnchorPane.setTopAnchor(OpponentPokemonStatusPanel, 50.0);
        AnchorPane.setLeftAnchor(OpponentPokemonStatusPanel, 50.0);

        // Sets my pokemons status panel position.
        AnchorPane.setBottomAnchor(MyPokemonStatusPanel, 40.0);
        AnchorPane.setRightAnchor(MyPokemonStatusPanel, 40.0);

        // Sets my pokemons display name and hp text.
        MyPokemonName.setText(trainer.getPartyPokemons().get(0).getDisplayName());
        AnchorPane.setTopAnchor(MyPokemonName, 15.0);
        AnchorPane.setLeftAnchor(MyPokemonName, 10.0);
        MyPokemonHpText.setText(String.valueOf(trainer.getPartyPokemons().get(0).getHp()));
        AnchorPane.setTopAnchor(MyPokemonHpText, 50.0);
        AnchorPane.setLeftAnchor(MyPokemonHpText, 60.0);

        // Sets the opponent pokemons display name and hp text.
        OpponentPokemonName.setText(opponent.getPartyPokemons().get(0).getDisplayName());
        AnchorPane.setTopAnchor(OpponentPokemonName, 15.0);
        AnchorPane.setLeftAnchor(OpponentPokemonName, 10.0);
        OpponentPokemonHpText.setText(String.valueOf(opponent.getPartyPokemons().get(0).getHp()));
        AnchorPane.setTopAnchor(OpponentPokemonHpText, 50.0);
        AnchorPane.setLeftAnchor(OpponentPokemonHpText, 60.0);

        // Sets the starter value to the hp bar and sets its position.
        OpponentPokemonHpProgressBar.progressProperty().setValue(1);
        MyPokemonHpProgressBar.progressProperty().setValue(1);
        AnchorPane.setTopAnchor(OpponentPokemonHpProgressBar, 39.0);
        AnchorPane.setLeftAnchor(OpponentPokemonHpProgressBar, 1.0);
        AnchorPane.setTopAnchor(MyPokemonHpProgressBar, 39.0);
        AnchorPane.setLeftAnchor(MyPokemonHpProgressBar, 1.0);

        // Attach the pokemons abilities to the buttons and make it works via onAction listener
        // Move 1.
        if(trainer.getPartyPokemons().get(0).getMove1() != null) {
            Move1Button.setText(trainer.getPartyPokemons().get(0).getMove1().getDisplayName());
            Move1Button.setOnAction(event -> {
                opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getOwnedPokemons().get(0), trainer.getOwnedPokemons().get(0).getMove1());
                battle_textfield.setText(trainer.getPartyPokemons().get(0).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(0).getMove1().getDisplayName().toUpperCase() + "!");
            });
        }
        // Move 2.
        if(trainer.getPartyPokemons().get(0).getMove2() != null) {
            Move2Button.setText(trainer.getPartyPokemons().get(0).getMove2().getDisplayName());
            Move2Button.setOnAction(event -> {
                opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getOwnedPokemons().get(0), trainer.getOwnedPokemons().get(0).getMove2());
                battle_textfield.setText(trainer.getPartyPokemons().get(0).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(0).getMove2().getDisplayName().toUpperCase() + "!");
            });
        }
        // Move 3.
        if(trainer.getPartyPokemons().get(0).getMove3() != null) {
            Move3Button.setText(trainer.getPartyPokemons().get(0).getMove3().getDisplayName());
            Move3Button.setOnAction(event -> {
                opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getOwnedPokemons().get(0), trainer.getOwnedPokemons().get(0).getMove3());
                battle_textfield.setText(trainer.getPartyPokemons().get(0).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(0).getMove3().getDisplayName().toUpperCase() + "!");
            });
        }
        // Move 4.
        if(trainer.getPartyPokemons().get(0).getMove4() != null) {
            Move4Button.setText(trainer.getPartyPokemons().get(0).getMove4().getDisplayName());
            Move4Button.setOnAction(event -> {
                opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getOwnedPokemons().get(0), trainer.getOwnedPokemons().get(0).getMove4());
                battle_textfield.setText(trainer.getPartyPokemons().get(0).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(0).getMove4().getDisplayName().toUpperCase() + "!");
            });
        }


        battle_textfield.setText(opponent.getDisplayName().toUpperCase() + " has challenged you to a battle with " + opponent.getPartyPokemons().get(0).getDisplayName().toUpperCase() + "! Use your " + trainer.getPartyPokemons().get(0).getDisplayName().toUpperCase() + "'s abilities to win!");

    }

    private void changeOpponentsPokemon(int partyNumber){

        System.out.println(opponentsCurrentPokemon);

        if(opponentsCurrentPokemon == 5) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText("You have won the match!");
            alert.setContentText("");
            alert.show();

        } else {

            battle_textfield.setText(opponent.getDisplayName().toUpperCase() + " sent out " + opponent.getPartyPokemons().get(opponentsCurrentPokemon).getDisplayName().toUpperCase() + "!");

            opponent.getPartyPokemons().get(opponentsCurrentPokemon).gethpProperty().removeListener(OpponentPokemonHpchangeListener);
            if(partyNumber > 0) {
                opponentsCurrentPokemon++;
            }
            opponent.getPartyPokemons().get(opponentsCurrentPokemon).gethpProperty().addListener(OpponentPokemonHpchangeListener);
            OpponentPokemonHpProgressBar.progressProperty().setValue(1);
            OpponentPokemonName.setText(opponent.getPartyPokemons().get(opponentsCurrentPokemon).getDisplayName());
            OpponentPokemonHpText.setText(String.valueOf(opponent.getPartyPokemons().get(opponentsCurrentPokemon).getHp()));


            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };

            Move1Button.setDisable(true);
            Move2Button.setDisable(true);
            Move3Button.setDisable(true);
            Move4Button.setDisable(true);

            KeyFrame keyFrame1On = new KeyFrame(Duration.seconds(0), new KeyValue(opponentPokemonImage.imageProperty(), opponentPokemonImage.getImage()));
            KeyFrame startFadeOut = new KeyFrame(Duration.seconds(0.2), new KeyValue(opponentPokemonImage.opacityProperty(), 1.0));
            KeyFrame endFadeOut = new KeyFrame(Duration.seconds(2), new KeyValue(opponentPokemonImage.opacityProperty(), 0.0));
            KeyFrame keyFrame2On = new KeyFrame(Duration.seconds(2.2), new KeyValue(opponentPokemonImage.imageProperty(), PokemonUtils.INSTANCE.getPokemonImage(opponent.getPartyPokemons().get(opponentsCurrentPokemon).getId())));
            KeyFrame endFadeIn = new KeyFrame(Duration.seconds(4), new KeyValue(opponentPokemonImage.opacityProperty(), 1.0));
            Timeline timelineOn = new Timeline(keyFrame1On, startFadeOut, endFadeOut, keyFrame2On, endFadeIn);
            timelineOn.play();

            sleeper.setOnSucceeded(event -> {
                Move1Button.setDisable(false);
                Move2Button.setDisable(false);
                Move3Button.setDisable(false);
                Move4Button.setDisable(false);
            });
            new Thread(sleeper).start();

        }

    }

}
