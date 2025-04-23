package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.TherapySessionBO;
import lk.ijse.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.dto.TherapySessionDTO;
import lk.ijse.view.tdm.TherapySessionTM;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class TherapySessionTableFormController implements Initializable {

    @FXML
    private JFXButton btnBack;

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
    private AnchorPane recordPane;

    @FXML
    private TableView<TherapySessionTM> tblTherapySession;

    private final TherapySessionBO therapySessionBO = new TherapySessionBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        clmTime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        clmStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        clmPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        clmProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        clmTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));

        loadSessions();
    }

    private void loadSessions() {
        ArrayList<TherapySessionDTO> sessions =  therapySessionBO.loadAllSessions();
        ObservableList<TherapySessionTM> sessionTMS = FXCollections.observableArrayList();

        for (TherapySessionDTO dto : sessions) {

            TherapySessionTM sessionTM = new TherapySessionTM(
                    dto.getSessionId(),
                    dto.getSessionDate(),
                    dto.getSessionTime(),
                    dto.getStatus(),
                    dto.getPatientId(),
                    dto.getProgramId(),
                    dto.getTherapistId()

            );

            sessionTMS.add(sessionTM);
        }
        tblTherapySession.setItems(sessionTMS);
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Patient-Table-View.fxml")));
            recordPane.getChildren().setAll(pane);
        } catch (IOException e) {
            showAlert("Error", "Failed to load session list!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}