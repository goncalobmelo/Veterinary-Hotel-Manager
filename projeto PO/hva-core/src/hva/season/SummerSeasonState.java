package hva.season;

import hva.tree.*;

public class SummerSeasonState extends SeasonState{
    
    private static final long serialVersionUID = 202410211123L;

    public SummerSeasonState(Season season){
        super(season);
    }

    @Override
    public void changeState(){
        Season season = getSeason();
        season.setCode(2);
        season.setState(new AutumnSeasonState(season));
    }

    @Override
    public TreeState evergreenState(Tree tree){
        return new EvergreenTreeSummer(tree);
    }

    @Override
    public TreeState deciduousState(Tree tree){
        return new DeciduousTreeSummer(tree);
    }
}