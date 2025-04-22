package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Objects;

public class ForgetPasswordFormController  {

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private ImageView imgBack;

    @FXML
    private Label lblError;

    @FXML
    private Label lblLogin;

    @FXML
    private AnchorPane whiteap;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField txtEmail;

    public static String emailAddress = "";

    //public UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public static String otpGenerated = "0000";

    SendMailController sendMailController = new SendMailController();

    @FXML
    public void initialize() {
        txtEmail.requestFocus();
    }

    @FXML
    void btnSubmitOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (areFieldsEmpty()) {
            showErrorMessage("*Required fields are empty");
//        } else if (!isValidEmailAddress()) {
//            showErrorMessage("*Invalid email address");
        } else {
            emailAddress = txtEmail.getText();

            String recipientEmail = emailAddress; // Replace with the recipient's email
            String otp = generateOTP();
            otpGenerated = generateOTP();// Generate OTP
            //System.out.println("Generated OTP: " + otp);
            sendMailController.sendEmail(recipientEmail, otpGenerated);

            loadUI("/view/OTPForm.fxml");
        }
    }

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 1000 + random.nextInt(9000); // Generates a 4-digit OTP
        return String.valueOf(otp);
    }

//    private boolean isValidEmailAddress() throws SQLException, ClassNotFoundException {
//        return userBO.isEmailExists(txtEmail.getText());
//    }

    private boolean areFieldsEmpty() {
        return txtEmail.getText().isEmpty();
    }

    @FXML
    void imgBackOnAction(MouseEvent event) {
        loadUI("/view/LoginPage.fxml");
    }

    private void loadUI(String resource) {
        rootPane.getChildren().clear();
        try {
            rootPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorMessage(String message) {
        lblError.setText(message);
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> lblError.setText("")
        ));
        timeline.play();
    }

}
