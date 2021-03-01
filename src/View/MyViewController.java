package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import resources.Configurations;

import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

import static resources.Configurations.getNumOfThreads;
import static resources.Configurations.getSearchingAlgorithem;


/**
 * this class is an implementation of the Controller as part of the MVVM architecture
 * @authors: Daniel Goldman and Dor levi
 * @since : 25-6-2020
 */
public class MyViewController implements IView, Initializable {

    private MyViewModel myViewModel;

    private Object game;
@FXML
public ScrollPane scroll_pane;
    @FXML
    public ScrollPane about_pane;
    @FXML
    public Button solve;
    @FXML
    public Label player_row_label;
    @FXML
    public Label player_col_label;
    @FXML
    public TextField textField_mazeRows;
    @FXML
    public TextField textField_mazeColumns;
    @FXML
    public MyDisplayer myDisplayer;

    @FXML
    public Label lbl_player_row;
    @FXML
    public Label lbl_player_column;
    @FXML
    public TextFlow about;
    @FXML
    public ChoiceBox searcher;
    @FXML
    public Slider volume_control;

    private MediaPlayer mediaPlayer;


    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();

    /**
     * sets the StringProperty of the class represents the player current row
     * @param update_player_position_row, new value to be inserted to StringProperty
     */


    public void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set("               "+update_player_position_row);
        int player_row = 0;
        if (update_player_position_row != "")
            player_row = Integer.parseInt(update_player_position_row);
        myViewModel.setPlayer_row(player_row);
    }

    /**
     * sets the StringProperty of the class represents the player current column
     * @param update_player_position_col, new value to be inserted to StringProperty
     */
    public void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set("               "+update_player_position_col);
        int player_col = 0;
        if (update_player_position_col != "")
            player_col = Integer.parseInt(update_player_position_col);
        myViewModel.setPlayer_col(player_col);
    }

    /**
     * initilizing the controller by ResourcesBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_player_row.textProperty().bind(update_player_position_row);
        lbl_player_column.textProperty().bind(update_player_position_col);
        backGroundSound();

    }

    /**
     * initilizes the media and sound that runs in the background
     */
    public void backGroundSound(){
        String path = "src\\resources\\Sound\\game_playback.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.setAutoPlay(true);
        volume_control.setValue(30);
        volume_control.setOnMouseReleased(e->mediaPlayer.setVolume(volume_control.getValue()/100));
    }

    /**
     * plays the win sound when player reaches the goal destination
     */
    public void WinMoveSound() {
        String path = "src\\resources\\Sound\\win.mp3";
        javafx.scene.media.Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.setAutoPlay(true);
        showAlert("You win!!!");
    }

    /**
     * this functions checks that the user has entered correct maze sizes
     * @return array of int containing the size of the desired maze
     */
    public int[] checkInputs(){
        int[] arr = new int[2];
        try{
            arr[0] = Integer.parseInt(textField_mazeRows.getText());
        }
        catch (Exception e){
            showAlert("Enter maze rows.");
            return null;
        }
        try{
            arr[1] = Integer.parseInt(textField_mazeColumns.getText());
        }catch (Exception e){
            showAlert("Enter maze cols.");
            return null;
        }
        if (arr[0] > 500 || arr[0] < 2){
            showAlert("Enter maze rows between 2 and 500.");
            return null;
        }
        if (arr[1] > 500 || arr[1] < 2){
            showAlert("Enter maze columns between 2 and 500.");
            return null;
        }
        return arr;
    }

    /**
     * initlizing back mouse scrolling movment and primary stage
     */
    public void init(){
        addMouseScrolling(myDisplayer.getParent());
        SetStageCloseEvent(myDisplayer.getPrimarystage());
        initSearchChoose();
        scroll_pane.setStyle("-fx-opacity: .9");
    }

    /**
     * activiating the functions from the model and generating a new Maze
     * @throws UnknownHostException
     */
    public void generateMaze() throws UnknownHostException {
        int[] dim = checkInputs();
        if (dim != null) {
            myViewModel.StartServers();

            game = myViewModel.getMaze(dim[0], dim[1]);
            myDisplayer.drawMaze(game, myViewModel.getStartRow(game), myViewModel.getStartCol(game));

            set_update_player_position_row(myViewModel.getStartRow(game) + "");
            set_update_player_position_col(myViewModel.getStartCol(game) + "");

            EnableButtons();
        }
    }

    /**
     * this functions enables and disable certain buttons on the panel
     */
    public void EnableButtons(){
        // enable Solve,searcher, player buttons and labels:
        solve.setDisable(false);
        searcher.setDisable(false);
        player_row_label.setVisible(true);
        player_col_label.setVisible(true);
    }

    /**
     * * activiating the functions from the model and generating a soloution for the new Maze
     */
    public void solveMaze() {

        if (game == null){
            showAlert("Generate maze first.");
        }
        else {
            Configurations.setSolver(searcher.getValue().toString());
            myDisplayer.setSol(myViewModel.getSol());
            myDisplayer.drawSolution(game);
        }
    }

    /**
     * shows an alert by opening new alert window
     * @param message
     */
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE,message, ButtonType.CLOSE);
        alert.show();
    }

    /**
     * initializing the possible  searching algorithm
     */
    public void initSearchChoose() {

        searcher.getItems().add("BreadthFirstSearch");
        searcher.getItems().add("BestFirstSearch");
        searcher.getItems().add("DepthFirstSearch");

        //Set a default value
        searcher.setValue("BestFirstSearch");
        searcher.setOnAction(e -> Configurations.setSolver(searcher.getValue().toString()));
    }

    /**
     * enabling player movement
     * @param p, indicator for if cell value is the winning value
     * @param row, desired row
     * @param col, desired col
     */
    public void MakePlayerMove(int p, int row, int col){
        if (p == 0 || p == 1) {
            myDisplayer.set_player_position(row, col);
            set_update_player_position_row(row + "");
            set_update_player_position_col(col + "");
        }
        if (p == 1)
            WinMoveSound();
    }

    /**
     * maps each number pressed to it's appropriate movement in the board game
     * @param keyEvent, key pressed event 1-8 in numPad
     */
    public void keyPressed(KeyEvent keyEvent) {
        int player_row_position = myDisplayer.getRow_player();
        int player_col_position = myDisplayer.getCol_player();
        int p;
        switch (keyEvent.getCode()){
            case UP:
            case NUMPAD8: // up
                p = myViewModel.MakeMove(game,player_row_position-1,player_col_position);
                MakePlayerMove(p,player_row_position-1,player_col_position);
                break;
            case NUMPAD9: // up-right
                p = myViewModel.MakeMove(game,player_row_position-1,player_col_position+1);
                MakePlayerMove(p,player_row_position-1,player_col_position+1);
                break;
            case RIGHT:
            case NUMPAD6: // right
                p = myViewModel.MakeMove(game,player_row_position,player_col_position+1);
                MakePlayerMove(p,player_row_position,player_col_position+1);
                break;
            case NUMPAD3: // down-right
                p = myViewModel.MakeMove(game,player_row_position+1,player_col_position+1);
                MakePlayerMove(p,player_row_position+1,player_col_position+1);
                break;
            case DOWN:
            case NUMPAD2: // down
                p = myViewModel.MakeMove(game,player_row_position+1,player_col_position);
                MakePlayerMove(p,player_row_position+1,player_col_position);
                break;
            case NUMPAD1: // down-left
                p = myViewModel.MakeMove(game,player_row_position+1,player_col_position-1);
                MakePlayerMove(p,player_row_position+1,player_col_position-1);
                break;
            case LEFT:
            case NUMPAD4: // left
                p = myViewModel.MakeMove(game,player_row_position,player_col_position-1);
                MakePlayerMove(p, player_row_position,player_col_position-1);
                break;
            case NUMPAD7: // up-left
                p = myViewModel.MakeMove(game,player_row_position-1,player_col_position-1);
                MakePlayerMove(p,player_row_position-1,player_col_position-1);
                break;
        }
        keyEvent.consume();
    }

    /**
     * gets focus upon mouse click
     */
    public void mouseClicked() {
        myDisplayer.requestFocus();
    }


    /**
    add event handler to zoom in and out of the board upon mouse scrolling
    */
    public void addMouseScrolling(Node node) {
        node.setOnScroll(
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        double zoomFactor = 1.05;
                        double deltaY = event.getDeltaY();

                        if (deltaY < 0){
                            zoomFactor = 0.95;
                        }
                        node.setScaleX(node.getScaleX() * zoomFactor);
                        node.setScaleY(node.getScaleY() * zoomFactor);
                        myDisplayer.requestFocus();
                        event.consume();

                    }
                });

    }

    /**
    add listner to resize event
     */

    public void setResizeEvent(Scene scene) {
          scene.widthProperty().addListener(new ChangeListener<Number>() {
              @Override
              public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                  if (game != null) {
                      myDisplayer.setWidth(newValue.doubleValue() / 1.3);
                      myDisplayer.draw();
                  }
              }
          });

          scene.heightProperty().addListener(new ChangeListener<Number>() {
              @Override
              public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                  if (game!= null) {
                      myDisplayer.setHeight(newValue.doubleValue() / 1.3);
                      myDisplayer.draw();
                  }
              }
          });
        myDisplayer.requestFocus();
    }

    /**
     * setter for the ViewModel
     * @param view, new viewModel to be inserted
     */
    public void setViewModel(MyViewModel view){
            this.myViewModel=view;

    }

    /**
     * creates new File and save the maze at it's current state
     */
    public void newFile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",
                ButtonType.NO,
                ButtonType.YES);
        alert.setHeaderText("Are you sure you want to create a new maze?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {

            if (mediaPlayer.isAutoPlay())
                mediaPlayer.stop();
            game = null;
            myDisplayer.getParent().setScaleX(1);
            myDisplayer.getParent().setScaleY(1);
            myDisplayer.drawMaze(game, 0,0);
            DisableButtons();
            set_update_player_position_row("");
            set_update_player_position_col("");
            backGroundSound();
            if (!about.getChildren().isEmpty())
                about.getChildren().remove(0);
            myDisplayer.requestFocus();
        }

    }

    /**
     * disable all the buttons on the border
     */
    public void DisableButtons(){
        solve.setDisable(true);
        searcher.setDisable(true);
        player_row_label.setVisible(false);
        player_col_label.setVisible(false);
        about_pane.setVisible(false);
    }

    /**
     * saves the file via MyViewModel
     */
    public void saveFile() {
        myViewModel.saveFile();
    }

    /**
     * loading the specific file and displaying the maze in the same
     * state it was loaded and stoped
     */
    public void loadFile() {
        myViewModel.loadFile();
        int player_row = myViewModel.getPlayer_row();
        int player_col = myViewModel.getPlayer_col();
        set_update_player_position_row(String.valueOf(player_row));
        set_update_player_position_col(String.valueOf(player_col));
        game = myViewModel.getMaze();
        myDisplayer.drawMaze(game, player_row, player_col);
        EnableButtons();

    }

    /**
     * shows alert that display the current instance properties
     */
    public void getProperties() {
        String prop= "Searching Algorithm is: "+getSearchingAlgorithem().getName()+"\n";
        prop= prop+ "Number of Threads available:  "+getNumOfThreads();
        Alert a = new Alert(Alert.AlertType.INFORMATION,prop);
        a.show();
    }

    /**
     * exits the game in an order manner, shuts down all threads and exits
     * @return true if user pressed ok and requested exit, false otherwise
     */
    public boolean exitGame() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Exit game?");
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            myViewModel.StopServers();
            System.exit(0);
        }
        else{
            return false;
        }
        return true;
    }

    /**
     * handles the stage upon user exit request
     * @param primaryStage
     */
    public void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                if (!exitGame())
                    windowEvent.consume();
            }
        });
    }
    /**
    * displays the help text upon user request
    */
    public void getHelp() {
        about_pane.setVisible(true);
            if (!about.getChildren().isEmpty())
                about.getChildren().remove(0);
            about.getChildren().add(new Text(
                    "Welcome to the Maze game.\n" +
                            "to play this game \n" +
                            "use numpad keys\n" +
                            "Up:                   8\n" +
                            "Up-Right:         9\n" +
                            "Right:               6\n" +
                            "Right-Down:    3\n" +
                            "Down:              2\n" +
                            "Left-Down:      1\n" +
                            "Left:                 4\n" +
                            "Left-Up:           7"));
    }

    /**
     * displays the about info
     */
    public void getAbout() {
        about_pane.setVisible(true);
            if (!about.getChildren().isEmpty())
                about.getChildren().remove(0);
                about.getChildren().add(new Text(
                        "About us: \n" +
                    "This game was created by:\n" +
                    "Dor Levy and Daniel Goldman.\n" +
                    "Two young software engineers.\n" +
                    "We hope you enjoyed it.\n"));
    }
}




