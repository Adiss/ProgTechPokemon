package hu.experiment_team.controllers;

import com.cathive.fx.gravatar.FileTypeExtension;
import com.cathive.fx.gravatar.GravatarImageView;
import hu.experiment_team.PokemonUtils;
import hu.experiment_team.models.Trainer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    public Button startButton;

    public Trainer user;
    private IntegerProperty partyPokemonCounter;
    final ChangeListener changeListener = (observableValue, oldValue, newValue) -> {
        if((int)newValue >= 6){
            startButton.setDisable(false);
        }
    };

    public FxmlAccountPanelController(Trainer user){
        this.user = user;
        this.partyPokemonCounter = new SimpleIntegerProperty(0);
        this.partyPokemonCounter.addListener(changeListener);
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

        //Initialize start button
        startButton.setDisable(true);
        startButton.setOnAction(event -> {
            Stage stage;
            Parent root;

            stage = (Stage)startButton.getScene().getWindow();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/battle_scene.fxml"));
                FxmlBattleSceneController controller = new FxmlBattleSceneController(user);
                loader.setController(controller);
                root = loader.load();
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ownedPokemonHolderTilePane.setVgap(15);
        ownedPokemonHolderTilePane.setHgap(15);
        ownedPokemonHolderTilePane.setPrefTileWidth(90);
        ownedPokemonHolderTilePane.setPrefTileHeight(90);

        // Initialize partyPokemonHolders:
        partyPokemon1Holder.setPrefHeight(100);

        // PartyPokemon1 drag and drop implementáció:
        partyPokemon1Image.setImage(new Image("/images/battlers/egg.gif"));
        partyPokemon1Image.setOnDragOver(event -> {
            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != partyPokemon1Image &&
                    event.getDragboard().hasImage()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        partyPokemon1Image.setOnDragEntered(event -> {
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != partyPokemon1Image &&
                    event.getDragboard().hasImage()) {
                partyPokemon1Name.setFill(Color.RED);
            }
            event.consume();
        });

        partyPokemon1Image.setOnDragExited(event -> {
            /* mouse moved away, remove the graphical cues */
            partyPokemon1Name.setFill(Color.BLACK);
            event.consume();
        });

        partyPokemon1Image.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage() &&
                    !partyPokemon2Image.getImage().equals(db.getImage()) &&
                    !partyPokemon3Image.getImage().equals(db.getImage()) &&
                    !partyPokemon4Image.getImage().equals(db.getImage()) &&
                    !partyPokemon5Image.getImage().equals(db.getImage()) &&
                    !partyPokemon6Image.getImage().equals(db.getImage())
                    ) {
                partyPokemon1Image.setImage(db.getImage());
                partyPokemon1Image.setPreserveRatio(true);
                partyPokemon1Image.setFitHeight(80);
                success = true;
                partyPokemonCounter.set(partyPokemonCounter.getValue()+1);
                user.addPartyPokemon(user.getOwnedPokemons().get(Integer.parseInt(db.getString())));
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // PartyPokemon2 DnD Implementáció
        partyPokemon2Image.setImage(new Image("/images/battlers/egg.gif"));
        partyPokemon2Image.setOnDragOver(event -> {
            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != partyPokemon2Image &&
                    event.getDragboard().hasImage()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        partyPokemon2Image.setOnDragEntered(event -> {
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != partyPokemon2Image &&
                    event.getDragboard().hasImage()) {
                partyPokemon2Name.setFill(Color.RED);
            }
            event.consume();
        });

        partyPokemon2Image.setOnDragExited(event -> {
            /* mouse moved away, remove the graphical cues */
            partyPokemon2Name.setFill(Color.BLACK);
            event.consume();
        });

        partyPokemon2Image.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage() &&
                    !partyPokemon1Image.getImage().equals(db.getImage()) &&
                    !partyPokemon3Image.getImage().equals(db.getImage()) &&
                    !partyPokemon4Image.getImage().equals(db.getImage()) &&
                    !partyPokemon5Image.getImage().equals(db.getImage()) &&
                    !partyPokemon6Image.getImage().equals(db.getImage())
                    ) {
                partyPokemon2Image.setImage(db.getImage());
                partyPokemon2Image.setPreserveRatio(true);
                partyPokemon2Image.setFitHeight(80);
                success = true;
                partyPokemonCounter.set(partyPokemonCounter.getValue()+1);
                user.addPartyPokemon(user.getOwnedPokemons().get(Integer.parseInt(db.getString())));
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // PP3 DND
        partyPokemon3Image.setImage(new Image("/images/battlers/egg.gif"));
        partyPokemon3Image.setOnDragOver(event -> {
            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != partyPokemon3Image &&
                    event.getDragboard().hasImage()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        partyPokemon3Image.setOnDragEntered(event -> {
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != partyPokemon3Image &&
                    event.getDragboard().hasImage()) {
                partyPokemon3Name.setFill(Color.RED);
            }
            event.consume();
        });

        partyPokemon3Image.setOnDragExited(event -> {
            /* mouse moved away, remove the graphical cues */
            partyPokemon3Name.setFill(Color.BLACK);
            event.consume();
        });

        partyPokemon3Image.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage() &&
                    !partyPokemon1Image.getImage().equals(db.getImage()) &&
                    !partyPokemon2Image.getImage().equals(db.getImage()) &&
                    !partyPokemon4Image.getImage().equals(db.getImage()) &&
                    !partyPokemon5Image.getImage().equals(db.getImage()) &&
                    !partyPokemon6Image.getImage().equals(db.getImage())
                    ) {
                partyPokemon3Image.setImage(db.getImage());
                partyPokemon3Image.setPreserveRatio(true);
                partyPokemon3Image.setFitHeight(80);
                success = true;
                partyPokemonCounter.set(partyPokemonCounter.getValue()+1);
                user.addPartyPokemon(user.getOwnedPokemons().get(Integer.parseInt(db.getString())));
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // PP4 DND
        partyPokemon4Image.setImage(new Image("/images/battlers/egg.gif"));
        partyPokemon4Image.setOnDragOver(event -> {
            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != partyPokemon4Image &&
                    event.getDragboard().hasImage()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        partyPokemon4Image.setOnDragEntered(event -> {
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != partyPokemon4Image &&
                    event.getDragboard().hasImage()) {
                partyPokemon4Name.setFill(Color.RED);
            }
            event.consume();
        });

        partyPokemon4Image.setOnDragExited(event -> {
            /* mouse moved away, remove the graphical cues */
            partyPokemon4Name.setFill(Color.BLACK);
            event.consume();
        });

        partyPokemon4Image.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage() &&
                    !partyPokemon1Image.getImage().equals(db.getImage()) &&
                    !partyPokemon2Image.getImage().equals(db.getImage()) &&
                    !partyPokemon3Image.getImage().equals(db.getImage()) &&
                    !partyPokemon5Image.getImage().equals(db.getImage()) &&
                    !partyPokemon6Image.getImage().equals(db.getImage())
                    ) {
                partyPokemon4Image.setImage(db.getImage());
                partyPokemon4Image.setPreserveRatio(true);
                partyPokemon4Image.setFitHeight(80);
                success = true;
                partyPokemonCounter.set(partyPokemonCounter.getValue()+1);
                user.addPartyPokemon(user.getOwnedPokemons().get(Integer.parseInt(db.getString())));
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // PP5 DnD
        partyPokemon5Image.setImage(new Image("/images/battlers/egg.gif"));
        partyPokemon5Image.setOnDragOver(event -> {
            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != partyPokemon5Image &&
                    event.getDragboard().hasImage()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        partyPokemon5Image.setOnDragEntered(event -> {
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != partyPokemon5Image &&
                    event.getDragboard().hasImage()) {
                partyPokemon5Name.setFill(Color.RED);
            }
            event.consume();
        });

        partyPokemon5Image.setOnDragExited(event -> {
            /* mouse moved away, remove the graphical cues */
            partyPokemon5Name.setFill(Color.BLACK);
            event.consume();
        });

        partyPokemon5Image.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage() &&
                    !partyPokemon1Image.getImage().equals(db.getImage()) &&
                    !partyPokemon2Image.getImage().equals(db.getImage()) &&
                    !partyPokemon3Image.getImage().equals(db.getImage()) &&
                    !partyPokemon4Image.getImage().equals(db.getImage()) &&
                    !partyPokemon6Image.getImage().equals(db.getImage())
                    ) {
                partyPokemon5Image.setImage(db.getImage());
                partyPokemon5Image.setPreserveRatio(true);
                partyPokemon5Image.setFitHeight(80);
                success = true;
                partyPokemonCounter.set(partyPokemonCounter.getValue()+1);
                user.addPartyPokemon(user.getOwnedPokemons().get(Integer.parseInt(db.getString())));
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // PP6 DnD
        partyPokemon6Image.setImage(new Image("/images/battlers/egg.gif"));
        partyPokemon6Image.setOnDragOver(event -> {
            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != partyPokemon6Image &&
                    event.getDragboard().hasImage()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        partyPokemon6Image.setOnDragEntered(event -> {
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != partyPokemon6Image &&
                    event.getDragboard().hasImage()) {
                partyPokemon6Name.setFill(Color.RED);
            }
            event.consume();
        });

        partyPokemon6Image.setOnDragExited(event -> {
            /* mouse moved away, remove the graphical cues */
            partyPokemon6Name.setFill(Color.BLACK);
            event.consume();
        });

        partyPokemon6Image.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage() &&
                    !partyPokemon1Image.getImage().equals(db.getImage()) &&
                    !partyPokemon2Image.getImage().equals(db.getImage()) &&
                    !partyPokemon3Image.getImage().equals(db.getImage()) &&
                    !partyPokemon4Image.getImage().equals(db.getImage()) &&
                    !partyPokemon5Image.getImage().equals(db.getImage())
                    ) {
                partyPokemon6Image.setImage(db.getImage());
                partyPokemon6Image.setPreserveRatio(true);
                partyPokemon6Image.setFitHeight(80);
                success = true;
                partyPokemonCounter.set(partyPokemonCounter.getValue()+1);
                user.addPartyPokemon(user.getOwnedPokemons().get(Integer.parseInt(db.getString())));
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // Initialize Owned Pokemons
        for(int i = 0; i < user.getOwnedPokemons().size(); i++){
            // Grid, that holds the pokemon and his name
            GridPane pokeHolder = new GridPane();
            // The pokemons name centered
            Text pokeName = new Text(user.getOwnedPokemons().get(i).getDisplayName());
            pokeName.setTextAlignment(TextAlignment.CENTER);
            // Pokemons image
            ImageView pokImage = PokemonUtils.INSTANCE.getPokemonImageView(user.getOwnedPokemons().get(i).getId());
            // Add them to the grid
            pokeHolder.add(pokImage, 0, 0);
            pokeHolder.add(pokeName, 0, 1);

            // PP DnD Event detected
            final int finalI = i;
            pokeHolder.setOnDragDetected(event -> {
                Dragboard db = pokImage.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(pokImage.getImage());
                content.putString(String.valueOf(finalI));
                db.setContent(content);
                event.consume();
            });
            // Display grid
            ownedPokemonHolderTilePane.getChildren().add(pokeHolder);
        }

    }

}
