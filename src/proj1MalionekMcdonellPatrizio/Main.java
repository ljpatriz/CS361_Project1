package proj1MalionekMcdonellPatrizio;

/*
 * File: Main.java
 * Names: Joseph Malionek, Kyle McDonell, Larry Patrizio
 * Class: CS361
 * Project 1
 * Date: September 14
 */


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

/**
 * An application in which a user can player an ascending and descending scale using midi
 * with a variable start note.
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


    /** Stops the midi player and instead plays a scale up and down starting from startNote
     *
     * @param startNote the starting note for the scale
     */
    public void playScale(int startNote)
    {
        //The notes of a scale up and then down when starting from 0 and with a half-step being 1
        int[] notes = {0,2,4,5,7,9,11,12,12,11,9,7,5,4,2,0};
        for(int i=0; i<notes.length; i++)
        {
            midiPlayer.addNote(startNote+notes[i], VOLUME, i*NOTE_DURATION, NOTE_DURATION, 0, 0);
        }
        midiPlayer.play();
    }


    /**
     * Stops and clears the midi player queue
     */
    public void stopPlayer()
    {
        midiPlayer.stop();
        midiPlayer.clear();
    }


    /**Factory method for creating buttons
     *
     * @param text The text displayed on the button
     * @param css A css string used to style the button
     * @param handler The handler that is called when the button is pressed
     * @return The button object which this method creates
     */
    public Button createButton(String text, String css,
                               EventHandler<ActionEvent> handler)
    {
        Button button = new Button();
        button.setText(text);
        button.setOnAction(handler);
        button.setStyle(css);
        return button;
    }


    /**
     * Creates the controls for the scale player scene
     * @return an HBox containing the necessary controls for the Scale Player
     */
    private HBox createScaleControls()
    {
        // Button bar
        HBox buttonBox = new HBox(8.0); //Spacing between elements

        // Play button
        buttonBox.getChildren().add(createButton("Play scale",
                                                 "-fx-base: #99FF66",
                                                 event -> openNoteDialog()));
        // Stop button
        buttonBox.getChildren().add(createButton("Stop playing",
                                                 "-fx-base: #FF8888",
                                                 event -> stopPlayer()));

        return buttonBox;
    }

    /**Creates and returns the menu bar to be added to this application.
     *
     * @return This applications menu bar
     */
    private MenuBar makeMenuBar()
    {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event -> this.stop());     // This could be done using a factory
        fileMenu.getItems().add(exitMenuItem);              // like it is for buttons

        menuBar.getMenus().add(fileMenu);
        return menuBar;
    }



    /**
     * Opens a dialog asking the user for a starting note at which to play a scale
     */
    public void openNoteDialog(){
        TextInputDialog startingNoteDialog = new TextInputDialog();
        startingNoteDialog.setTitle("Starting Note");
        startingNoteDialog.setHeaderText("Give me a starting note (0-115):");

        // Prevent characters other than 0-9 from being entered (we recognize this was not necessary)
        TextFormatter decimalFilter = new TextFormatter<>(c -> c.getText().matches("\\d*") ? c : null);
        startingNoteDialog.getEditor().setTextFormatter(decimalFilter);
        // Needs an error for no number or out of bounds

        // If they hit OK, parse their input and play the scale
        startingNoteDialog.showAndWait().ifPresent(
                selection -> {
                    int startNote = Integer.parseInt(selection);
                    stopPlayer();
                    playScale(startNote);
                });
    }



    /**
     * The main method for this javafx program which handles setting up the layout, controls, and
     * all button bindings
     * @param primaryStage The main window
     */
    @Override
    public void start(Stage primaryStage)
    {
        BorderPane pane = new BorderPane();

        // Create the menubar
        MenuBar menuBar = makeMenuBar();
        pane.setTop(menuBar);

        // Create buttonBox
        HBox buttonBox = createScaleControls();
        buttonBox.setAlignment(Pos.CENTER);
        pane.setCenter(buttonBox);


        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(new Scene(pane, 300, 250));
        primaryStage.show();
    }

    /**
     * Exits the program
     */
    public void stop(){ System.exit(0); }

    /**
     * Just calls the launch method
     * @param args the command line arguments that this program was run with
     */
    public static void main(String[] args) { launch(args); }


}



