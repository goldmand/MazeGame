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


    public void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set("               "+update_player_position_row);
    }

    public void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set("               "+update_player_position_col);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_player_row.textProperty().bind(update_player_position_row);
        lbl_player_column.textProperty().bind(update_player_position_col);
        backGroundSound();

    }
    public void backGroundSound(){
        String path = "src\\resources\\Sound\\game_playback.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.setAutoPlay(true);
        volume_control.setValue(30);
        volume_control.setOnMouseReleased(e->mediaPlayer.setVolume(volume_control.getValue()/100));
    }
    public void WinMoveSound() {
        String path = "src\\resources\\Sound\\win.mp3";
        javafx.scene.media.Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.setAutoPlay(true);
        showAlert("You win!!!");
    }

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

    public void init(){
        addMouseScrolling(myDisplayer.getParent());
        SetStageCloseEvent(myDisplayer.getPrimarystage());
        initSearchChoose();
        scroll_pane.setStyle("-fx-opacity: .9");
    }
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
    public void EnableButtons(){
        // enable Solve,searcher, player buttons and labels:
        solve.setDisable(false);
        searcher.setDisable(false);
        player_row_label.setVisible(true);
        player_col_label.setVisible(true);
    }

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

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE,message, ButtonType.CLOSE);
        alert.show();
    }

    public void initSearchChoose() {

        searcher.getItems().add("BreadthFirstSearch");
        searcher.getItems().add("BestFirstSearch");
        searcher.getItems().add("DepthFirstSearch");

        //Set a default value
        searcher.setValue("BestFirstSearch");
        searcher.setOnAction(e -> Configurations.setSolver(searcher.getValue().toString()));
    }

    public void MakePlayerMove(int p, int row, int col){
        if (p == 0 || p == 1) {
            myDisplayer.set_player_position(row, col);
            set_update_player_position_row(row + "");
            set_update_player_position_col(col + "");
        }
        if (p == 1)
            WinMoveSound();
    }

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

    public void mouseClicked() {
        myDisplayer.requestFocus();
    }



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

    public void setViewModel(MyViewModel view){
            this.myViewModel=view;

    }


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
    public void DisableButtons(){
        solve.setDisable(true);
        searcher.setDisable(true);
        player_row_label.setVisible(false);
        player_col_label.setVisible(false);
        about_pane.setVisible(false);
    }

    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        //Extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("MAZE files (*.maze)", "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(null);

        if(file!=null){
            try(ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream(file))){
                ob.writeObject(game);
                ob.writeObject(myDisplayer.getRow_player());
                ob.writeObject(myDisplayer.getCol_player());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        //Extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("MAZE files (*.maze)", "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try(ObjectInputStream ob = new ObjectInputStream(new FileInputStream(file))){ ;
               game = ob.readObject();
               String player_row = ob.readObject().toString();
               set_update_player_position_row(player_row);
               String player_col = ob.readObject().toString();
               set_update_player_position_col(player_col);
               myDisplayer.drawMaze(game, Integer.parseInt(player_row), Integer.parseInt(player_col));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void getProperties() {
        String prop= "Searching Algorithm is:      "+getSearchingAlgorithem().getName()+"\n";
        prop= prop+ "Number of Threads available:  "+getNumOfThreads();
        Alert a = new Alert(Alert.AlertType.INFORMATION,prop);
        a.show();
    }

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
    public void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                if (!exitGame())
                    windowEvent.consume();
            }
        });
    }

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




