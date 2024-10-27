package hva.season;

import hva.tree.*;

public class WinterSeasonState extends SeasonState{

    private static final long serialVersionUID = 202410211124L;

    public WinterSeasonState(Season season){
        super(season);
    }

    @Override
    public void changeState(){
        Season season = getSeason();
        season.setCode(0);
        season.setState(new SpringSeasonState(season));
    }

    @Override
    public TreeState evergreenState(Tree tree){
        return new EvergreenTreeWinter(tree);
    }

    @Override
    public TreeState deciduousState(Tree tree){
        return new DeciduousTreeWinter(tree);
    }
}