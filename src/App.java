/*
 * File: DemoApp.java
 * Author: Dale Skrien
 * Course: CS361
 * Project: 0
 * Date: Sept. 6, 2016
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application // VERSION 1
{
    @Override
    public void start(Stage primaryStage) {
        Button button = new Button();
        button.setText("Click Me");

        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}