package hu.experiment_team;

import javafx.scene.image.ImageView;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Jakab on 2016.05.17..
 */
public class PokemonUtilsTest {

    @Rule
    public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();

    @Test
    public void testGetPokemonBackImage(){
        assertTrue(PokemonUtils.INSTANCE.getPokemonBackImage(1) != null);
    }

    @Test
    public void testGetPokemonImage(){
        assertTrue(PokemonUtils.INSTANCE.getPokemonImage(1) != null);
    }

    @Test
    public void testGetRandomTrainerImage(){
        assertTrue(PokemonUtils.INSTANCE.getRandomTrainerImage() != null);
    }

    @Test
    public void testGetPokemonImageView(){
        ImageView poki = PokemonUtils.INSTANCE.getPokemonImageView(1);
        assertTrue(poki != null);
    }

}
