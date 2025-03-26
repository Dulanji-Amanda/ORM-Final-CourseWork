package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class OTPVerifiedFormController {

    @FXML
    private JFXButton btnReset;

    @FXML
    private ImageView img;

    @FXML
    private ImageView imgBack;

    @FXML
    private ImageView imgConfirmPasswordView;

    @FXML
    private ImageView imgPasswordView;

    @FXML
    private Label lblError;

    @FXML
    private Label lblLogin;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtConfirmPasswordVisible;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPasswordVisible;

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void imgBackOnAction(MouseEvent event) {

    }

    @FXML
    void imgConfirmPasswordViewOnAction(MouseEvent event) {

    }

    @FXML
    void imgPasswordViewOnAction(MouseEvent event) {

    }
    private void showErrorMessage(String message) {
        lblError.setText(message);
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-alignment: center");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> lblError.setText("")
        ));
        timeline.play();
    }
    private void loadUI(String resource) {
        rootPane.getChildren().clear();
        try {
            rootPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
