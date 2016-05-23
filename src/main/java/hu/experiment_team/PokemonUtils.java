package hu.experiment_team;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Created by Jakab on 2016.04.03..
 */
public enum PokemonUtils {
    INSTANCE;

    public ImageView getPokemonImageView(int id){

        ImageView pokemonImage;

        /*
        if(id < 10)
            pokemonImage = new ImageView("/images/battlers/00" + String.valueOf(id) + ".gif");
        else if (id < 100)
            pokemonImage = new ImageView("/images/battlers/0" + String.valueOf(id) + ".gif");
        else
            pokemonImage = new ImageView("/images/battlers/" + String.valueOf(id) + ".gif");
        */

        pokemonImage = new ImageView("/images/battlers/pokiAdiss.gif");

        pokemonImage.setPreserveRatio(true);
        pokemonImage.setFitHeight(80);

        return pokemonImage;
    }

    public Image getPokemonImage(int id){

        Image pokemonImage;

        /*
        if(id < 10)
            pokemonImage = new Image("/images/battlers/00" + String.valueOf(id) + ".gif");
        else if (id < 100)
            pokemonImage = new Image("/images/battlers/0" + String.valueOf(id) + ".gif");
        else
            pokemonImage = new Image("/images/battlers/" + String.valueOf(id) + ".gif");
        */

        pokemonImage = new Image("/images/battlers/pokiAdiss.gif");


        return pokemonImage;
    }

    public Image getPokemonBackImage(int id){

        Image pokemonImage;

        /*
        if(id < 10)
            pokemonImage = new Image("/images/battlers/00" + String.valueOf(id) + "b.gif");
        else if (id < 100)
            pokemonImage = new Image("/images/battlers/0" + String.valueOf(id) + "b.gif");
        else
            pokemonImage = new Image("/images/battlers/" + String.valueOf(id) + "b.gif");
        */
        pokemonImage = new Image("/images/battlers/pokiAdiss_b.gif");

        return pokemonImage;
    }

    public Image getRandomTrainerImage(){
        Random r = new Random();
        return new Image("/images/trainers/" + r.nextInt(8) + ".png");
    }

}
