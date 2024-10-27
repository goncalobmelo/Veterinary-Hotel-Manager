package hva.tree;

public class DeciduousTreeSpring extends DeciduousTreeState{

    private static final long serialVersionUID = 202410071910L;

    public DeciduousTreeSpring(Tree tree){
        super(tree, 1,"GERARFOLHAS");
    }

    @Override
    public void changeState(){
        incrementAge();
        getTree().setState(new DeciduousTreeSummer(getTree()));
    }
}