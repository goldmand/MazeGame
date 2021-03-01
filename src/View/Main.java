package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.util.Optional;

/**
 * main class to launches the application
 * @authors: Daniel Goldman and Dor levi
 * @since : 25-6-2020
 */

public class Main extends Application {
    /**
     * Implmention of the start method that activities the window
     * upon launching the application
     * @param primaryStage, primaryStage window of the application
     * @throws Exception, in case a source xml file not found
     */

    @Override public void start(Stage primaryStage) throws Exception{
          MyModel model= new MyModel();
        MyViewModel myViewModel=new MyViewModel(model);
        FXMLLoader fxmlLoader=new FXMLLoader();
        primaryStage.setTitle("Maze game");

        Parent root = fxmlLoader.load(getClass().getResource("MyView.fxml").openStream());

        Scene scene= new Scene(root, 300, 275);
        scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        MyViewController myViewController = fxmlLoader.getController();
        myViewController.setViewModel(myViewModel);
        MyDisplayer myDisplayer= myViewController.myDisplayer;
        myDisplayer.setMyViewModel(myViewModel);
        myDisplayer.setMyViewController(myViewController);
        myDisplayer.setPrimarystage(primaryStage);
        myViewController.setResizeEvent(scene);
        myViewController.init();

        primaryStage.setHeight(500);
        primaryStage.setWidth(193);

        primaryStage.show();

    }

        public static void main(String[] args) { launch(args); }
}
