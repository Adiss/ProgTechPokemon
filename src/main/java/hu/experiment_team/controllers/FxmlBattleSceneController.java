package hu.experiment_team.controllers;

import com.sun.javafx.css.Size;
import hu.experiment_team.models.OwnedPokemon;
import hu.experiment_team.models.Trainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Jakab on 2016.04.25..
 */
public class FxmlBattleSceneController implements Initializable {

    @FXML
    public AnchorPane top_battle_scene_holder;

    private Trainer trainer;
    private Random r;

    private int rand;

    public FxmlBattleSceneController(Trainer t) {
        this.trainer = t;
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

    }

}
