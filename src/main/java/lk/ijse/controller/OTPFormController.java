package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class OTPFormController {

    @FXML
    private JFXButton btnVerify;

    @FXML
    private ImageView girl;

    @FXML
    private ImageView imgBack;

    @FXML
    private Label lblError;

    @FXML
    private Label lblResend;

    @FXML
    private Label lblResendTimer;

    @FXML
    private TextField txtOtp1;

    @FXML
    private TextField txtOtp2;

    @FXML
    private TextField txtOtp3;

    @FXML
    private TextField txtOtp4;

    @FXML
    void btnVerifyOnAction(ActionEvent event) {

    }

    @FXML
    void imgBackOnAction(MouseEvent event) {
        loadUI("/view/ForgetPassword.fxml");

    }

    @FXML
    void moveToNextField(KeyEvent event) {

    }

    @FXML
    void resendOtp(MouseEvent event) {

    }

    @FXML
    void submitOtpOnEnter(KeyEvent event) {

    }
    private void loadUI(String resource) {

    }

}
