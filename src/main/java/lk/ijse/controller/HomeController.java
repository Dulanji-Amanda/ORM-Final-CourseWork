package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Label timeLabel;

    @FXML
    private AnchorPane homepane;

    @FXML
    private Button logoutbtn;

    @FXML
    void logoutbtnOnAction(ActionEvent event) {
        navigateTo();
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void navigateTo() {
        try {

            Stage stage = (Stage) homepane.getScene().getWindow();
            stage.close();

            start(new Stage());

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        setupTherapyPieChart(8, 2); // Example: 8 completed, 2 upcoming
//        setupPaymentBarChart(5, 3); // Example: 5 completed, 3 pending
        // Define the formatter for date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Create a Timeline to update the label every 1 second
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            timeLabel.setText(now.format(formatter));
        }));

        clock.setCycleCount(Timeline.INDEFINITE); // Run forever
        clock.play(); // Start the clock
    }

//    @FXML
//    private PieChart therapyPieChart;
//
//    @FXML
//    private BarChart<String, Number> paymentBarChart;

//    private void setupTherapyPieChart(int completed, int upcoming) {
//        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
//                new PieChart.Data("Completed", completed),
//                new PieChart.Data("Upcoming", upcoming)
//        );
//        therapyPieChart.setData(pieData);
//        therapyPieChart.setTitle("Today's Therapies");
//    }
//
//    private void setupPaymentBarChart(int completedPayments, int pendingPayments) {
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.setName("Payments");
//
//        series.getData().add(new XYChart.Data<>("Completed", completedPayments));
//        series.getData().add(new XYChart.Data<>("Pending", pendingPayments));
//
//        paymentBarChart.getData().add(series);
//    }
}