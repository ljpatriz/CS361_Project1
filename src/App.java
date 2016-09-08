/*
 * File: DemoApp.java
 * Author: Dale Skrien
 * Course: CS361
 * Project: 0
 * Date: Sept. 6, 2016
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application // VERSION 1
{
    @Override
    public void start(Stage primaryStage) {
        Button playButton = new Button();
        playButton.setText("Play Scale");
        playButton.setBackground( new Background(new BackgroundFill(Color.LIME, CornerRadii.EMPTY, Insets.EMPTY)));

        Button stopButton = new Button();
        stopButton.setText("Stop Playing");
        playButton.setBackground( new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));

        StackPane root = new StackPane();
        root.getChildren().add(playButton);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}