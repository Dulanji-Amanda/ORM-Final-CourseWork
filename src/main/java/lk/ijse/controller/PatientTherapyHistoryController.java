package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.bo.custom.TherapyProgramsBO;
import lk.ijse.bo.custom.TherapySessionBO;
import lk.ijse.bo.custom.impl.TherapistBOImpl;
import lk.ijse.bo.custom.impl.TherapyProgramsBOImpl;
import lk.ijse.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.dto.TherapySessionDTO;
import lk.ijse.view.tdm.TherapyHistoryTM;

import java.util.ArrayList;

public class PatientTherapyHistoryController {

    @FXML
    private TableView<TherapyHistoryTM> tblTherapyHistory;

    @FXML
    private TableColumn<TherapySessionDTO, String> colSessionId;

    @FXML
    private TableColumn<TherapySessionDTO, String> colTherapistName;

    @FXML
    private TableColumn<TherapySessionDTO, String> colProgramName;

    @FXML
    private TableColumn<TherapySessionDTO, String> colDate;

    private final TherapySessionBO therapySessionBO = new TherapySessionBOImpl();
    private final TherapistBO therapistBO = new TherapistBOImpl();
    private final TherapyProgramsBO therapyProgramsBO = new TherapyProgramsBOImpl();

    public void initialize() {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadTherapyHistory();
    }

    private void loadTherapyHistory() {
        try  {
            ArrayList<TherapySessionDTO> therapySessionDTOS = therapySessionBO.loadAllSessions();
            ObservableList<TherapyHistoryTM> therapyHistoryTMS = FXCollections.observableArrayList();

            for (TherapySessionDTO therapySessionDTO : therapySessionDTOS) {
                TherapyHistoryTM therapyHistoryTM = new TherapyHistoryTM(
                        therapySessionDTO.getSessionId(),
                        therapistBO.findById(therapySessionDTO.getTherapistId()).getTherapistName(),
                        therapyProgramsBO.findById(therapySessionDTO.getProgramId()).getProgramName(),
                        therapySessionDTO.getSessionDate()
                );

                therapyHistoryTMS.add(therapyHistoryTM);
            }
            tblTherapyHistory.setItems(therapyHistoryTMS);
        } catch (Exception e) {
            showAlert("Error", "Failed to load Payments!", Alert.AlertType.ERROR);
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