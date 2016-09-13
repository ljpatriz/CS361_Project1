package proj1MalionekMcDonnellPatrizio;/*
 * File: Main.java
 * Author: Joseph Malionek
 * Course: CS361
 * Project: 1
 * Date: Sept. 6, 2016
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**An application which allows the user to play scales up and down using different starting notes
 *
 */
public class App extends Application // VERSION 1
{
    /**The volume at which the scale will play */
    final int VOLUME = 64;
    /**The number of beats per minute the midi player will play at*/
    final int BPM = 100;
    /**The number of ticks that will be counted in a beat*/
    final int TICKS_PER_BEAT = 4;
    /**The duration of the notes (in ticks) of the notes of the scale*/
    final int NOTE_DURATION = 4;
    /**The midi player which plays the scale*/
    private MidiPlayer midiPlayer = new MidiPlayer(TICKS_PER_BEAT,BPM);

    /**Sets the layout of the app and starts it running
     *
     * @param primaryStage The main window for this app
     */
    @Override
    public void start(Stage primaryStage) {
        //Adding the Buttons first
        Button playButton = new Button();
        playButton.setText("Play Scale");
        playButton.setStyle("-fx-base: #00FF00;");
        playButton.setOnAction(new playButtonHandler());

        Button stopButton = new Button();
        stopButton.setText("Stop Playing");
        stopButton.setStyle("-fx-base: #FF8888;");
        stopButton.setOnAction(event -> {midiPlayer.stop();midiPlayer.clear();});

        //Setting up the remaining layout
        BorderPane root = new BorderPane();
        root.setTop(this.makeMenuBar());

        HBox buttonPane = new HBox(5.0,playButton,stopButton);
        buttonPane.setAlignment(Pos.CENTER);
        root.setCenter(buttonPane);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**Creates and returns the menu bar to be added to this application.
     *
     * @return This applications menu bar
     */
    protected MenuBar makeMenuBar()
    {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event -> this.stop());

        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);
        return menuBar;
    }

    /** A class which handles what happens when the play button is pressed. It creates a dialog for text input (a number
     * between 0 and 115 inclusive and then stops whatever the midi player is playing to play a scale which goes up and
     * down starting at the value received in the text input
     */
    protected class playButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            TextInputDialog startingNoteDialog = new TextInputDialog();
            startingNoteDialog.setTitle("Starting Note");
            startingNoteDialog.setHeaderText("Give me a starting note (0-115):");
            startingNoteDialog.showAndWait().ifPresent(
                    string -> {
                    int startNote = Integer.parseInt(string);
                    playScale(startNote);
            });

        }
    }

    /** Stops the midi player and instead plays a scale up and down starting from startNote
     *
     * @param startNote the starting note for the scale
     */
    public void playScale(int startNote)
    {
        midiPlayer.stop();
        midiPlayer.clear();
        //The notes of a scale up and then down when starting from 0 and with a half-step being 1
        int[] notes = {0,2,4,5,7,9,11,12,12,11,9,7,5,4,2,0};
        for(int i = 0;i<notes.length;i++)
        {
            midiPlayer.addNote(startNote+notes[i],VOLUME,i*NOTE_DURATION,NOTE_DURATION,0,0);
        }
        midiPlayer.play();
    }

    /**
     * Exits the program
     */
    public void stop(){
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}