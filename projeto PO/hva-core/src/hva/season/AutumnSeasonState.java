package hva.season;

import hva.tree.*;

public class AutumnSeasonState extends SeasonState{

    private static final long serialVersionUID = 202410211125L;

    public AutumnSeasonState(Season season){
        super(season);
    }

    @Override
    public void changeState(){
        Season season = getSeason();
        season.setCode(3);
        season.setState(new WinterSeasonState(season));
    }

    @Override
    public TreeState evergreenState(Tree tree){
        return new EvergreenTreeAutumn(tree);
    }

    @Override
    public TreeState deciduousState(Tree tree){
        return new DeciduousTreeAutumn(tree);
    }
}