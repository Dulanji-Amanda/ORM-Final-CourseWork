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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private BarChart<String, Number> paymentBarChart;

    private SessionFactory factory;

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

        factory = new Configuration().configure().buildSessionFactory();

        loadPaymentChartData();

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

    private void loadPaymentChartData() {
        Session session = factory.openSession();
        session.beginTransaction();

        // Fetch counts for completed and pending payments
        Long completed = (Long) session.createQuery("SELECT COUNT(*) FROM Payment WHERE status = 'completed'")
                .uniqueResult();
        Long pending = (Long) session.createQuery("SELECT COUNT(*) FROM Payment WHERE status = 'pending'")
                .uniqueResult();

        session.getTransaction().commit();
        session.close();

        // Calculate total payments
        long totalPayments = completed + pending;

        // Calculate percentages
        double completedPercentage = totalPayments > 0 ? (completed * 100.0) / totalPayments : 0;
        double pendingPercentage = totalPayments > 0 ? (pending * 100.0) / totalPayments : 0;

        // Update bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Payment Status");

        series.getData().add(new XYChart.Data<>("Completed (" + String.format("%.2f", completedPercentage) + "%)", completedPercentage));
        series.getData().add(new XYChart.Data<>("Pending (" + String.format("%.2f", pendingPercentage) + "%)", pendingPercentage));

        paymentBarChart.getData().clear();
        paymentBarChart.getData().add(series);
    }

}