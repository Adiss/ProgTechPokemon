package hu.experiment_team.controllers;

import com.cathive.fx.gravatar.FileTypeExtension;
import com.cathive.fx.gravatar.GravatarImageView;
import hu.experiment_team.models.Trainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jakab on 2016.04.02..
 */
public class FxmlAccountPanelController implements Initializable {

    @FXML
    public GravatarImageView gravatarImageView;
    @FXML
    public Text userDisplayName;
    @FXML
    public Text userWinCounter;
    @FXML
    public Text userLooseCounter;
    @FXML
    public GridPane ownedPokemonHolderGridPane;

    public Trainer user;

    public FxmlAccountPanelController(Trainer user){
        this.user = user;
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        // Initialize avatar
        gravatarImageView.setEmail(user.getEmail());
        gravatarImageView.setFileTypeExtension(FileTypeExtension.PNG);
        gravatarImageView.setSize(80);
        gravatarImageView.update();

        // Initialize user stats
        userDisplayName.setText(user.getDisplayName());
        userWinCounter.setText(String.valueOf(user.getMatchWin()));
        userLooseCounter.setText(String.valueOf(user.getMatchLoose()));

        // Initialize Owned Pokemons
        int column = 0;
        int row = 0;
        for(int i = 0; i < user.getOwnedPokemons().size(); i++){
            if(column == 5){
                column = 0;
                row++;
            }
            ownedPokemonHolderGridPane.add(new Text(user.getOwnedPokemons().get(i).getDisplayName()), column, row);
            column++;
        }

    }

}
