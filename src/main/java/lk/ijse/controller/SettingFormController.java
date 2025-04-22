package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.UserBo;
import lk.ijse.bo.custom.impl.UserBoImpl;
import lk.ijse.entity.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SettingFormController implements Initializable {

    @FXML
    private ComboBox<String> cmbroll;

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

    @FXML
    private Label useridlbl;

    private final UserBo userBo = new UserBoImpl();

    public void initialize(URL url, ResourceBundle rb) {
        unametxt.requestFocus();

        try {
            loadRoll();
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        unametxt.setText("");
        emailtxt.setText("");
        passwordtxt.setText("");

        savebtn.setDisable(false);
    }

    private void loadRoll() throws SQLException, ClassNotFoundException {
        //ArrayList<String> userIds = userBo.getAllRoll();
        //cmbroll.setItems(FXCollections.observableArrayList(userIds));
    }

    @FXML
    void cmbrollOnAction(ActionEvent event) {
        String selectedRoll = cmbroll.getSelectionModel().getSelectedItem();
        selectedRoll = selectedRoll.replace("[", "").replace("]", "");
        if (selectedRoll != null) {
            //User userDTO = userBo.findByRoll(selectedRoll);
//            if (userDTO != null) {
//                unametxt.setText(userDTO.getUsername());
//                emailtxt.setText(userDTO.getEmail());
//                passwordtxt.setText(userDTO.getPassword());
//            }
        }
    }

    @FXML
    void resetbtnOnAction(ActionEvent event) throws SQLException {
        cmbroll.setValue(null);
        cmbroll.setPromptText("Select Roll");

        unametxt.setText("");
        emailtxt.setText("");
        passwordtxt.setText("");

        refreshPage();
    }

    @FXML
    void savebtnOnAction(ActionEvent event) {

    }

}

