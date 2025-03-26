package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TherapistFormController {

    @FXML
    private JFXButton btnAddNewTherapist;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> clmTherapisstAvailability;

    @FXML
    private TableColumn<?, ?> clmTherapistId;

    @FXML
    private TableColumn<?, ?> clmTherapistName;

    @FXML
    private TableColumn<?, ?> clmTherapistSpeciality;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<?> tblTherapists;

    @FXML
    private TextField txtTherapistAvailability;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTherapistName;

    @FXML
    private TextField txtTherapistSpecialty;

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {

    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {

    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    @FXML
    void tblTherapistsOnClick(MouseEvent event) {

    }

}
