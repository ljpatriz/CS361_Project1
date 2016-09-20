/*
 * File: Main.java
 * Names: Joseph Malionek, Kyle McDonell, Larry Patrizio
 * Class: CS361
 * Project 2
 * Date: September 19
 */

package proj2MalionekMcdonellPatrizio;

import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


/**
 * An application in which a user can player an ascending and descending scale using midi
 * with a variable start note.
 * @author Joseph Malionek, Kyle McDonell, Larry Patrizio
 */
public class Main extends Application {

    /**The volume at which the scale will play */
    private final int VOLUME = 64;
    /**The number of beats per minute the midi player will play at*/
    private final int BPM = 100;
    /**The number of ticks that will be counted in a beat*/
    private final int TICKS_PER_BEAT = 4;
    /**The duration of the notes (in ticks) of the notes of the scale*/
    private final int NOTE_DURATION = 4;
    /**The midi player which plays the scale*/
    private final MidiPlayer midiPlayer = new MidiPlayer(TICKS_PER_BEAT,BPM);


    // FXML items automatically injected by the FXMLLoader
    /**JavaFX exit menu item to quit the application*/
    @FXML   //fx:id="exitButton"
    private MenuItem exitButton;
    /**JavaFX button to allow the user to select a scale and play it*/
    @FXML   //fx:id="playButton"
    private Button playButton;
    /**JavaFX button to stop the player*/
    @FXML   //fx:id="stopButton"
    private Button stopButton;


    /**
     * Binds all of the actions to the control structures in the stage. This is automatically called
     * when all FXML injectable fields have been loaded.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    private void initialize() {
        exitButton.setOnAction(event -> stop());
        playButton.setOnAction(event -> notePromptAndPlay());
        stopButton.setOnAction(event -> stopPlayer());
    }

    /**
     * The main method for this javafx program which handles setting up the layout, controls, and
     * all button bindings
     * @param primaryStage The main window
     * @throws IOException If the fxml or css files cannot be properly initialized
     */
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Scene mainScene = FXMLLoader.load(getClass().getResource("Main.fxml"));
        mainScene.getStylesheets().add(getClass().getResource("Main.css").toString());
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Scale Player");
        primaryStage.show();
    }

    /**
     * Exits the program
     */
    @Override
    public void stop() { System.exit(0); }


    /**
     * Opens a a text input dialog with the given title and prompt. This dialog does not allow the
     * user to input anything other than digits.  If the user hits okay with an integer entered,
     * this method will return said integer. Otherwise this will return an empty Optional<Integer>.
     *
     * @param title The title of the dialog box
     * @param prompt The prompt given to the user in the dialog box
     * @return an Optional<Integer> which is non-empty if the user enters an integer and hits okay
     */
    public Optional<Integer> getUserIntegerInput(String title, String prompt)
    {
        TextInputDialog startingNoteDialog = new TextInputDialog();
        startingNoteDialog.setTitle(title);
        startingNoteDialog.setHeaderText(prompt);

        // Prevent characters other than 0-9 from being entered
        TextFormatter decimalFilter = new TextFormatter<>(c -> c.getText().matches("\\d*") ? c : null);
        startingNoteDialog.getEditor().setTextFormatter(decimalFilter);

        // Return an optional containing their input as an integer, or empty if there is no integer
        return startingNoteDialog.showAndWait().filter(str -> !str.isEmpty()).map(Integer::parseInt);
    }

    /**
     * Prompts the user for a note and plays the scale if a valid integer (an integer in the range
     * of 0-115) is input and the user hits okay. This stops any currently playing sounds and plays
     * a major scale starting at the given note.
     * An InvalidMidiDataException is thrown by the MidiPlayer if the starting note is above 115.
     */
    public void notePromptAndPlay()
    {
        getUserIntegerInput("Starting Note", "Give me a starting note (0-115):")
            .ifPresent(input -> { stopPlayer(); playScale(input); });
    }

    /**
     * Plays a scale up and down starting from startNote. An InvalidMidiDataException is thrown by
     * the player if the starting note is outside of the range 0 to 115.
     * @param startNote the starting note for the scale
     */
    public void playScale(int startNote)
    {
        System.out.println(startNote);
        //The notes of a scale up and then down when starting from 0 and with a half-step being 1
        int[] notes = {0,2,4,5,7,9,11,12,12,11,9,7,5,4,2,0};
        for(int i=0; i<notes.length; i++)
        {
            midiPlayer.addNote(startNote+notes[i], VOLUME, i*NOTE_DURATION, NOTE_DURATION, 0, 0);
        }
        midiPlayer.play();
    }


    /**
     * Stops and clears the midi player queue, stopping all sound
     */
    public void stopPlayer()
    {
        midiPlayer.stop();
        midiPlayer.clear();
    }



    /**
     * Entry method to the application with launches the JavaFX GUI thread responsible for
     * the display.
     * @param args the command line arguments that this program was run with.
     */
    public static void main(String[] args) { launch(args); }


}



