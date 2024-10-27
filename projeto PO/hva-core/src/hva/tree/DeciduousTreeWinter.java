package hva.tree;

public class DeciduousTreeWinter extends DeciduousTreeState{

    private static final long serialVersionUID = 202410071855L;

    public DeciduousTreeWinter(Tree tree){
        super(tree, 0,"SEMFOLHAS");
    }

    @Override
    public void changeState(){
        incrementAge();
        getTree().setState(new DeciduousTreeSpring(getTree()));
    }
}