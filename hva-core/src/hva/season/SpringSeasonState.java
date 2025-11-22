package hva.season;

import hva.tree.*;

public class SpringSeasonState extends SeasonState{

    private static final long serialVersionUID = 202410211122L;

    public SpringSeasonState(Season season){
        super(season);
    }

    @Override
    public void changeState(){
        Season season = getSeason();
        season.setCode(1);
        season.setState(new SummerSeasonState(season));
    }

    @Override
    public TreeState evergreenState(Tree tree){
        return new EvergreenTreeSpring(tree);
    }

    @Override
    public TreeState deciduousState(Tree tree){
        return new DeciduousTreeSpring(tree);
    }
}