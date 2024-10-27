package hva.tree;

public class DeciduousTreeSummer extends DeciduousTreeState{

    private static final long serialVersionUID = 202410071900L;

    public DeciduousTreeSummer(Tree tree){
        super(tree, 2,"COMFOLHAS");
    }

    @Override
    public void changeState(){
        incrementAge();
        getTree().setState(new DeciduousTreeAutumn(getTree()));
    }
}