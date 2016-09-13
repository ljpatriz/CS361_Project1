package sample;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


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



    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Exits the program
     */
    public void stop(){ System.exit(0); }

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane pane = new BorderPane();

        // Create the menubar
        MenuBar menuBar = makeMenuBar();
        pane.setTop(menuBar);

        // Create buttonBox
        HBox buttonBox = createScaleControls();
        buttonBox.setAlignment(Pos.CENTER);
        pane.setCenter(buttonBox);


        primaryStage.setTitle("Demo");
        primaryStage.setScene(new Scene(pane, 300, 250));
        primaryStage.show();
    }




    public HBox createScaleControls(){
        // Button bar
        HBox buttonBox = new HBox(8.0); //Spacing between elements
        // Play button
        buttonBox.getChildren().add(createButton("Play scale",
                "-fx-base: #99FF66",
                event -> openNoteDialog()));
        // Stop button
        buttonBox.getChildren().add(createButton("Stop playing",
                "-fx-base: #FF8888",
                event -> stopScale()));

        return buttonBox;
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
    // Could be done in a similar way to the buttons.  Function to create a menubar given the menu options


    public Button createButton(String text, String css,
                               EventHandler<ActionEvent> handler){
        Button button = new Button();
        button.setText(text);
        button.setOnAction(handler);
        button.setStyle(css);
        return button;
    }


    public void openNoteDialog(){
        TextInputDialog startingNoteDialog = new TextInputDialog();
        startingNoteDialog.setTitle("Starting Note");
        startingNoteDialog.setHeaderText("Give me a starting note (0-115):");

        // Prevent characters other than 0-9 from being entered
        TextFormatter decimalFilter = new TextFormatter<>(c -> c.getText().matches("\\d*") ? c : null);
        startingNoteDialog.getEditor().setTextFormatter(decimalFilter);
        // Needs an error for no number or too large

        startingNoteDialog.showAndWait().ifPresent(     // Is this the way to do it?
                string -> {
                    int startNote = Integer.parseInt(string);
                    stopScale();
                    playScale(startNote);
                });
    }


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

    public void stopScale()
    {
        midiPlayer.stop();
        midiPlayer.clear();
    }




}



