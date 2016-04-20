package hu.experiment_team.controllers;

import com.cathive.fx.gravatar.FileTypeExtension;
import com.cathive.fx.gravatar.GravatarImageView;
import hu.experiment_team.PokemonUtils;
import hu.experiment_team.models.Trainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
    public TilePane ownedPokemonHolderTilePane;

    @FXML
    public BorderPane partyPokemon1Holder;
    @FXML
    public Text partyPokemon1Name;
    @FXML
    public ImageView partyPokemon1Image;
    @FXML
    public Text partyPokemon2Name;
    @FXML
    public ImageView partyPokemon2Image;
    @FXML
    public Text partyPokemon3Name;
    @FXML
    public ImageView partyPokemon3Image;
    @FXML
    public Text partyPokemon4Name;
    @FXML
    public ImageView partyPokemon4Image;
    @FXML
    public Text partyPokemon5Name;
    @FXML
    public ImageView partyPokemon5Image;
    @FXML
    public Text partyPokemon6Name;
    @FXML
    public ImageView partyPokemon6Image;

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

        ownedPokemonHolderTilePane.setVgap(15);
        ownedPokemonHolderTilePane.setHgap(15);
        ownedPokemonHolderTilePane.setPrefTileWidth(90);
        ownedPokemonHolderTilePane.setPrefTileHeight(90);

        // Initialize partyPokemonHolders:
        partyPokemon1Holder.setPrefHeight(100);

        partyPokemon1Image.setImage(new Image("/images/battlers/question_mark.png"));
        partyPokemon2Image.setImage(new Image("/images/battlers/question_mark.png"));
        partyPokemon3Image.setImage(new Image("/images/battlers/question_mark.png"));
        partyPokemon4Image.setImage(new Image("/images/battlers/question_mark.png"));
        partyPokemon5Image.setImage(new Image("/images/battlers/question_mark.png"));
        partyPokemon6Image.setImage(new Image("/images/battlers/question_mark.png"));

        // Initialize Owned Pokemons
        for(int i = 0; i < user.getOwnedPokemons().size(); i++){
            // Grid, that holds the pokemon and his name
            GridPane pokeHolder = new GridPane();
            // The pokemons name centered
            Text pokeName = new Text(user.getOwnedPokemons().get(i).getDisplayName());
            pokeName.setTextAlignment(TextAlignment.CENTER);
            // Add them to the grid
            pokeHolder.add(PokemonUtils.INSTANCE.getPokemonImageView(user.getOwnedPokemons().get(i).getId()), 0, 0);
            pokeHolder.add(pokeName, 0, 1);
            //
            final int finalI = i;
            pokeHolder.setOnMouseClicked(event -> {
                partyPokemon1Image.setImage(PokemonUtils.INSTANCE.getPokemonImage(user.getOwnedPokemons().get(finalI).getId()));
                partyPokemon1Image.setPreserveRatio(true);
                partyPokemon1Image.setFitHeight(80);
                partyPokemon1Name.setText(user.getOwnedPokemons().get(finalI).getDisplayName());
                partyPokemon1Name.setTextAlignment(TextAlignment.CENTER);
            });
            // Display grid
            ownedPokemonHolderTilePane.getChildren().add(pokeHolder);
        }

    }

}
