package hva.tree;

public class EvergreenTreeSpring extends EvergreenTreeState{

    private static final long serialVersionUID = 202410071840L;

    public EvergreenTreeSpring(Tree tree){
        super(tree, 1,"GERARFOLHAS");
    }

    @Override
    public void changeState(){
        incrementAge();
        getTree().setState(new EvergreenTreeSummer(getTree()));
    }
}