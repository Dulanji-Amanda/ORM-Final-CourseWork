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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class OTPVerifiedFormController {

    @FXML
    private JFXButton btnReset;

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
    private AnchorPane whiteap;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtConfirmPasswordVisible;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPasswordVisible;

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

    //public UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    private boolean isPasswordVisible = false;

    private boolean isConfirmPasswordVisible = false;

    @FXML
    public void initialize() {
        txtPassword.requestFocus();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        if (areFieldsEmpty()) {
            showErrorMessage("*Required fields cannot be empty.");
        } else if (!isValidPassword(txtPassword.getText())) {
            showErrorMessage("*Password must be at least 8 characters long, contain a digit, a lowercase letter, an uppercase letter, and a special character.");
        } else if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            showErrorMessage("*Passwords do not match.");
        } else {
//                if (updateUser()) {
//                    loadUI("/view/LoginPage.fxml");
//                } else {
//                    showErrorMessage("*User not updated.");
//                }
        }
    }

    @FXML
    void imgBackOnAction(MouseEvent event) {
        loadUI("/view/LoginPage.fxml");
    }

    @FXML
    void imgConfirmPasswordViewOnAction(MouseEvent event) {
        if (isConfirmPasswordVisible) {
            txtConfirmPassword.setText(txtConfirmPasswordVisible.getText());
            txtConfirmPasswordVisible.setVisible(false);
            txtConfirmPassword.setVisible(true);
        } else {
            txtConfirmPasswordVisible.setText(txtConfirmPassword.getText());
            txtConfirmPasswordVisible.setVisible(true);
            txtConfirmPassword.setVisible(false);
        }
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
    }

    @FXML
    void imgPasswordViewOnAction(MouseEvent event) {
        if (isPasswordVisible) {
            txtPassword.setText(txtPasswordVisible.getText());
            txtPasswordVisible.setVisible(false);
            txtPassword.setVisible(true);
        } else {
            txtPasswordVisible.setText(txtPassword.getText());
            txtPasswordVisible.setVisible(true);
            txtPassword.setVisible(false);
        }
        isPasswordVisible = !isPasswordVisible;
    }

//    private boolean updateUser() throws SQLException, ClassNotFoundException {
//        final List<UserDTO> allUsers = userBO.getAllUser();
//        for (UserDTO user : allUsers) {
//            if (user.getEmail().equals(ForgetPasswordFormController.emailAddress)) {
//                user.setPassword(txtPassword.getText());
//                userBO.updateUser(user);
//                System.out.println("User updated");
//                return true;
//            }
//        }
//        System.out.println("not");
//        return false;
//    }

    private boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    private boolean areFieldsEmpty() {
        return txtPassword.getText().isEmpty() && txtConfirmPassword.getText().isEmpty();
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
