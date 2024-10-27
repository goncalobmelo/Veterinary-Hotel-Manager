package hva.tree;

import java.io.Serializable;

public abstract class TreeState implements Serializable{

    private static final long serialVersionUID = 202410071820L;

    private Tree _tree;
    private int _seasonCleaningEffort;
    private String _biologicalCycle;

    public TreeState(Tree tree, int seasonCleaningEffort, String biologicalCycle){
        _tree = tree;
        _seasonCleaningEffort = seasonCleaningEffort;
        _biologicalCycle = biologicalCycle;
    }

    public Tree getTree(){
        return _tree;
    }

    public void incrementAge(){
        _tree.incrementAge();
    }

    public abstract void changeState();

    public int getSeasonCleaningEffort(){
        return _seasonCleaningEffort;
    }

    public String getBiologicalCycle(){
        return _biologicalCycle;
    }

    @Override
    public abstract String toString();
}