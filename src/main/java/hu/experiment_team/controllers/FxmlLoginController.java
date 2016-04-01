package hu.experiment_team.controllers;

import hu.experiment_team.UserMethods;
import hu.experiment_team.models.Trainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FxmlLoginController {

    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Text actiontarget;

    @FXML
    public void handleSigninButtonAction(ActionEvent actionEvent) {
        if(UserMethods.INSTANCE.login(usernameField.getText(), passwordField.getText()) != null)
            actiontarget.setText("Sikeres bejelentkezés!");
        else
            actiontarget.setText("Sikertelen bejelentkezés!");
    }

}
