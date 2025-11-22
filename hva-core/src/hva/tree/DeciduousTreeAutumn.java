package hva.tree;

public class DeciduousTreeAutumn extends DeciduousTreeState{

    private static final long serialVersionUID = 202410071915L;

    public DeciduousTreeAutumn(Tree tree){
        super(tree, 5,"LARGARFOLHAS");
    }

    @Override
    public void changeState(){
        incrementAge();
        getTree().setState(new DeciduousTreeWinter(getTree()));
    }
}