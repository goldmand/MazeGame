package ViewModel;

import Model.IModel;
import Model.MyModel;
import View.MyDisplayer;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    IModel model;

    public void StartServers(){this.model.startServers();}

    public void StopServers(){this.model.stopServers();}

    public MyViewModel(){
        this.model=new MyModel();
    }

    public MyViewModel(MyModel model) {
        this.model = model;
    }

    public int getCellValue(int i, int j, Object o){
        return model.getCellValue(i,j,(Maze)o);
    }

    public int getGoalRow(Object o) {
        return model.getGoalRow((Maze)o);
    }


    public int getGoalCol(Object o) {
        return model.getGoalCol((Maze)o);
    }


    public int getStartRow(Object o) {
        return model.getStartRow((Maze)o);
    }

    public int MakeMove(Object o, int row, int col) { return model.MakeMove((Maze)o,row,col); }


    public int getStartCol(Object o) {
        return model.getStartCol((Maze)o);
    }

    public Object getMaze(int row, int col) throws UnknownHostException {
        model.GenerateMaze(row,col); //changed here
        return model.getMaze();
    }


    public Object getSol() {
        model.GenerateSolution();
        return model.getSol();
    }

    public int getRows(Object o) {
        return model.getRows((Maze)o);
    }

    public int getCols(Object o) {
        return model.getCols((Maze)o);
    }

    public boolean inSol(Object o, int i, int j){
        return model.inSol((Solution)o,i,j);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o==model){
            setChanged();
            notifyObservers();
        }
    }
}

