package hva.tree;

public abstract  class EvergreenTreeState extends TreeState{

    private static final long serialVersionUID = 202410071835L;

    public EvergreenTreeState(Tree tree, int seasonCleaningEffort, String biologicalCycle){
        super(tree, seasonCleaningEffort, biologicalCycle);
    }

    @Override
    public String toString(){
        return "PERENE|" + getBiologicalCycle(); 
    }
}