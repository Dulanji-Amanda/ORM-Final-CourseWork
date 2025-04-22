package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.view.tdm.TherapySessionTM;

import java.time.LocalDate;
import java.time.LocalTime;

public class TherapySessionManagementFormController {

    @FXML
    private JFXButton btnAddNewTherapist;

    @FXML
    private JFXButton btnAddPatient;

    @FXML
    private JFXButton btnAddTherapist;

    @FXML
    private JFXButton btnAddTherapyProgram;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnCompleteSetup;

    @FXML
    private JFXButton btnReschedule;

    @FXML
    private TableColumn<TherapySessionTM, LocalDate> clmDate;

    @FXML
    private TableColumn<TherapySessionTM, String> clmPatientId;

    @FXML
    private TableColumn<TherapySessionTM, String> clmProgramId;

    @FXML
    private TableColumn<TherapySessionTM, String> clmSessionId;

    @FXML
    private TableColumn<TherapySessionTM, String> clmStatus;

    @FXML
    private TableColumn<TherapySessionTM, String> clmTherapistId;

    @FXML
    private TableColumn<TherapySessionTM, LocalTime> clmTime;

    @FXML
    private DatePicker dpSessionDate;

    @FXML
    private ImageView imgHome;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<TherapySessionTM> tblTherapySession;

    @FXML
    private TextField txtSessionId;

    @FXML
    private TextField txtSessionTime;

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {

    }

    @FXML
    void btnAddPatient_OnAction(ActionEvent event) {

    }

    @FXML
    void btnAddTherapist_OnAction(ActionEvent event) {

    }

    @FXML
    void btnAddTherapyProgram_OnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnCompleteSetupOnAction(ActionEvent event) {

    }

    @FXML
    void btnRescheduleOnAction(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

}

