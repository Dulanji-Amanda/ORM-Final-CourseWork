package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgetPasswordController {

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private ImageView imgBack;

    @FXML
    private Label lblError;

    @FXML
    private Label lblLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    void btnSubmitOnAction(ActionEvent event) {
        loadUI(event, "/view/OTPForm.fxml");
    }

    private void loadUI(ActionEvent event, String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // You can replace this with an alert for better user feedback
        }
    }

    @FXML
    void imgBackOnAction(MouseEvent event) {
        loadUI(new ActionEvent(event.getSource(), null), "/view/LoginPage.fxml"); // Navigate back to login page
    }
}
