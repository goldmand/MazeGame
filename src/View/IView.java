package View;

import ViewModel.MyViewModel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.UnknownHostException;

/**
 * this interface includes all functionallty of the controller as
 * part of MVVM architecture
 * to be implemented by MyViewController
 */
public interface IView {
    void set_update_player_position_row(String update_player_position_row);
    void set_update_player_position_col(String update_player_position_col);
    void backGroundSound();
    void WinMoveSound();
    void generateMaze() throws UnknownHostException;
    void solveMaze();
    void showAlert(String message);
    void keyPressed(KeyEvent keyEvent);
    void addMouseScrolling(Node node);
    void newFile();
    void saveFile();
    void loadFile();
    void getProperties();
    boolean exitGame();
    void SetStageCloseEvent(Stage primaryStage);
    void getHelp();
    void getAbout();
    int[] checkInputs();
    void init();
    void EnableButtons();
    void initSearchChoose();
    void DisableButtons();
    void mouseClicked();
}
