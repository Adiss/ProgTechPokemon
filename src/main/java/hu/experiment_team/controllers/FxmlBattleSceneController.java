package hu.experiment_team.controllers;

import hu.experiment_team.PokemonUtils;
import hu.experiment_team.battleAI.BattleAI;
import hu.experiment_team.dao.PokemonDAO;
import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private int myCurrentPokemon;
    private Random r;
    private Image opponentTrainerImage;
    private IntegerProperty turn = new SimpleIntegerProperty();
    private boolean won;

    private ChangeListener OpponentPokemonHpchangeListener = (observableValue, oldValue, newValue) -> {
        double oldV = opponent.getPartyPokemons().get(opponentsCurrentPokemon).getClonedOne().getHp();
        double newV = (int)newValue;
        OpponentPokemonHpProgressBar.progressProperty().set(newV / oldV);

        if(newV <= 0){
            changeOpponentsPokemon(1);
        }

    };

    private ChangeListener myPokemonHpchangeListener = (observableValue, oldValue, newValue) -> {
        double oldV = trainer.getPartyPokemons().get(myCurrentPokemon).getClonedOne().getHp();
        double newV = (int)newValue;
        MyPokemonHpProgressBar.progressProperty().set(newV / oldV);

        if(newV <= 0){
            changeMyPokemon(1);
        }

    };

    private ChangeListener turnListener = (observableValue, oldValue, newValue) -> {
        if((int)newValue % 2 == 0 && !won){
            doOpponentPokemonsAttack();
            System.out.println("Origi: " + trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName());
            System.out.println("Origi: " + trainer.getPartyPokemons().get(myCurrentPokemon).getOriginalHp());
            System.out.println("Clone: " + trainer.getPartyPokemons().get(myCurrentPokemon).getClonedOne().gethpProperty().getValue());
        }
    };

    FxmlBattleSceneController(Trainer t) {
        this.trainer = t;
        myCurrentPokemon = 0;
        opponentsCurrentPokemon = 0;
        trainer.getPartyPokemons().get(myCurrentPokemon).setHpProperty(trainer.getPartyPokemons().get(myCurrentPokemon).getOriginalHp());
        trainer.getPartyPokemons().get(myCurrentPokemon).setHp(trainer.getPartyPokemons().get(myCurrentPokemon).getOriginalHp());
        trainer.getPartyPokemons().get(myCurrentPokemon).gethpProperty().addListener(myPokemonHpchangeListener);
        this.opponent = PokemonDAO.INSTANCE.createRandomTrainer(t.getPartyPokemons().get(opponentsCurrentPokemon).getLevel());
        opponent.getPartyPokemons().get(opponentsCurrentPokemon).gethpProperty().addListener(OpponentPokemonHpchangeListener);
        opponentTrainerImage = PokemonUtils.INSTANCE.getRandomTrainerImage();
        this.r = new Random();
        won = false;

        turn.setValue(1);
        turn.addListener(turnListener);

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
        myPokemonImage.setImage(PokemonUtils.INSTANCE.getPokemonBackImage(trainer.getPartyPokemons().get(myCurrentPokemon).getId()));
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
        MyPokemonName.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName());
        AnchorPane.setTopAnchor(MyPokemonName, 15.0);
        AnchorPane.setLeftAnchor(MyPokemonName, 10.0);
        MyPokemonHpText.setText(String.valueOf(trainer.getPartyPokemons().get(myCurrentPokemon).getHp()));
        AnchorPane.setTopAnchor(MyPokemonHpText, 50.0);
        AnchorPane.setLeftAnchor(MyPokemonHpText, 60.0);

        // Sets the opponent pokemons display name and hp text.
        OpponentPokemonName.setText(opponent.getPartyPokemons().get(opponentsCurrentPokemon).getDisplayName());
        AnchorPane.setTopAnchor(OpponentPokemonName, 15.0);
        AnchorPane.setLeftAnchor(OpponentPokemonName, 10.0);
        OpponentPokemonHpText.setText(String.valueOf(opponent.getPartyPokemons().get(opponentsCurrentPokemon).getHp()));
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
        if(trainer.getPartyPokemons().get(myCurrentPokemon).getMove1() != null) {
            Move1Button.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getMove1().getDisplayName());
            Move1Button.setOnAction(event -> {
                turn.setValue(turn.getValue() + 1);
                opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getPartyPokemons().get(myCurrentPokemon), trainer.getPartyPokemons().get(myCurrentPokemon).getMove1());
                battle_textfield.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(myCurrentPokemon).getMove1().getDisplayName().toUpperCase() + "!");
            });
        } else {
            Move1Button.setText("");
            Move1Button.setDisable(true);
        }
        // Move 2.
        if(trainer.getPartyPokemons().get(myCurrentPokemon).getMove2() != null) {
            Move2Button.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getMove2().getDisplayName());
            Move2Button.setOnAction(event -> {
                turn.setValue(turn.getValue() + 1);
                opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getPartyPokemons().get(myCurrentPokemon), trainer.getPartyPokemons().get(myCurrentPokemon).getMove2());
                battle_textfield.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(myCurrentPokemon).getMove2().getDisplayName().toUpperCase() + "!");
            });
        } else {
            Move2Button.setText("");
            Move2Button.setDisable(true);
        }
        // Move 3.
        if(trainer.getPartyPokemons().get(myCurrentPokemon).getMove3() != null) {
            Move3Button.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getMove3().getDisplayName());
            Move3Button.setOnAction(event -> {
                turn.setValue(turn.getValue() + 1);
                opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getPartyPokemons().get(myCurrentPokemon), trainer.getPartyPokemons().get(myCurrentPokemon).getMove3());
                battle_textfield.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(myCurrentPokemon).getMove3().getDisplayName().toUpperCase() + "!");
            });
        } else {
            Move3Button.setText("");
            Move3Button.setDisable(true);
        }
        // Move 4.
        if(trainer.getPartyPokemons().get(myCurrentPokemon).getMove4() != null) {
            Move4Button.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getMove4().getDisplayName());
            Move4Button.setOnAction(event -> {
                turn.setValue(turn.getValue() + 1);
                opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getPartyPokemons().get(myCurrentPokemon), trainer.getPartyPokemons().get(myCurrentPokemon).getMove4());
                battle_textfield.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(myCurrentPokemon).getMove4().getDisplayName().toUpperCase() + "!");
            });
        } else {
            Move4Button.setText("");
            Move4Button.setDisable(true);
        }


        battle_textfield.setText(opponent.getDisplayName().toUpperCase() + " has challenged you to a battle with " + opponent.getPartyPokemons().get(opponentsCurrentPokemon).getDisplayName().toUpperCase() + "! Use your " + trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + "'s abilities to win!");

    }

    private void doOpponentPokemonsAttack(){

        // Sleeper to delay the pokemon's move
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(2000);
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

        sleeper.setOnSucceeded(event2 -> {
            Move m = BattleAI.INSTANCE.calculateNextMove(opponent.getPartyPokemons().get(opponentsCurrentPokemon), trainer.getPartyPokemons().get(myCurrentPokemon));
            trainer.getPartyPokemons().get(myCurrentPokemon).hurt(opponent.getPartyPokemons().get(opponentsCurrentPokemon), m);
            battle_textfield.setText(opponent.getPartyPokemons().get(opponentsCurrentPokemon).getDisplayName().toUpperCase() + " used " + m.getDisplayName().toUpperCase() + "!");

            Move1Button.setDisable(false);
            Move2Button.setDisable(false);
            Move3Button.setDisable(false);
            Move4Button.setDisable(false);

            turn.setValue(turn.getValue() + 1);

        });
        new Thread(sleeper).start();

    }

    private void changeOpponentsPokemon(int partyNumber) {

        // If the opponents party pokemons fainted you won the match and got the message about it
        if (opponentsCurrentPokemon == 5) {

            won = true;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText("You have won the match!");
            alert.setContentText("");
            alert.setOnCloseRequest(event -> {
                try {
                    trainer.getPartyPokemons().get(0).setHpProperty(trainer.getPartyPokemons().get(0).getOriginalHp());
                    trainer.getPartyPokemons().get(1).setHpProperty(trainer.getPartyPokemons().get(1).getOriginalHp());
                    trainer.getPartyPokemons().get(2).setHpProperty(trainer.getPartyPokemons().get(2).getOriginalHp());
                    trainer.getPartyPokemons().get(3).setHpProperty(trainer.getPartyPokemons().get(3).getOriginalHp());
                    trainer.getPartyPokemons().get(4).setHpProperty(trainer.getPartyPokemons().get(4).getOriginalHp());
                    trainer.getPartyPokemons().get(5).setHpProperty(trainer.getPartyPokemons().get(5).getOriginalHp());

                    trainer.getPartyPokemons().get(0).setHp(trainer.getPartyPokemons().get(0).getOriginalHp());
                    trainer.getPartyPokemons().get(1).setHp(trainer.getPartyPokemons().get(1).getOriginalHp());
                    trainer.getPartyPokemons().get(2).setHp(trainer.getPartyPokemons().get(2).getOriginalHp());
                    trainer.getPartyPokemons().get(3).setHp(trainer.getPartyPokemons().get(3).getOriginalHp());
                    trainer.getPartyPokemons().get(4).setHp(trainer.getPartyPokemons().get(4).getOriginalHp());
                    trainer.getPartyPokemons().get(5).setHp(trainer.getPartyPokemons().get(5).getOriginalHp());

                    trainer.setPartyPokemons(new ArrayList<Pokemon>());
                    Stage stage = (Stage) top_battle_scene_holder.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/account_panel.fxml"));
                    FxmlAccountPanelController controller = new FxmlAccountPanelController(trainer);
                    loader.setController(controller);
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 700, 500);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            alert.show();

        } else {
            opponent.getPartyPokemons().get(opponentsCurrentPokemon).gethpProperty().removeListener(OpponentPokemonHpchangeListener);
            if (partyNumber > 0) {
                opponentsCurrentPokemon++;
            }
            opponent.getPartyPokemons().get(opponentsCurrentPokemon).gethpProperty().addListener(OpponentPokemonHpchangeListener);


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

            KeyFrame keyFrame1On = new KeyFrame(Duration.seconds(1), new KeyValue(opponentPokemonImage.imageProperty(), opponentPokemonImage.getImage()));
            KeyFrame startFadeOut = new KeyFrame(Duration.seconds(1.2), new KeyValue(opponentPokemonImage.opacityProperty(), 1.0));
            KeyFrame endFadeOut = new KeyFrame(Duration.seconds(3), new KeyValue(opponentPokemonImage.opacityProperty(), 0.0));
            KeyFrame keyFrame2On = new KeyFrame(Duration.seconds(3.2), new KeyValue(opponentPokemonImage.imageProperty(), PokemonUtils.INSTANCE.getPokemonImage(opponent.getPartyPokemons().get(opponentsCurrentPokemon).getId())));
            KeyFrame endFadeIn = new KeyFrame(Duration.seconds(5), new KeyValue(opponentPokemonImage.opacityProperty(), 1.0));
            Timeline timelineOn = new Timeline(keyFrame1On, startFadeOut, endFadeOut, keyFrame2On, endFadeIn);
            timelineOn.play();

            sleeper.setOnSucceeded(event -> {
                OpponentPokemonHpProgressBar.progressProperty().setValue(1);
                OpponentPokemonName.setText(opponent.getPartyPokemons().get(opponentsCurrentPokemon).getDisplayName());
                OpponentPokemonHpText.setText(String.valueOf(opponent.getPartyPokemons().get(opponentsCurrentPokemon).gethpProperty().getValue()));
                battle_textfield.setText(opponent.getDisplayName().toUpperCase() + " sent out " + opponent.getPartyPokemons().get(opponentsCurrentPokemon).getDisplayName().toUpperCase() + "!");

                Move1Button.setDisable(false);
                Move2Button.setDisable(false);
                Move3Button.setDisable(false);
                Move4Button.setDisable(false);
            });
            new Thread(sleeper).start();

        }

    }

    private void changeMyPokemon(int partyNumber){

        // If the opponents party pokemons fainted you won the match and got the message about it
        if(myCurrentPokemon == 5) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("What a shame!");
            alert.setHeaderText("You have lost this match!");
            alert.setContentText("");
            alert.setOnCloseRequest(event -> {
                try {
                    trainer.getPartyPokemons().clear();
                    Stage stage = (Stage)top_battle_scene_holder.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/account_panel.fxml"));
                    FxmlAccountPanelController controller = new FxmlAccountPanelController(trainer);
                    loader.setController(controller);
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 700, 500);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            alert.show();

        } else {
            trainer.getPartyPokemons().get(myCurrentPokemon).gethpProperty().removeListener(myPokemonHpchangeListener);
            trainer.getPartyPokemons().get(myCurrentPokemon).gethpProperty().setValue(trainer.getPartyPokemons().get(myCurrentPokemon).getOriginalHp());
            if(partyNumber > 0) {
                myCurrentPokemon++;
            }
            trainer.getPartyPokemons().get(myCurrentPokemon).gethpProperty().addListener(myPokemonHpchangeListener);


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

            KeyFrame keyFrame1On = new KeyFrame(Duration.seconds(1), new KeyValue(myPokemonImage.imageProperty(), myPokemonImage.getImage()));
            KeyFrame startFadeOut = new KeyFrame(Duration.seconds(1.2), new KeyValue(myPokemonImage.opacityProperty(), 1.0));
            KeyFrame endFadeOut = new KeyFrame(Duration.seconds(3), new KeyValue(myPokemonImage.opacityProperty(), 0.0));
            KeyFrame keyFrame2On = new KeyFrame(Duration.seconds(3.2), new KeyValue(myPokemonImage.imageProperty(), PokemonUtils.INSTANCE.getPokemonBackImage(trainer.getPartyPokemons().get(myCurrentPokemon).getId())));
            KeyFrame endFadeIn = new KeyFrame(Duration.seconds(5), new KeyValue(myPokemonImage.opacityProperty(), 1.0));
            Timeline timelineOn = new Timeline(keyFrame1On, startFadeOut, endFadeOut, keyFrame2On, endFadeIn);
            timelineOn.play();

            sleeper.setOnSucceeded(event -> {

                Move1Button.onActionProperty().unbind();
                Move2Button.onActionProperty().unbind();
                Move3Button.onActionProperty().unbind();
                Move4Button.onActionProperty().unbind();

                // Attach the pokemons abilities to the buttons and make it works via onAction listener
                // Move 1.
                if(trainer.getPartyPokemons().get(myCurrentPokemon).getMove1() != null) {
                    Move1Button.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getMove1().getDisplayName());
                    Move1Button.setDisable(false);
                    Move1Button.setOnAction(onEvent -> {
                        turn.setValue(turn.getValue() + 1);
                        opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getPartyPokemons().get(myCurrentPokemon), trainer.getPartyPokemons().get(myCurrentPokemon).getMove1());
                        battle_textfield.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(myCurrentPokemon).getMove1().getDisplayName().toUpperCase() + "!");
                    });
                } else {
                    Move1Button.setText("");
                    Move1Button.setDisable(true);
                }
                // Move 2.
                if(trainer.getPartyPokemons().get(myCurrentPokemon).getMove2() != null) {
                    Move2Button.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getMove2().getDisplayName());
                    Move2Button.setDisable(false);
                    Move2Button.setOnAction(onEvent -> {
                        turn.setValue(turn.getValue() + 1);
                        opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getPartyPokemons().get(myCurrentPokemon), trainer.getPartyPokemons().get(myCurrentPokemon).getMove2());
                        battle_textfield.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(myCurrentPokemon).getMove2().getDisplayName().toUpperCase() + "!");
                    });
                } else {
                    Move2Button.setText("");
                    Move2Button.setDisable(true);
                }
                // Move 3.
                if(trainer.getPartyPokemons().get(myCurrentPokemon).getMove3() != null) {
                    Move3Button.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getMove3().getDisplayName());
                    Move3Button.setDisable(false);
                    Move3Button.setOnAction(onEvent -> {
                        turn.setValue(turn.getValue() + 1);
                        opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getPartyPokemons().get(myCurrentPokemon), trainer.getPartyPokemons().get(myCurrentPokemon).getMove3());
                        battle_textfield.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(myCurrentPokemon).getMove3().getDisplayName().toUpperCase() + "!");
                    });
                } else {
                    Move3Button.setText("");
                    Move3Button.setDisable(true);
                }
                // Move 4.
                if(trainer.getPartyPokemons().get(myCurrentPokemon).getMove4() != null) {
                    Move4Button.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getMove4().getDisplayName());
                    Move4Button.setDisable(false);
                    Move4Button.setOnAction(onEvent -> {
                        turn.setValue(turn.getValue() + 1);
                        opponent.getPartyPokemons().get(opponentsCurrentPokemon).hurt(trainer.getPartyPokemons().get(myCurrentPokemon), trainer.getPartyPokemons().get(myCurrentPokemon).getMove4());
                        battle_textfield.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + " used " + trainer.getPartyPokemons().get(myCurrentPokemon).getMove4().getDisplayName().toUpperCase() + "!");
                    });
                } else {
                    Move4Button.setText("");
                    Move4Button.setDisable(true);
                }

                MyPokemonHpProgressBar.progressProperty().setValue(1);
                MyPokemonName.setText(trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName());
                MyPokemonHpText.setText(String.valueOf(trainer.getPartyPokemons().get(myCurrentPokemon).getHp()));
                battle_textfield.setText(trainer.getDisplayName().toUpperCase() + " sent out " + trainer.getPartyPokemons().get(myCurrentPokemon).getDisplayName().toUpperCase() + "!");

                Move1Button.setDisable(false);
                Move2Button.setDisable(false);
                Move3Button.setDisable(false);
                Move4Button.setDisable(false);
            });
            new Thread(sleeper).start();

        }

    }

}
