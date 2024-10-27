package hva.tree;

public abstract class DeciduousTreeState extends TreeState{

    private static final long serialVersionUID = 202410071905L;

    public DeciduousTreeState(Tree tree, int seasonCleaningEffort, String biologicalCycle){
        super(tree, seasonCleaningEffort, biologicalCycle);
    }

    @Override
    public String toString(){
        return "CADUCA|" + getBiologicalCycle(); 
    }
}