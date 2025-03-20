package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.UserBoImpl;
import lk.ijse.bo.custom.UserBo;
import lk.ijse.util.Role;
import javafx.scene.image.ImageView;

import lk.ijse.util.PasswordEncryptionUtil;

import java.io.IOException;

public class SignupPageController {

    @FXML
    private AnchorPane signupPage;

    @FXML
    private TextField txtUsername;

    @FXML
    private AnchorPane ap2;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private ImageView img;

    @FXML
    private Button btnSignUp;

    private final UserBo userBo = new UserBoImpl();

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();
        String selectedRole = cmbRole.getValue();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedRole == null) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!", Alert.AlertType.ERROR);
            return;
        }

        // Convert string to Role Enum
        Role role = Role.valueOf(selectedRole);

        // Save user to database
        boolean success = userBo.registerUser(username, password, role);

        if (success) {
            showAlert("Success", "Account created successfully!", Alert.AlertType.INFORMATION);
            goToLogin(null);
        } else {
            showAlert("Error", "Username already exists!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void goToLogin(ActionEvent event) {
        loadUI("/view/LoginPage.fxml");
    }

    private void loadUI(String resource) {
        signupPage.getChildren().clear();
        try {
            signupPage.getChildren().add(FXMLLoader.load(getClass().getResource(resource)));
        } catch (IOException e) {
            showAlert("Error", "Failed to load page!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
