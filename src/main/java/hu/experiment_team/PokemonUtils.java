package hu.experiment_team;

import javafx.scene.image.ImageView;

/**
 * Created by Jakab on 2016.04.03..
 */
public enum PokemonUtils {
    INSTANCE;

    public ImageView getPokemonImageView(int id){

        ImageView pokemonImage;

        if(id < 10)
            pokemonImage = new ImageView("/images/battlers/00" + String.valueOf(id) + ".gif");
        else if (id < 100)
            pokemonImage = new ImageView("/images/battlers/0" + String.valueOf(id) + ".gif");
        else
            pokemonImage = new ImageView("/images/battlers/" + String.valueOf(id) + ".gif");

        pokemonImage.setPreserveRatio(true);
        pokemonImage.setFitHeight(80);

        return pokemonImage;
    }

}
