package hu.experiment_team.controllers;

import hu.experiment_team.UserMethods;
import hu.experiment_team.models.Trainer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Trainer> result = executor.submit(() -> UserMethods.INSTANCE.login(usernameField.getText(), passwordField.getText()));

        Stage stage;
        Parent root;
        Trainer user;

        try {
            if((user = result.get()) != null) {
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
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}
