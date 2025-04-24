package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.bo.custom.TherapySessionBO;
import lk.ijse.bo.custom.impl.TherapistBOImpl;
import lk.ijse.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.dto.TherapySessionDTO;
import lk.ijse.view.tdm.TherapistPerformanceTM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TherapistPerformanceReportController {

    @FXML
    private TableView<TherapistPerformanceTM> tblTherapistPerformance;

    @FXML
    private TableColumn<TherapistPerformanceTM, String> colTherapistName;

    @FXML
    private TableColumn<TherapistPerformanceTM, Integer> colSessionCount;

    private final TherapySessionBO therapySessionBO = new TherapySessionBOImpl();
    private final TherapistBO therapistBO = new TherapistBOImpl();

    public void initialize() {
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colSessionCount.setCellValueFactory(new PropertyValueFactory<>("sessionCount"));

        loadTherapistPerformance();
        loadSessionStatistics();
    }

    private void loadTherapistPerformance() {
        try {
            List<TherapistDTO> allTherapists = therapistBO.loadAllTherapists();

            List<TherapySessionDTO> allSessions = therapySessionBO.loadAllSessions();

            Map<String, Integer> sessionCountMap = new HashMap<>();
            for (TherapySessionDTO session : allSessions) {
                String therapistId = session.getTherapistId();
                sessionCountMap.put(therapistId, sessionCountMap.getOrDefault(therapistId, 0) + 1);
            }

            ObservableList<TherapistPerformanceTM> performanceData = FXCollections.observableArrayList();

            for (TherapistDTO therapist : allTherapists) {
                int count = sessionCountMap.getOrDefault(therapist.getTherapistID(), 0);
                performanceData.add(new TherapistPerformanceTM(
                        therapist.getTherapistName(),
                        String.valueOf(count)
                ));
            }

            tblTherapistPerformance.setItems(performanceData);

        } catch (Exception e) {
            showAlert("Error", "Failed to load therapist performance data!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void loadSessionStatistics() {
        try {
            List<TherapySessionDTO> allSessions = therapySessionBO.loadAllSessions();

            int completed = 0;
            int pending = 0;

            for (TherapySessionDTO session : allSessions) {
                if ("COMPLETED".equalsIgnoreCase(session.getStatus())) {
                    completed++;
                } else {
                    pending++;
                }
            }

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Completed", completed),
                    new PieChart.Data("Pending", pending)
            );
            
        } catch (Exception e) {
            showAlert("Error", "Failed to load session statistics!", Alert.AlertType.ERROR);
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