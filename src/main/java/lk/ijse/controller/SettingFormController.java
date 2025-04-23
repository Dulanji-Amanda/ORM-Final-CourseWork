package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.UserBo;
import lk.ijse.bo.custom.impl.UserBoImpl;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingFormController implements Initializable {

    @FXML
    private Label emaillbl;

    @FXML
    private TextField emailtxt;

    @FXML
    private ImageView iconimg;

    @FXML
    private Label lb;

    @FXML
    private Label passwordlbl;

    @FXML
    private TextField passwordtxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private AnchorPane settingap;

    @FXML
    private Label unamelbl;

    @FXML
    private TextField unametxt;

    private final UserBo userBo = new UserBoImpl();

    public void initialize(URL url, ResourceBundle rb) {
        unametxt.requestFocus();
        passwordtxt.setEditable(false);
        try {
            loadData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        unametxt.setText("");
        emailtxt.setText("");
        passwordtxt.setText("");

        savebtn.setDisable(false);
    }

    private void loadData() throws SQLException, ClassNotFoundException {
        String userId = LoginPageController.username;
        User user = userBo.getUserByUsername(userId);

        unametxt.setText(user.getUsername());
        emailtxt.setText(user.getEmail());
        passwordtxt.setText(user.getPassword());
//        cmbroll.setItems(FXCollections.observableArrayList(userIds));
    }


    @FXML
    void savebtnOnAction(ActionEvent event) throws SQLException {
        String username = unametxt.getText();
        String email = emailtxt.getText();

        String unamePattern = "^[a-zA-Z0-9_]{5,15}$";
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";

        boolean isValidUname = username.matches(unamePattern);
        boolean isValidEmail = email.matches(emailPattern);

        unametxt.setStyle(unametxt.getStyle() + ";-fx-border-color:  #091057;");
        emailtxt.setStyle(emailtxt.getStyle() + ";-fx-border-color:  #091057;");

        if(!isValidUname){
            unametxt.setStyle(unametxt.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail){
            emailtxt.setStyle(emailtxt.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidUname && isValidEmail){
            String userId = LoginPageController.username;
            User user = userBo.getUserByUsername(userId);

            String selectedUserId = userId.replace("[", "").replace("]", "");
            UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), String.valueOf(user.getRole()));
            userDTO.setUsername(unametxt.getText());
            userDTO.setEmail(emailtxt.getText());
            boolean isSaved = userBo.updateUser(userDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User Information saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save user information...!").show();
            }
        }
    }

}