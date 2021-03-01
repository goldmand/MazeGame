package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.io.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;



public class MyDisplayer extends Canvas {

    private Stage primarystage;

    public void setPrimarystage(Stage primarystage) {
        this.primarystage = primarystage;
    }

    private Object game;
    private Object sol;

    private MyViewModel myViewModel;
    private MyViewController myViewController;

    private int row_player = 0;
    private int col_player = 0;

    StringProperty imageFileNamePath = new SimpleStringProperty();
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameGoal = new SimpleStringProperty();

    public String getImageFileNamePath() {
        return imageFileNamePath.get();
    }

    public void setImageFileNamePath(String imageFileNamePath) {
        this.imageFileNamePath.set(imageFileNamePath);
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameGoal() {
        return imageFileNameGoal.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.imageFileNameGoal.set(imageFileNameGoal);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) { this.imageFileNamePlayer.set(imageFileNamePlayer); }

    public int getRow_player() {
        return row_player;
    }

    public int getCol_player() {
        return col_player;
    }

    public void set_player_position(int row, int col){
        this.row_player = row;
        this.col_player = col;

        draw();

    }

    public void setMyViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    public StringProperty imageFileNamePathProperty() {
        return imageFileNamePath;
    }

    public StringProperty imageFileNameWallProperty() {
        return imageFileNameWall;
    }

    public StringProperty imageFileNamePlayerProperty() {
        return imageFileNamePlayer;
    }

    public StringProperty imageFileNameGoalProperty() {
        return imageFileNameGoal;
    }

    public Stage getPrimarystage() {
        return primarystage;
    }

    public void setMyViewController(MyViewController myViewController) {
        this.myViewController = myViewController;
    }

    public void drawMaze(Object o, int row_player_new, int col_player_new)
    {
        this.game = o;
        this.sol = null;
        row_player = row_player_new;
        col_player = col_player_new;

        primarystage.setHeight(700);
        primarystage.setWidth(800);
        draw();
    }

    public void draw() {

        GraphicsContext graphicsContext = getGraphicsContext2D();
       // graphicsContext.
        if (game == null) {

            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(0, 0, getHeight(), getWidth());
            primarystage.setHeight(500);
            primarystage.setWidth(193);
        }
        else{
            int row = myViewModel.getRows(game);
            int col = myViewModel.getCols(game);

            double canvasHeight = getHeight();
            double canvasWidth = getWidth();

            double cellHeight = canvasHeight / row;
            double cellWidth = canvasWidth / col;
            double w, h;

            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            graphicsContext.setFill(Color.GREEN);

            //Draw Maze
            Image wallImage = null, padImage = null, pathImage = null;
            try {
                pathImage = new Image(new FileInputStream(getImageFileNamePath()));
                wallImage = new Image(new FileInputStream(getImageFileNameWall()));
                padImage = new Image(new FileInputStream(getImageFileNameGoal()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no file....");
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    h = i * cellHeight;
                    w = j * cellWidth;

                    if (myViewModel.getCellValue(i, j, game) == 1) // Wall
                    {
                        graphicsContext.drawImage(wallImage, w, h, cellWidth, cellHeight);
                    } else if (myViewModel.getCellValue(i, j, game) == 0) // path
                    {
                        graphicsContext.drawImage(pathImage, w, h, cellWidth, cellHeight);
                    }
                    if (sol != null && myViewModel.inSol(sol, i, j)) {
                        graphicsContext.fillRect(w, h, cellWidth, cellHeight);
                    }
                    if (myViewModel.getGoalRow(game) == i && myViewModel.getGoalCol(game) == j) {
                        graphicsContext.drawImage(padImage, w, h, cellWidth, cellHeight);
                    }
                }
            }

            double h_player = getRow_player() * cellHeight;
            double w_player = getCol_player() * cellWidth;
            Image playerImage = null;
            try {
                playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no Image player....");
            }
            graphicsContext.drawImage(playerImage, w_player, h_player, cellWidth, cellHeight);
        }

    }



    public void drawSolution(Object o) {
        this.game = o;
        this.sol = myViewModel.getSol();
        draw();
    }

    public void setSol(Object sol) {
        this.sol = sol;
    }
}

