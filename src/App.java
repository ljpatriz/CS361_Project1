/*
 * File: App.java
 * Author: Larry Patrizio
 * Course: CS361
 * Project: 1
 * Date: Sept 11 2016
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class App extends Application // VERSION 1
{
    MidiPlayer midiPlayer = new MidiPlayer(60,60);
    @Override
    public void start(Stage primaryStage) {

        //Play Button setup and resulting dialogue window
        Button playButton = new Button();
        playButton.setText("Play Scale");
        playButton.setStyle("-fx-border-color:black; -fx-background-color:lightgreen;");
        playButton.setOnAction(new EventHandler<ActionEvent>() {

            //Dialogue window that alows users to select a starting value for a scale to play
            @Override
            public void handle(ActionEvent event) {

                //Create needed Stackpane and Stage for the dialogue window
                System.out.println("Opened PopUp Window");
                StackPane root = new StackPane();
                Stage stage = new Stage();

                //Create instruction message within the dialogue window
                Text instructions = new Text();
                instructions.setText("Give me a starting note (0-115):");
                root.getChildren().add(instructions);
                root.setAlignment(instructions,Pos.CENTER);
                instructions.setTranslateY(-45);

                //Create the textfield for the user to enter a scale start value
                TextField startingNoteText = new TextField();
                root.getChildren().add(startingNoteText);


                //Create an "ok" button that contains the code that creates a scale and then plays it
                Button okButton = new Button();
                okButton.setText("Ok");
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int start = Integer.parseInt(startingNoteText.getText());
                        int[] scale;
                        scale = playScale(start);
                        System.out.println("Played Scale");
                        System.out.println(Arrays.toString(scale));
                        stage.close();
                    }
                });
                root.getChildren().add(okButton);
                root.setAlignment(okButton,Pos.BOTTOM_RIGHT);
                okButton.setTranslateX(-15);
                okButton.setTranslateY(-10);

                //Create a cancel button that exits from the dialogue window
                Button cancelButton = new Button();
                cancelButton.setText("Cancel");
                cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Canceling Starting Note Window");
                        stage.close();
                    }
                });
                root.getChildren().add(cancelButton);
                root.setAlignment(cancelButton,Pos.BOTTOM_RIGHT);
                cancelButton.setTranslateX(-60);
                cancelButton.setTranslateY(-10);


                //Open the dialogue window
                stage.setTitle("Starting Note");
                stage.setScene(new Scene(root, 200,150));
                stage.show();
            }
        });

        //Creates a stop button that will stop the playing of a scale.
        Button stopButton = new Button();
        stopButton.setText("Stop Playing");
        stopButton.setStyle("-fx-border-color:black; -fx-background-color:pink;");
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                midiPlayer.stop();
                System.out.println("Stopped Scale");
            }
        });


        //positions the play and stop buttons within the window
        StackPane root = new StackPane();
        root.getChildren().add(playButton);
        root.setAlignment(playButton, Pos.CENTER);
        playButton.setTranslateX(-50);

        root.getChildren().add(stopButton);
        root.setAlignment(stopButton, Pos.CENTER);
        stopButton.setTranslateX(50);
        Scene scene = new Scene(root, 300, 250);


        //adds the menubar and positions it correctly
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuBar.getMenus().add(menuFile);
        root.getChildren().add(menuBar);
        root.setAlignment(Pos.TOP_CENTER);

        //adds a exit button within the menubar to exit the Java program
        MenuItem exitButton = new MenuItem("Exit",null);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        menuFile.getItems().add(exitButton);


        //Opens up the window
        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //Method that takes in a start value (integer), creates a scale based on that value,
    //and then plays the resulting scale
    public int[] playScale(int start)
    {
        int[] scale = {start, start+10, start+20, start+30, start+40};
        int startTick = 0;
        for(int note : scale)
        {
            System.out.println(note);
            midiPlayer.addNote(note, 100, startTick, 1, 0, 0);
            startTick+=10;
        }
        midiPlayer.play();
        return scale;
    }

    //Launchs program
    public static void main(String[] args) {
        launch(args);
    }

}