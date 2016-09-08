/*
 * File: DemoApp.java
 * Author: Dale Skrien
 * Course: CS361
 * Project: 0
 * Date: Sept. 6, 2016
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application // VERSION 1
{
    @Override
    public void start(Stage primaryStage) {
        Button playButton = new Button();
        playButton.setText("Play Scale");
        playButton.setBackground( new Background(new BackgroundFill(Color.LIME, CornerRadii.EMPTY, Insets.EMPTY)));
        playButton.setOnAction(event -> System.out.println("playButton clicked"));

        Button stopButton = new Button();
        stopButton.setText("Stop Playing");
        stopButton.setBackground( new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        stopButton.setOnAction(event -> System.out.println("stopButton clicked"));

        StackPane root = new StackPane();
        HBox buttonPane = new HBox(5.0);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(playButton,stopButton);
        root.getChildren().add(buttonPane);


        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}