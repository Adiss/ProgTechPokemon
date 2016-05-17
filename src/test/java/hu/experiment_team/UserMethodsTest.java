/*
package hu.experiment_team;


import hu.experiment_team.dao.PokemonDAO;
import hu.experiment_team.models.Trainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserMethods.class})
public class UserMethodsTest {

    @Before
    public void setUp() throws SQLException {

        mockStatic(UserMethods.class);

        Trainer t = new Trainer();
        t.setId(999);
        t.setUsername("Adiss");
        t.setDisplayName("Adiss");
        t.setPassword("c4a40a1a0d907558e088fca78fd534dbd6912d1a");
        t.setEmail("adiss.b17@gmail.com");

        when(UserMethods.INSTANCE.login("Adiss", "12345")).thenReturn(t);

    }

    @Test
    public void testRegister(){

        Trainer t = new Trainer();
        t.setId(999);
        t.setUsername("Adiss");
        t.setDisplayName("Adiss");
        t.setPassword("824e456e4cfd675348965387a08924fffaf2ae06");
        t.setEmail("adiss.b17@gmail.com");

        assertEquals(UserMethods.INSTANCE.login("Adiss", "12345"), t);

    }


}
*/