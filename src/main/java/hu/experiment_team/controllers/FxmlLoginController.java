package hu.experiment_team.controllers;

import com.cathive.fx.gravatar.GravatarImageView;
import com.sun.deploy.util.FXLoader;
import hu.experiment_team.UserMethods;
import hu.experiment_team.models.Trainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlLoginController {

    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Text actiontarget;
    @FXML
    public Button submitButton;

    @FXML
    public void handleSigninButtonAction(ActionEvent actionEvent) {

        Stage stage;
        Parent root;
        Trainer user;

        if((user = UserMethods.INSTANCE.login(usernameField.getText(), passwordField.getText())) != null) {
            stage = (Stage)submitButton.getScene().getWindow();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/account_panel.fxml"));
                FxmlAccountPanelController controller = new FxmlAccountPanelController(user);
                loader.setController(controller);
                root = loader.load();
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            actiontarget.setText("Sikertelen bejelentkezés!");
            submitButton.setDisable(false);
        }
    }

}
