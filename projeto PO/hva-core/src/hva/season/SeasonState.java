package hva.season;

import hva.tree.*;
import java.io.Serializable;

public abstract class SeasonState implements Serializable{

    private static final long serialVersionUID = 202410211121L;

    private final Season _season;

    public SeasonState(Season season){
        _season = season;
    }

    public Season getSeason(){
        return _season;
    }

    public abstract void changeState();

    public abstract TreeState evergreenState(Tree tree);
    public abstract TreeState deciduousState(Tree tree);

}